/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class CommandeController implements Initializable {

    @FXML
    private TableView<?> Ctable;
    @FXML
    private TableColumn<?, ?> prodCo;
    @FXML
    private TableColumn<?, ?> contactCo;
    @FXML
    private TableColumn<?, ?> nomCo;
    @FXML
    private TableColumn<?, ?> emailCo;
    @FXML
    private TableColumn<?, ?> adresseCo;
    @FXML
    private Button annulerC;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void annulerC(ActionEvent event) {
    }

    @FXML
    private void swingtoS(ActionEvent event) {
    }
    
}
