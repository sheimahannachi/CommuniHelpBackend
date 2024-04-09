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
import static java.lang.Math.E;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.Debug.id;
import static org.omg.CORBA.AnySeqHelper.id;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class EventController implements Initializable {

    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfnom;
    @FXML
    private ComboBox<String> tfcombo;
    private String[] typee ={"aaaa","bbbb","cccc"};
    @FXML
    private Button ajoutbtn;
    @FXML
    private Button modifbtn;
    @FXML
    private Button supprimbtn;
    @FXML
    private TableView<Evennement> view_event;
    @FXML
    private TableColumn<Evennement, String> nom_column;
    @FXML
    private TableColumn<Evennement, String> lieu_column;
    @FXML
    private TableColumn<Evennement, String> date_column;
     String query = null;
    MyConnection cnx = getInstance();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Evennement evn = null;
    EventCrud EC=new EventCrud();
    private Pane pane;
       String path ;
    @FXML
    private ImageView imgv;
    @FXML
    private Button btn_img;
    @FXML
    private AnchorPane paneh;
    private TextField country= new TextField();
    @FXML
    private Button btndon;
    @FXML
    private Button boutiqueid;
    @FXML
    private Button dec;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfcombo.getItems().addAll("Tunis", "Bizerte", "Nabeul");
         try {
            ShowEvennement();
             
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
           
        }
          //country c=new country();
        //  c.country1();

     
    }
   public String GetCountry(){
        return country.getText();
    }
   

    @FXML
    private void ajouterEvent(ActionEvent event) {
      
       String nom = tfnom.getText();
        String lieu = (String) tfcombo.getValue();
        LocalDate date = tfdate.getValue();
       
       Evennement E = new Evennement(1,nom,lieu,date);
        EventCrud ev = new EventCrud();
        System.out.println(path);
        ev.ajouterEvent(E,path);
        
        
        try {
            ShowEvennement();
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void modifierEvent(ActionEvent event) {
        Evennement e =view_event.getSelectionModel().getSelectedItem();
          String nom = tfnom.getText();
        String lieu = (String) tfcombo.getValue();
        LocalDate date = tfdate.getValue();
        Evennement E = new Evennement(e.getid(),nom,lieu,date);
       
      
        EC.modifierEvent(E);
       
        try {
            ShowEvennement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void supprimerEvent(ActionEvent event) {
        Evennement e = view_event.getSelectionModel().getSelectedItem();

    if (e == null) {
        // Aucun événement sélectionné, affichez un message d'erreur
        showAlert("Erreur", "Sélection d'événement", "Veuillez sélectionner un événement à supprimer.");
        return;
    }

    // Afficher une boîte de dialogue de confirmation
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
    alert.setHeaderText("Supprimer l'événement ?");
    alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

    // Attendre la réponse de l'utilisateur
    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

    if (result == ButtonType.OK) {
        // L'utilisateur a confirmé la suppression
        EC.supprimerEvent(e.getid());

        try {
            ShowEvennement();
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

private void showAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}
    
    
    
    public void ShowEvennement () throws SQLException {
         
        
         ObservableList< Evennement> Eventlist =getEventlist() ;
       
       
          
       
        nom_column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lieu_column.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        view_event.setItems(Eventlist);
    
    
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
                
                      
            }
      }catch (SQLException e) {
            e.printStackTrace();
        }
     
        return eventlist;
         
     }

    @FXML
    private void btn_img(ActionEvent event) {
        EventCrud aa = new EventCrud();
        importerImg(paneh,imgv);
        
    }
     
public String importerImg (AnchorPane paneh, ImageView imgv){
     FileChooser open = new FileChooser();
     open.getExtensionFilters().add(new FileChooser.ExtensionFilter("open Image File","*png","*jpg")) ;
     File file = open.showOpenDialog(paneh.getScene().getWindow());
     if (file !=null){
         Image image = new Image (file.toURI().toString(),500,500,false,true);
         imgv.setImage(image);
         path=file.getAbsolutePath();
      
       
         
     }
     return null;
 }

    @FXML
    private void btndonaction(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
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
    private void boutiqueaction(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopwind.fxml"));
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
    private void dec1(ActionEvent event) {
         try {
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
