/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package shop.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ShopwindController implements Initializable {

    @FXML
    private TextField prod_nom;
    @FXML
    private TextField prod_prix;
    @FXML
    private TextField desc_prod;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnImporter;
    @FXML
    private ComboBox<?> statut_prod;
    @FXML
    private Button vider;
    @FXML
    private TableView<?> product_view;
    @FXML
    private TableColumn<?, ?> prodcol_nom;
    @FXML
    private TableColumn<?, ?> prodcol_prix;
    @FXML
    private TableColumn<?, ?> prod_coldesc;
    @FXML
    private TableColumn<?, ?> prodcol_statut;
    @FXML
    private Button swingtoL;
    @FXML
    private Button retriev;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleImportButtonAction(ActionEvent event) {
    }

    @FXML
    private void saveproducts(ActionEvent event) {
    }

    @FXML
    private void updateproducts(ActionEvent event) {
    }

    @FXML
    private void viderbtn(ActionEvent event) {
    }

    @FXML
    private void deleteproduct(ActionEvent event) {
    }

    @FXML
    private void swingtoL(ActionEvent event) {
    }

    @FXML
    private void retrieveProdadminDataButton(ActionEvent event) {
    }
    
}
