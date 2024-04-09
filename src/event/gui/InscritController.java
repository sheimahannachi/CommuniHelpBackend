/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package event.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class InscritController implements Initializable {

    @FXML
    private TextField nomp;
    @FXML
    private TextField nump;
    @FXML
    private TextField mailp;
    @FXML
    private Button ajoutp;
    @FXML
    private Label labelid;
    @FXML
    private ImageView ret_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterparti(ActionEvent event) {
    }

    @FXML
    private void btn_retour(MouseEvent event) {
    }
    
}
