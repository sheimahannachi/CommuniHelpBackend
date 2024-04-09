/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import edu.esprit.entities.ListeCData;
import edu.esprit.services.CommandeCRUD;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CommandeController implements Initializable {
    private CommandeCRUD commandeCRUD;

    @FXML
    private TableView<ListeCData> Ctable;
    @FXML
    private TableColumn<ListeCData, String> prodCo;
    @FXML
    private TableColumn<ListeCData, String> nomCo;
    @FXML
    private TableColumn<ListeCData, String> contactCo;
    @FXML
    private TableColumn<ListeCData, String> emailCo;
    @FXML
    private TableColumn<ListeCData, String> adresseCo;
    @FXML
    private Button annulerC;
    @FXML
    private Button retour;

    public CommandeController() {
        commandeCRUD = new CommandeCRUD();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Utilisez votre instance de commandeCRUD pour appeler la méthode
        prodCo.setCellValueFactory(new PropertyValueFactory<>("nomProduit"));
contactCo.setCellValueFactory(new PropertyValueFactory<>("contact"));
nomCo.setCellValueFactory(new PropertyValueFactory<>("nomDest"));
emailCo.setCellValueFactory(new PropertyValueFactory<>("emailC"));
adresseCo.setCellValueFactory(new PropertyValueFactory<>("adresseC"));

// Appelez la méthode pour peupler le tableau
populateTable();
    }  
  public void populateTable() {
    // Effacer les éléments existants du tableau
    Ctable.getItems().clear();

    // Récupérez la liste de données de listec_ à partir de votre classe CommandeCRUD
    List<ListeCData> listeCDataList = commandeCRUD.afficherListeC();

    // Créez une liste observable pour stocker les données
    ObservableList<ListeCData> dataList = FXCollections.observableArrayList(listeCDataList);

    // Ajoutez les données au TableView
    Ctable.setItems(dataList);
}

    @FXML
    private void annulerC(ActionEvent event) {ListeCData selectedCommande = Ctable.getSelectionModel().getSelectedItem();
    
    if (selectedCommande != null) {
        // Récupérez le nom du produit à partir de l'élément sélectionné
        String productName = selectedCommande.getNomProduit();
        
        // Appelez la méthode pour mettre à jour le statut du produit
        commandeCRUD.updateProductStatusToAvailable(productName);
        
        // Rafraîchissez le TableView pour refléter les modifications
        populateTable();
    } else {
        System.out.println("Aucun produit sélectionné pour annuler.");
    }
}

    @FXML
    private void swingtoS(ActionEvent event) {  
        try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopwind.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
       

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }





    
}
