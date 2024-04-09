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
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ModifierUtilisateurController implements Initializable {

    @FXML
    private Button btnModifier;
    @FXML
    private TextField fxnom;
    @FXML
    private TextField fxprenom;
    @FXML
    private TextField fxemail;
    @FXML
    private TextField fxnum;
    @FXML
    private TextField fxadresse;
    @FXML
    private TextField fxrole;
    @FXML
    private TextField fxid;
    @FXML
    private AnchorPane fxFront;
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
public void setUserData(User user) {
        // Vérifiez le rôle de l'utilisateur
        if (user != null) {
            String role = user.getRoles();
            // Si le rôlE est "MEDECIN", affichez le ComboBox
            if ("medecin".equalsIgnoreCase(role)) {
                fxspecialite.setVisible(true);
            } else if ("Association".equalsIgnoreCase(role)){
                // Si ce n'est pas un admin, masquez le ComboBox
                fxprenom.setVisible(false);
                fxspecialite.setVisible(false);
                
            }
            else{
                fxspecialite.setVisible(false);
                
            }
        }
    }
    @FXML
    private void Modifieruser(ActionEvent event) {
        
          Integer id=Integer.parseInt(fxid.getText());
         Integer num_tel=Integer.parseInt(fxnum.getText());
         String nom= fxnom.getText();
         String prenom= fxprenom.getText();
         String roles = fxrole.getText();
         String adresse= fxadresse.getText();
         String email= fxemail.getText();
         String spec =fxspecialite.getValue();
          UserCrud rec= new UserCrud();
         User R = new User(id, email, nom, prenom, spec,adresse, num_tel, roles);
           rec.modifierUtilisateur(R);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(" :: Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Utilsateur modifié");
                alert.showAndWait(); 
                Stage home = new Stage();

                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene);
                        home.show();
                        home.close();
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
             

    }
     public void setText(User user) //modifier les donnes eli hatynehom 
    {
     
        String id =String.valueOf(user.getId()); //transformation : convertion de la valeur  du id vers un string 
        
        fxnom.setText(user.getNom());
        fxprenom.setText(user.getPrenom());
        fxspecialite.setValue(user.getSpecialite());
        fxadresse.setText(user.getAdresse());
        String num =String.valueOf(user.getNum_tel());
        fxnum.setText(num);
        fxemail.setText(user.getEmail());
     
    }

    void setTextFields(User user) ///taabili le fenetre bel les donnes 
    {
       fxid.setText(String.valueOf(user.getId()));
       fxnom.setText(user.getNom());
       fxprenom.setText(user.getPrenom());
       fxemail.setText(user.getEmail());
        fxnum.setText(String.valueOf(user.getNum_tel()));
        fxadresse.setText(user.getAdresse());
        fxrole.setText(user.getRoles());
        fxspecialite.setValue(user.getSpecialite());
    }

    @FXML
    private void close(MouseEvent event) { 
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    
    
}
