/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project23.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class SignUpController implements Initializable {

    @FXML
    private AnchorPane fxFront;
    @FXML
    private TextField fxnom;
    @FXML
    private TextField fxprenom;
    @FXML
    private TextField fxemail;
    @FXML
    private PasswordField fxpassword;
    @FXML
    private Button btninscrit;
    @FXML
    private PasswordField confirmerPassword;
    @FXML
    private Button btncnx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void inscrit(ActionEvent event) {
    }


    @FXML
    private void connexionB(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
    }
    
}
