/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.esprit.entities.CLivraison;
import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.User;
import edu.esprit.services.ProduitsCRUD;
import edu.esprit.services.livraisonCRUD;
import edu.esprit.utilis.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class InscriController implements Initializable {

    @FXML
    private TextField livnom_;
    @FXML
    private TextField livpre_;
    @FXML
    private TextField livphone_;
    @FXML
    private TextField adresseliv_;
    @FXML
    private TextField emailiv_;
    @FXML
    private Button annuler;
    @FXML
    private Button confirmerliv;

    /**
     * Initializes the controller class.
     */
    User user;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (user == null) {
            user = CurrentUser.getLoggedInUser();
        }
        if (user != null) {
            setTextFields(user);
            CurrentUser.setLoggedInUser(user);
            User loggedInUser = CurrentUser.getLoggedInUser();
            int loggedInUserId = loggedInUser.getId();

            setTextFields(loggedInUser);

            String email = user.getEmail(); // Get the email from the logged-in user

            // Query your database to get the name and surname based on the email
            String query = "SELECT nom, prenom ,Num_tel ,adresse FROM user WHERE email = ?";
            Connection conn = MyConnection.getInstance().getCnx();

            try ( PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String adresse=rs.getString("adresse");
                    int Num_Tel =rs.getInt("Num_tel");
                    livnom_.setText(nom); // Set the retrieved name
                    livpre_.setText(prenom); 
                    adresseliv_.setText(adresse);
                    livphone_.setText(String.valueOf(Num_Tel));
                
                
                
                
                
                
                
                } else {
                    // Handle the case when the user with the given email is not found in the database
                }
            } catch (SQLException e) {
                // Handle database errors
                e.printStackTrace();
            }
        }
    }

    void setTextFields(User loggedInUser) ///taabili le fenetre bel les donnes 
    {

        livnom_.setText(loggedInUser.getEmail());

        livpre_.setText(loggedInUser.getNom());
        emailiv_.setText(loggedInUser.getPrenom());
        livphone_.setText(String.valueOf(loggedInUser.getNum_tel()));
        adresseliv_.setText(loggedInUser.getAdresse());
    }

    @FXML
    private void annulerliv(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livraison1test.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton "Annuler"
            Stage currentStage = (Stage) annuler.getScene().getWindow();

            // Remplacer la scène actuelle par la nouvelle scène (interface "livraison1.fxml")
            currentStage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void confirmerliv(ActionEvent event) throws IOException {
        String nom = livnom_.getText();
        String prenom = livpre_.getText();
        String numeroTelephone = livphone_.getText();
        String adresse = adresseliv_.getText();
        String email = emailiv_.getText();

        if (numeroTelephone.isEmpty() || !isNumeric(numeroTelephone)) {
            showAlert("Erreur", "Numéro de téléphone invalide", "Veuillez saisir un numéro de téléphone valide.");
            return; // Sortie de la méthode en cas d'erreur
        }

        if (!isValidEmail(email)) {
            showAlert("Erreur", "Adresse e-mail invalide", "Veuillez saisir une adresse e-mail valide.");
            return; // Sortie de la méthode en cas d'erreur
        }

        // Créez un objet CLivraison avec les données collectées
        CLivraison livraison = new CLivraison(nom, prenom, numeroTelephone, adresse, email);

        // Utilisez le CRUD pour ajouter les données de livraison
        livraisonCRUD livraisonCRUD = new livraisonCRUD();
        livraisonCRUD.addLivraison(livraison);

        // Appelez la méthode pour mettre à jour les produits en fonction du dernier nomProdR
        ProduitsCRUD produitsCRUD = new ProduitsCRUD();
        produitsCRUD.updateProductsStatusBasedOnLastInsertedNomProdR();

        // Appelez la méthode pour insérer les informations dans listec_
        livraisonCRUD.insertIntoListec();

        // Affichez un message de succès
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Information");
        successAlert.setHeaderText(null);
        successAlert.setContentText("  MERCI ");
        successAlert.showAndWait();

        // Chargez la nouvelle scène "livraison1.fxml"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("livraison1test.fxml"));
        Parent root = loader.load();

        // Créez une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir du bouton "Confirmer"
        Stage currentStage = (Stage) confirmerliv.getScene().getWindow();

        // Affichez la nouvelle scène
        currentStage.setScene(scene);
    }

    private boolean isNumeric(String str) {
        // Vérifie si une chaîne est numérique
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isValidEmail(String email) {
        // Vérifie si une adresse e-mail est valide
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private int produitId;

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

}
