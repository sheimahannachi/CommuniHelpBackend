package edu.esprit.gui;

import edu.esprit.entities.Produits;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import edu.esprit.services.ProduitsCRUD;
import edu.esprit.utilis.MyConnection;

public class  LivraisonwindController implements Initializable {

    @FXML
    private TableView<Produits> prod1_view;
    @FXML
    private TableColumn<Produits, String> prodcol_;
    @FXML
    private TableColumn<Produits, String> prixcol_;
    @FXML
    private TableColumn<Produits, String>desccol_;
    @FXML
    private TableColumn<Produits, String> statutcol_;
     private MyConnection cnx2 = MyConnection.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
         // Initialisez les colonnes du TableView
           prodcol_.setCellValueFactory(new PropertyValueFactory<>("nomDuProduit"));
    prixcol_.setCellValueFactory(new PropertyValueFactory<>("prix"));
    desccol_.setCellValueFactory(new PropertyValueFactory<>("description"));
    statutcol_.setCellValueFactory(new PropertyValueFactory<>("statut"));

    // Obtenez la liste des produits depuis la base de données
    List<Produits> produitsList = getProduitsList();

    // Filtrer la liste pour afficher uniquement les produits avec le statut "available"
    List<Produits> availableProducts = produitsList.stream()
            .filter(produit -> "available".equals(produit.getStatut()))
            .collect(Collectors.toList());

    // Créez une liste observable pour alimenter le TableView
    ObservableList<Produits> observableProduitsList = FXCollections.observableArrayList(availableProducts);

    // Remplissez le TableView avec la liste observable
    prod1_view.setItems(observableProduitsList);
}

public List<Produits> getProduitsList() {
    List<Produits> produitsList = new ArrayList<>();
    ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
    produitsList = produitsCRUDInstance.getProduitsList();
    return produitsList;
}

}