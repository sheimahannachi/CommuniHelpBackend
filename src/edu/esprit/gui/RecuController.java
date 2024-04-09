/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.enumVille;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import edu.esprit.entities.enumVille;
import edu.esprit.entities.enumVille.ville;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class RecuController {
    @FXML
    private Label montantLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label carteBancaireLabel;
    @FXML
    private Button Retour;

public void setDonationData(String montant, String email, String nom, String prenom, enumVille.ville ville, String carteBancaire) {
    if (montant != null) {
        montantLabel.setText(montant);
    } else {
        montantLabel.setText("");
    }

    if (email != null) {
        emailLabel.setText(email);
    } else {
        emailLabel.setText("");
    }

    if (nom != null) {
        nomLabel.setText(nom);
    } else {
        nomLabel.setText("");
    }

    if (prenom != null) {
        prenomLabel.setText(prenom);
    } else {
        prenomLabel.setText("");
    }

   

    if (carteBancaire != null) {
        carteBancaireLabel.setText(carteBancaire);
    } else {
        carteBancaireLabel.setText("");
    }
}
    @FXML
    public void handleRetourButtonAction(ActionEvent event) {
    try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FaireDon2.fxml"));
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
