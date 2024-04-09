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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class EventController implements Initializable {

    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfnom;
    @FXML
    private ComboBox<?> tfcombo;
    @FXML
    private Button ajoutbtn;
    @FXML
    private Button modifbtn;
    @FXML
    private Button supprimbtn;
    @FXML
    private TableView<?> view_event;
    @FXML
    private TableColumn<?, ?> nom_column;
    @FXML
    private TableColumn<?, ?> lieu_column;
    @FXML
    private TableColumn<?, ?> date_column;
    @FXML
    private Button btn_img;
    @FXML
    private AnchorPane paneh;
    @FXML
    private ImageView imgv;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterEvent(ActionEvent event) {
    }

    @FXML
    private void modifierEvent(ActionEvent event) {
    }

    @FXML
    private void supprimerEvent(ActionEvent event) {
    }

    @FXML
    private void btn_img(ActionEvent event) {
    }
    
}
