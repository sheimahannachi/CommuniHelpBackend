/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.Produits;
import edu.esprit.services.ProduitsCRUD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class CardshopController implements Initializable {
private Produits produit;
    @FXML
    private Button acheterButton;
    @FXML
    private ImageView postImageViewshop;
    @FXML
    private Text postContentTextshop;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
  public void setProduit(Produits produit) {
    this.produit = produit;

    // Check for null values before setting text
    String NomDuProduit = produit.getNomDuProduit();
    int prix = produit.getPrix();
    String description = produit.getDescription();
    String statut = produit.getStatut();
    
    StringBuilder text = new StringBuilder("Nom du produit: ");
      text.append(NomDuProduit != null ? NomDuProduit : "N/A");
    text.append("\nPrix: ");
    text.append(prix);
    text.append("\nDescription: ");
    text.append(description != null ? description : "N/A");
    text.append("\nStatut: ");
    text.append(statut != null ? statut : "N/A");

    
    postContentTextshop.setText(text.toString());

    // Set the image based on the article's image URL
     String imageUrl = produit.getImage();
    if (imageUrl != null) {
        Image image = new Image("file:" + imageUrl);
        postImageViewshop.setImage(image);
    }
}
    @FXML
    private void acheterButtonClick(ActionEvent event) {
        if (produit != null) {
        // Utilize the name of the selected product as needed
        String selectedProductName = produit.getNomDuProduit();
        System.out.println("Produit sélectionné : " + selectedProductName);

        // Vérifier que vous avez une instance de ProduitsCRUD
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();

        // Appeler la méthode insertNomProduit pour insérer le nom dans la base de données
        produitsCRUDInstance.insertNomProduit(selectedProductName, 1);
          if (produit == null) {
        // Handle the case where produit is not set or is null
        return;
    }
        String description = produit.getDescription();
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscri.fxml")); // Replace with the actual FXML file name
        Parent root = loader.load();

        // Access the AcheterFormController and pass the description
        InscriController acheterFormController = loader.getController();
        
        // You can pass other data to AcheterFormController if needed
        // acheterFormController.setSomeOtherData(someOtherValue);
        acheterFormController.setProduitId(produit.getId());

        // Create a new stage for the acheter form
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Acheter Form");
        stage.show();

        // Close the current stage (your current window)
        Stage currentStage = (Stage) acheterButton.getScene().getWindow(); // Replace "acheterButton" with the actual button
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
        }}}
    
       

