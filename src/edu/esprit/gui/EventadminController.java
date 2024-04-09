package edu.esprit.gui;

import edu.esprit.entities.Evennement;
import edu.esprit.services.EventCrud;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EventadminController implements Initializable {

    @FXML
    private TableView<Evennement> tableadmin;
    @FXML
    private TableColumn<Evennement, String> adnom;
    @FXML
    private TableColumn<Evennement, String> adlieu;
    @FXML
    private TableColumn<Evennement, String> admdate;
    @FXML
    private Button adminsup;
    EventCrud EC = new EventCrud();
    private Evennement selectedEvennement;
    @FXML
    private Button boutique;
    @FXML
    private Button don;
    @FXML
    private Button participants;
    @FXML
    private Button retour;
    @FXML
    private Button dec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Initialise le TableView avec les données des événements
        adnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adlieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        admdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableadmin.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        selectedEvennement = newSelection;
    }
});
        try {
            ShowEvennement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void admin_supp(ActionEvent event) {
     if (selectedEvennement != null) {
        // Create a PauseTransition of 5 seconds to delay the deletion
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            // Remove the selected item from the TableView
            tableadmin.getItems().remove(selectedEvennement);
            
            // Delete the selected event from the database
            EC.supprimerEvent(selectedEvennement.getid()); // You need to implement the supprimerEvent method
            
            selectedEvennement = null; // Reset the selectedEvennement
        });
        pause.play(); // Start the pause
    }
    }

    public void ShowEvennement() throws SQLException {
        ObservableList<Evennement> Eventlist = getEventlist();
        tableadmin.setItems(Eventlist);
    }

    private ObservableList<Evennement> getEventlist() throws SQLException {
        ObservableList<Evennement> eventlist = FXCollections.observableArrayList();

        String query = "SELECT * FROM test";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
             ResultSet rs = pst.executeQuery(query)) {
            while (rs.next()) {
                eventlist.add(new Evennement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("lieu"),
                        rs.getDate("date").toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventlist;
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void boutiqueaction(ActionEvent event) { 
         try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminshop.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
        
    }
    }
    @FXML
    private void donaction(ActionEvent event) { try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin2.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    @FXML
    private void participeaction(ActionEvent event) throws IOException {
         try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Paricipeadmin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
        
        
       
    }

    
    }
    @FXML
    private void retouraction(ActionEvent event) { try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    @FXML
    private void dec1(ActionEvent event) { try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignIn.fxml"));
        Parent root = loader.load();

        // Create a new stage and show the FaireDon.fxml
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Close the current stage (Recu.fxml)
        ((Node) (event.getSource())).getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    }
}