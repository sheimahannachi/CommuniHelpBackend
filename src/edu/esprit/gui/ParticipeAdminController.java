package edu.esprit.gui;

import edu.esprit.entities.Participe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ParticipeAdminController implements Initializable {

    @FXML
    private TableView<Participe> tableadmin;
    @FXML
    private TableColumn<Participe, String> adnom;
    @FXML
    private TableColumn<Participe, String> adnum;
    @FXML
    private TableColumn<Participe, String> admail;
    @FXML
    private TableColumn<Participe, Integer> idev;
    @FXML
    private Button adminsup;
    @FXML
    private Button adminmodif;

    PreparedStatement pst = null;
    ResultSet rs = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

   

    @FXML
    private void adminsup(ActionEvent event) {
        // Code pour supprimer l'élément sélectionné
    }

    @FXML
    private void adminmodif(ActionEvent event) {
        // Code pour modifier l'élément sélectionné
    }
}
