/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.esprit.entities.Evennement;
import edu.esprit.entities.Participe;
import edu.esprit.services.EventCrud;
import edu.esprit.services.SendMail;
import edu.esprit.utilis.MyConnection;
import static edu.esprit.utilis.MyConnection.getInstance;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ParicipeadminController implements Initializable {

    @FXML
    private TableView<Participe> tableadmin;
    @FXML
    private TableColumn<Participe, String> adnom;
    @FXML
    private TableColumn<Participe, String> adnum;
    @FXML
    private TableColumn<Participe, String> admail;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnsend;
    private SendMail sendMail;
  String query = null;
    MyConnection cnx = getInstance();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Evennement evn = null;
    EventCrud EC=new EventCrud();
    private ObservableList<Participe> parlist;
    @FXML
    private Button retour;
    @FXML
    private Button dec;
    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          try {
            ShowParticipe();
            
            parlist = getParlist();
        tableadmin.setItems(parlist);
         sendMail = new SendMail();
            
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    
}
    @FXML
    private void supprimer(ActionEvent event) {
        Participe selectedItem = tableadmin.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Create a PauseTransition for a 5-second delay
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                // Remove the selected item after the delay
                parlist.remove(selectedItem);
            });
            pause.play();
        }
    
    }
 @FXML
    private void send(ActionEvent event) {
        // Get the selected item
        Participe selectedItem = tableadmin.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Create a PauseTransition for a 5-second delay
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(e -> {
                // Send an email after the delay
                sendEmail(selectedItem.getMail());
            });
            pause.play();
        }
    }
     private void sendEmail(String to) {
        // Send an email using the SendMail instance
        String subject = "CmmuniHelp EVENTS";
        String message = "votre evenement aura lieu prochainement ";
        sendMail.sendMail(to, subject, message);
    }

    public void ShowParticipe() throws SQLException {
        ObservableList<Participe> parlist = getParlist();

        adnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adnum.setCellValueFactory(new PropertyValueFactory<>("num"));
        admail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        
        tableadmin.setItems(parlist);
    }

    private ObservableList<Participe> getParlist() throws SQLException {
        ObservableList<Participe> parlist = FXCollections.observableArrayList();

     String query = "SELECT nom, num, mail FROM participants";

    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            parlist.add(new Participe(
                rs.getString("nom"),
                rs.getString("num"),
                rs.getString("mail")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return parlist;
}
@FXML
    private void Retourtoeventadmin(ActionEvent event) { try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eventadmin.fxml"));
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
    }}

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
