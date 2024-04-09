/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.User;
import edu.esprit.services.UserCrud;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


public class AjouterController implements Initializable {

    @FXML
    private TextField fxprenom;
    @FXML
    private TextField fxnom;
    @FXML
    private TextField fxemail;
    private TextField fxcin;
    @FXML
    private TextField fxnum;
    @FXML
    private TextField fxadresse;
    private TextField fxroles;
    @FXML
    private Button btnaj;
    @FXML
    private PasswordField fxpassword;
    
    @FXML
    private ComboBox<String> fxspecialite;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         ObservableList<String> list =FXCollections.observableArrayList("PEDIATRE","CARDIOLOGUE","DERMATOLOGUE","GYNECOLOGUE","NEUROLOGUE","OPHTALMOLOGUE","PSYCHIATRE");
        fxspecialite.setItems(list);
    }    

     
    @FXML
    private void ajouter(ActionEvent event) {
        String spe = fxspecialite.getValue();
        Integer num_tel = Integer.parseInt(fxnum.getText());
           if (fxnom.getText().isEmpty() ||
                   fxprenom.getText().isEmpty()||
                   fxemail.getText().isEmpty() ||
                    fxnum.getText().isEmpty() || 
                   fxadresse.getText().isEmpty()||
                   fxspecialite.getValue().isEmpty() ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Champs invalides ! ", ButtonType.OK);
            a.showAndWait();
           
        } else if (fxpassword.getText().length()<8)
        {
             Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe doit etre >8 caractéres !)", ButtonType.OK);
              a.showAndWait();
        }
          else {
                 String hashedPassword = hashMotDePasse(fxpassword.getText());
                  UserCrud us = new UserCrud();
                  User p = new User(fxemail.getText(), fxnom.getText(), fxprenom.getText(), hashedPassword, spe, fxadresse.getText(), num_tel,"medecin","actif");
                  us.ajouterUtilisateur2(p);
                  Alert a = new Alert(Alert.AlertType.INFORMATION, "Utilisateur ajouté(e) avec succes !", ButtonType.OK);
                  a.showAndWait();
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
   

