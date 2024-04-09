package edu.esprit.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.esprit.entities.Produits;
import edu.esprit.services.ProduitsCRUD;
import edu.esprit.utilis.MyConnection;
import static edu.esprit.utilis.MyConnection.getInstance;

public class Livraison1Controller implements Initializable {

    @FXML
    private TableView<Produits> prod1_view;
    @FXML
    private TableColumn<Produits, String> prodcol_;
    @FXML
    private TableColumn<Produits, String> prixcol_;
    @FXML
    private TableColumn<Produits, String> desccol_;
    
    @FXML
    private TableColumn<Produits,String> statutcol_;
    @FXML
    private Button swingbtn;
       @FXML
    private ImageView imageviewlivraisonid;


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
         prod1_view.setOnMouseClicked(event -> {
        // Get the selected product
        Produits selectedProduit = prod1_view.getSelectionModel().getSelectedItem();

        
            // Load the image based on the selected product
            String imagePath = selectedProduit.getImage();
            
                Image image = new Image(new File(imagePath).toURI().toString());
                imageviewlivraisonid.setImage(image);
         });
         
}
    

    public List<Produits> getProduitsList() {
        List<Produits> produitsList = new ArrayList<>();
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
        produitsList = produitsCRUDInstance.getProduitsList();
        return produitsList;
    }

    @FXML
    private void swingbtn(ActionEvent event) {
         String selectedProductName = getSelectedProductName();
    if (!selectedProductName.isEmpty()) {
        // Utilisez le nom du produit sélectionné comme vous le souhaitez
        System.out.println("Produit sélectionné : " + selectedProductName);
         // Vérifier que vous avez une instance de ProduitsCRUD
    ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();

    // Appeler la méthode insertNomProduit pour insérer le nom dans la base de données
    produitsCRUDInstance.insertNomProduit(selectedProductName,1);
        // Ajoutez ici le code pour passer à l'interface suivante ou effectuer d'autres actions avec le nom du produit sélectionné
    }
    
    // Vérifier si un produit est sélectionné dans le tableau
    Produits selectedProduit = prod1_view.getSelectionModel().getSelectedItem();
    if (selectedProduit == null) {
        // Aucun produit n'a été sélectionné, afficher une alerte
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Sélectionnez un produit avant de passer à l'interface suivante.");
        alert.showAndWait();
        return; // Ne passez pas à l'interface 2 si aucun produit n'est sélectionné
    }

    try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscri.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
        Stage currentStage = (Stage) prod1_view.getScene().getWindow();

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        currentStage.setScene(scene);
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }

    public String getSelectedProductName() {
    Produits selectedProduit = prod1_view.getSelectionModel().getSelectedItem();

    if (selectedProduit != null) {
        return selectedProduit.getNomDuProduit();
    }

    // Si aucun produit n'est sélectionné, renvoyez une chaîne vide ou une valeur par défaut
    return "";
}
    
    
    
    
     
}