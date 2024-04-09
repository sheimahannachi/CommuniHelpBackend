/*



 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;


import edu.esprit.entities.Evennement;
import edu.esprit.services.EventCrud;
import edu.esprit.utilis.MyConnection;
import static edu.esprit.utilis.MyConnection.getInstance;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ParicipeController implements Initializable {

    @FXML
    private TableView<Evennement> table;
    @FXML
    private TableColumn<Evennement, String> tfidp;
    @FXML
    private TableColumn<Evennement, String> tflp;
    @FXML
    private TableColumn<Evennement, String> tfpd;
     String query = null;
    MyConnection cnx = getInstance();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Evennement evn = null;
    EventCrud EC=new EventCrud();
    
    int x;
    @FXML
    private Button participebt;
    @FXML
    private Label labelParticipants;
    @FXML
    private Button btn_voir;
    @FXML
    private ImageView imgview;
    @FXML
    private Button btndon;
    @FXML
    private Button btnboutique;
    @FXML
    private Button btnmed;
    @FXML
    private Button dec;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
            ShowEvennement();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    
}
     public void ShowEvennement () throws SQLException {
         
        
         ObservableList< Evennement> Eventlist =getEventlist() ;
       
       
          
       
        tfidp.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tflp.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        tfpd.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        
        table.setItems(Eventlist);
        
    
    
}
    private ObservableList<Evennement> getEventlist() throws SQLException {
         
          
      ObservableList<Evennement> eventlist =FXCollections.observableArrayList();
     
   
     String query = "SELECT * FROM test";
     
      try {
          PreparedStatement pst = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query);
           
              ResultSet rs =pst.executeQuery(query);
            
            while (rs.next()){
                eventlist.add(new Evennement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("lieu"),
                        rs.getDate("date").toLocalDate()));
              
                table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        int eventId = newSelection.getid(); // Obtenez l'ID de l'événement sélectionné
        int participantCount = countParticipantsForEvent(eventId); // Comptez les participants
        
        // Affichez le nombre de participants dans le Label
        labelParticipants.setText("Nombre de participants : " + participantCount);
        
        
    }
});
                
                      
            }
      }catch (SQLException e) {
            e.printStackTrace();
        }
     
        return eventlist;
        // TODO
    }  

    @FXML
    
private void participebtn(MouseEvent event) {
    Evennement selectedEvent = table.getSelectionModel().getSelectedItem();
    
    
    /*
    * kol participent yetzed taml if ezlse tchouf nombre de participent fet taille taille participe event walee
    */
    if (selectedEvent != null) {
        int eventId = selectedEvent.getid();
        
        // Récupérez la scène actuelle depuis le bouton "Participer"
        Scene currentScene = ((Node) event.getSource()).getScene();
        
        // Utilisez eventId pour compter le nombre de participants
        int participantCount = countParticipantsForEvent(eventId);
        
        if (participantCount < 2) {
            // Chargez la page "inscrit.fxml"
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("inscrit.fxml"));
                Parent root = loader.load();
                
                // Passez l'ID de l'événement au contrôleur de la page "inscrit.fxml"
                InscritController inscritController = loader.getController();
                inscritController.setid(eventId);
                
                // Créez une nouvelle scène
                Scene newScene = new Scene(root);
                
                // Obtenez la fenêtre (stage) à partir de la scène actuelle
                Stage stage = (Stage) currentScene.getWindow();
                
                // Affichez la nouvelle scène
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            
    
       //ImageView image;
       //Image myImage = new Image(getClass().getResourceAsStream("/images/retour"));
      
        
        
  // Affichez un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de participer");
            alert.setContentText("Le nombre de participants est supérieur ou égal à 2. Vous ne pouvez pas participer.");
            alert.showAndWait();

        }
    }
}


    @FXML
    private void fonction_voir(ActionEvent event) {
    Evennement selectedEvent = table.getSelectionModel().getSelectedItem();

    if (selectedEvent != null) {
        int eventId = selectedEvent.getid(); // Récupérez l'ID de l'événement sélectionné

        // Utilisez eventId pour compter le nombre de participants correspondant
        int participantCount = countParticipantsForEvent(eventId);

        // Affichez le nombre de participants dans le Label
        labelParticipants.setText("Nombre de participants : " + participantCount);
    }
}

// Méthode pour compter les participants pour un événement donné
private int countParticipantsForEvent(int eventId) {
    int count = 0;

    try {
        // Effectuez une requête pour compter les participants avec le même id_ev
        String query = "SELECT COUNT(*) FROM participants WHERE id_ev = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setInt(1, eventId);

        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return count;
}

    @FXML
    private void showimg(MouseEvent event) {
        Evennement e = table.getSelectionModel().getSelectedItem();
    int eventId = e.getid();
    int participantCount = countParticipantsForEvent(eventId);

    if (participantCount < 2) {
        // ParticipantCount est inférieur à 2, affichez l'image de l'ID sélectionné
        String path = getPathForEvent(eventId);
        if (path != null) {
            File imageFile = new File(path);
            String fileUri = imageFile.toURI().toString();
            Image image = new Image(fileUri);
            imgview.setImage(image);
        }
    } else {
        // ParticipantCount est supérieur ou égal à 2, affichez l'image "com.jpg" du package "image"
        String imagePath = "file:C:\\Users\\Lenovo\\Desktop\\wetransfer_projet_2023-10-21_2334\\Java-main\\src\\images\\retour.jpg"; // Spécifiez le chemin correct
        Image image = new Image(imagePath);
        imgview.setImage(image);
        
    }
       /* Evennement e = table.getSelectionModel().getSelectedItem();
        System.out.println(e.getid());
         ResultSet resultSet = null;
 String sql = "SELECT path FROM test WHERE id = ?";
PreparedStatement statement = null;
try {
    statement = MyConnection.getInstance().getCnx().prepareStatement(sql);
    statement.setInt(1, e.getid());
    ResultSet result = statement.executeQuery();

    String path = null;

    if (result.next()) {
        path = result.getString("path");
        System.out.println(path);
        File imageFile = new File(path);
        String fileUri = imageFile.toURI().toString();
         Image image = new Image(fileUri);
         imgview.setImage(image);
    }
} catch (SQLException ex) {
    Logger.getLogger(ParicipeController.class.getName()).log(Level.SEVERE, null, ex);
} finally {
    if (statement != null) {
        try {
            statement.close();
        } catch (SQLException ex) {
            // Handle any exceptions when closing the statement
            ex.printStackTrace();
        }
    }*/}
private String getPathForEvent(int eventId) {
    String path = null;
    String sql = "SELECT path FROM test WHERE id = ?";
    PreparedStatement statement = null;
    ResultSet result = null;

    try {
        statement = MyConnection.getInstance().getCnx().prepareStatement(sql);
        statement.setInt(1, eventId);
        result = statement.executeQuery();

        if (result.next()) {
            path = result.getString("path");
        }
    } catch (SQLException ex) {
        Logger.getLogger(ParicipeController.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    return path;
}
    

    

    @FXML
    private void btndonaction(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FaireDon2.fxml"));
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
    private void btnboutiqueaction(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("livraison1test.fxml"));
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
    private void btnmedaction(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userPosts.fxml"));
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

    

