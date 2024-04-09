package edu.esprit.gui;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.esprit.entities.Produits;
import edu.esprit.services.ProduitsCRUD;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AdminshopController implements Initializable {

    @FXML
    private TableColumn<Produits, String> prodcol1;
    @FXML
    private TableColumn<Produits, String> prodcol2;
    @FXML
    private TableColumn<Produits, String> prodcol3;
    @FXML
    private TableColumn<Produits, String> prodcol4;
    @FXML
    private ImageView imageview1;
    @FXML
    private TableView<Produits> table12;
    @FXML
    private Button supprimerprod;
    @FXML
    private Button modifier;
    private Produits selectedProduit;
    private String imagePath = "";
    @FXML
    private TextField nomprod;
    @FXML
    private TextField prixprod;
    @FXML
    private TextField descprod;
    @FXML
    private Button importer;
    @FXML
    private ComboBox<String> statutprod;
    @FXML
    private Button appeldonbtn2;
    @FXML
    private Button espaceadminbtn2;
    @FXML
    private Button eventbtn;
    @FXML
    private Button retourbtn;
    @FXML
    private Button dec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      prodcol1.setCellValueFactory(new PropertyValueFactory<>("nomDuProduit"));
    prodcol2.setCellValueFactory(new PropertyValueFactory<>("prix"));
    prodcol3.setCellValueFactory(new PropertyValueFactory<>("description"));
    prodcol4.setCellValueFactory(new PropertyValueFactory<>("statut"));

    // Peupler la ComboBox avec des choix possibles
    ObservableList<String> statutOptions = FXCollections.observableArrayList("available", "not_available");
    statutprod.setItems(statutOptions);

    // Call the method to populate the table
    populateTable();

    // Handle selection changes in the table
    table12.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Produits>() {
        @Override
        public void changed(ObservableValue<? extends Produits> observable, Produits oldValue, Produits newValue) {
            if (newValue != null) {
                selectedProduit = newValue;
                // Remplir les champs avec les données de la ligne sélectionnée
                nomprod.setText(selectedProduit.getNomDuProduit());
                prixprod.setText(String.valueOf(selectedProduit.getPrix()));
                descprod.setText(selectedProduit.getDescription());

                // Vous pouvez également définir la valeur de la ComboBox
                // en fonction de la valeur de statut du produit sélectionné
                // Assurez-vous que statutprod.getItems() contient les options possibles.
                statutprod.setValue(selectedProduit.getStatut());

                // Afficher l'image associée au produit (comme dans la méthode displayImage)
                displayImage(selectedProduit.getImage());
            }
        }
    });
    }
    private void displayImage(String imagePath) {
    if (imagePath != null && !imagePath.isEmpty()) {
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageview1.setImage(image);
        }
    }
}
    public void populateTable() {
        // Clear existing items from the table
        table12.getItems().clear();
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
        List<Produits> produits = produitsCRUDInstance.afficherP();
        ObservableList<Produits> produitsList = FXCollections.observableArrayList(produits);
        table12.setItems(produitsList);
    }

    @FXML
    private void supprod(ActionEvent event) {
        if (selectedProduit != null) {
            // Code to delete the selected product
            ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
            produitsCRUDInstance.deleteProduit(selectedProduit);
             produitsCRUDInstance.insertNomProdAdmin( selectedProduit.getNomDuProduit(),"supprime");



            populateTable();
            clearFields();
        }
    }

  @FXML
private void modprod(ActionEvent event) {
    if (selectedProduit != null) {
        // Get the updated values from the input fields
        String newNom = nomprod.getText();
        String prixStr = prixprod.getText();
        String desc = descprod.getText();
        String newStatut = statutprod.getValue();

        // Ensure that required fields are not empty
        if (newNom.isEmpty() || prixStr.isEmpty() || desc.isEmpty() || newStatut == null) {
            // Afficher une alerte pour les champs vides
            showAlert("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // Check if the new name already exists in the table
        if (isNameExists(newNom)) {
            // Afficher une alerte pour le nom existant
            showAlert("Nom déjà existant", "Le nom du produit existe déjà. Veuillez utiliser un nom différent.");
            return;
        }

        // Convert the price to an integer
        int newPrix = 0;
        try {
            newPrix = Integer.parseInt(prixStr);
        } catch (NumberFormatException e) {
            // Afficher une alerte pour un prix invalide
            showAlert("Prix invalide", "Le prix n'est pas un nombre valide. Veuillez entrer un nombre valide.");
            return;
        }

        // Create an updated Produits object
        Produits updatedProduit;

        if (imagePath != null && !imagePath.isEmpty()) {
            // Si une nouvelle image a été sélectionnée, utilisez le nouveau chemin d'image
            updatedProduit = new Produits(newNom, newPrix, desc, newStatut, imagePath);
        } else {
            // Sinon, conservez le chemin d'image actuel
            updatedProduit = new Produits(newNom, newPrix, desc, newStatut, selectedProduit.getImage());
        }

        // Update the product in the database
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
        produitsCRUDInstance.updateProduit(selectedProduit, updatedProduit);
          produitsCRUDInstance.insertNomProdAdmin( selectedProduit.getNomDuProduit(),"modifie");


        // Refresh the table after the update
        populateTable();
        clearFields();
    }
}

private boolean isNameExists(String nameToCheck) {
    for (Produits produit : table12.getItems()) {
        if (produit.getNomDuProduit().equals(nameToCheck)) {
            return true;
        }
    }
    return false;
}

private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

    
    private void clearFields() {
        nomprod.clear();
        prixprod.clear();
        descprod.clear();
        statutprod.getSelectionModel().clearSelection();
        imageview1.setImage(null);
        selectedProduit = null;
        imagePath = "";
    }

    private void handleImportButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        try {
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageview1.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void importerimage(ActionEvent event) {
    }


    @FXML
    private void retourmethod(ActionEvent event) {        try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void appeladmin(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin2.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void gotoadevent(ActionEvent event) {        try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eventadmin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void dec1(ActionEvent event) {
         try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    }
   
    
       
}
