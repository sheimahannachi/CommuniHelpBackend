/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.User;
import edu.esprit.services.UserCrud;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


public class AjoutUserController implements Initializable {

    @FXML
    private TextField fxprenom;
    @FXML
    private TextField fxnom;
    @FXML
    private TextField fxemail;
    @FXML
    private PasswordField fxpassword;
    @FXML
    private Button btnaj;
    @FXML
    private TextField fxnum;
    @FXML
    private TextField fxadresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void ajouter(ActionEvent event) {
       boolean saisieValide = true;
        String messageErreur = "";
        String activation_token = null;

        String email = fxemail.getText();
        if (fxemail.getText().isEmpty()
                | fxpassword.getText().isEmpty()
                | fxnom.getText().isEmpty()
                
                | fxprenom.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("  :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Remplir tous les champs  !!");
            alert.showAndWait();


        } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") || verifierEmailNonDuplique(email) == false) {
            saisieValide = false;
            messageErreur += "L'email n'est pas valide.\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(" :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("EMAIL NON VALIDE  !!");
            alert.showAndWait();

        } else {
            String hashedPassword = hashMotDePasse(fxpassword.getText());
            String Email = fxemail.getText();
            String nom = fxnom.getText();
            String prenom = fxprenom.getText();
            Integer num_tel = Integer.parseInt(fxnum.getText());
            
          
            UserCrud us = new UserCrud();
            User p = new User(Email, nom, prenom, hashedPassword, "user", activation_token, "actif",fxadresse.getText(),num_tel);
            us.ajouterUtilisateur2(p);
             Stage home = new Stage();

                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene);
                        home.show();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
        
    }
    }

    
    @FXML
    private void close(MouseEvent event) throws IOException {
       // récupère la scène actuelle à partir de l'événement
        Scene currentScene = ((Node) event.getSource()).getScene();
        // récupère la fenêtre actuelle à partir de la scène
        Stage currentStage = (Stage) currentScene.getWindow();
        // charge la nouvelle vue depuis un fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Parent newViewParent = loader.load();
        // crée une nouvelle scène à partir de la vue chargée
        Scene newScene = new Scene(newViewParent);
        // récupère la nouvelle fenêtre à partir de la scène
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        // affiche la nouvelle fenêtre
        newStage.show();
        // ferme l'ancienne fenêtre
        currentStage.close();
    }
     public boolean verifierEmailNonDuplique(String email) {
        String requete = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        Connection Ds = MyConnection.getInstance().getCnx();
        boolean emailExiste = false;

        try {
            statement = Ds.prepareStatement(requete);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                emailExiste = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return !emailExiste;
    }

    public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(fxemail.getText());

        if (match.find() && match.group().equals(fxemail.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;
        }
    }

    private boolean isValidEmail(String email) {
        // TODO: Ajouter une validation d'email plus avancée
        return email.matches(".+@.+\\..+");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String hashMotDePasse(String motDePasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(motDePasse.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
