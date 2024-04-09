/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.User;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

 
public class SignInController implements Initializable {

    @FXML
    private TextField fxemail;
    @FXML
    private TextField fxpassword;
    @FXML
    private Button btninscrit;
    @FXML
    private AnchorPane fxFront;
    @FXML
    private Button btnconnexion;
    @FXML
    private Hyperlink fxforgotpassword;
    @FXML
    private Button btninscritMedcin;
    @FXML
    private Button btninscritAssocia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
Connection DS;

    Statement ste;
    @FXML
    private void login(ActionEvent event) throws SQLException {
           String email = fxemail.getText();
           String pass =hashMotDePasse(fxpassword.getText());
       // String entPass= fxpassword.getText();
        DS = MyConnection.getInstance().getCnx();

        String req = "SELECT * FROM `user` WHERE email = '" + email + "' AND password = '" + pass + "'";
        ste = DS.createStatement();
        
        ResultSet result = ste.executeQuery(req);
        System.out.println("Email: " + email);
        System.out.println("Password: " + pass);

        if (result.next()) {

            String status = result.getString("status");
            if (status.equals("inactif")) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Votre compte a été désactivé par l'administrateur. Veuillez contacter l'administrateur pour plus d'informations.");
                alert.showAndWait();
            } else {
                int userId = result.getInt("id");
                String userName = result.getString("nom");
                String userprenom = result.getString("prenom");
                String useremail = result.getString("email");
                String userPassword = result.getString("password");
                String userRole = result.getString("roles");
                String userStatus = result.getString("status");
                String specialite =result.getString("specialite");
                String adresse = result.getString("adresse");
               

                User user = new User(userId, userName, userprenom, useremail, userPassword, userRole, userStatus);
                CurrentUser.setLoggedInUser(user);
                User loggedInUser = CurrentUser.getLoggedInUser();
                int loggedInUserId = loggedInUser.getId();
                System.out.println("Utilisateur connecté : " + loggedInUserId);
                if (userRole.equals("admin")) {
                    System.out.println("bien!");
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

                } else if (userRole.equals("user")) {

                    Stage home = new Stage();
                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("FaireDon2.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene);
                        home.show();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                else if (userRole.equals("medecin")) {

                    Stage home = new Stage();
                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("postsMed.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene);
                        home.show();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                else if (userRole.equals("association")) {

                    Stage home = new Stage();
                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("AddArticle.fxml"));
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
            alert.showAndWait();
        }
    }
    
   public String hashMotDePasse(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void inscription(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml); 
    }


    @FXML
    private void inscriptionMedecin(ActionEvent event) throws IOException {
       Parent fxml = FXMLLoader.load(getClass().getResource("SignUpMedecin.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
        
    }

    @FXML
    private void inscriptionAssociation(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("SignUpAssociation.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
    }

    @FXML
    private void Forgotpass(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader();//creation de FXMLLoader 
        loader.setLocation(getClass().getResource("ForgotPassword.fxml")); //emplacement du fichier fxml 
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ForgotPasswordController muc = loader.getController(); //recuperation deu controller de modification 
        //mrc.setUpdate(true);

        Parent parent = loader.getRoot();
        Stage stage = new Stage(); //affichage de la fenetre 
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    }

    
