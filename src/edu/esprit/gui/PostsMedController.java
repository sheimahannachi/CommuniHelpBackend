/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.User;
import edu.esprit.entities.enumMedecin;
import edu.esprit.entities.publications;
import edu.esprit.services.publicationsCrud;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PostsMedController implements Initializable {
    
  
     private int id_med ;
    @FXML
    private Label fxName;
    @FXML
    private Label fxemail;
    @FXML
    private Button dec;
    
    
    
    
    public void setId_med(int id_med) {
        this.id_med = id_med;
    }
    
    private String imagePath = null;
    
    private ObservableList<publications> publicationList;


  
    @FXML
    private ComboBox<String> cbSpecialite;
    @FXML
    private DatePicker dPub;
    @FXML
    private TextField tfTags;
   
   
    @FXML
    private Button bPublier;
    @FXML
    private Button bModifierPub;
    @FXML
    private Button bSupprimerPub;
    @FXML
    public TableView<publications> tablePub;
    @FXML
    private TextField tfTexte;
    
    @FXML
    private Button bimporter;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField imagePathTextField;
    //private ImageView imageCommuni;
    //private ImageView imageCommunity;
    //private ImageView imageDoctor;
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        User loggedInUser = CurrentUser.getLoggedInUser();
         fxemail.setText(loggedInUser.getEmail());
         fxName.setText(loggedInUser.getNom());
        
        String cheminImage = "/images/CommuniHelp.png";

        // Chargez l'image à partir du fichier
        Image image = new Image(getClass().getResourceAsStream(cheminImage));

        // Attribuez l'image à l'ImageView
        //imageCommunity.setImage(image);
        
        
         String cheminImage1 = "/images/logo.png";

        // Chargez l'image à partir du fichier
        Image image1 = new Image(getClass().getResourceAsStream(cheminImage1));

        // Attribuez l'image à l'ImageView
        //imageCommuni.setImage(image1);
        
        
           String cheminImage2 = "/images/doctor.png";

        // Chargez l'image à partir du fichier
        Image image2 = new Image(getClass().getResourceAsStream(cheminImage2));

        // Attribuez l'image à l'ImageView
        //imageDoctor.setImage(image2);
        
        
        
        
     ObservableList<String> specialiteList = FXCollections.observableArrayList(
        "PSYCHIATRE", "OPHTALMOLOGUE", "NEUROLOGUE", "GYNECOLOGUE", "DERMATOLOGUE", "CARDIOLOGUE", "PEDIATRE"
);
cbSpecialite.setItems(specialiteList);

      
        
        
         
       TableColumn<publications, String> texteColumn = new TableColumn<>("Texte");
        texteColumn.setCellValueFactory(new PropertyValueFactory<>("texte"));
        
       

     
        TableColumn<publications, LocalDate> dateDePublicationColumn = new TableColumn<>("Date de Publication");
        dateDePublicationColumn.setCellValueFactory(new PropertyValueFactory<>("dateDePublication"));

        TableColumn<publications, String> specialiteColumn = new TableColumn<>("Specialite ");
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<publications, String> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));
      
       TableColumn<publications, String> imagePathColumn = new TableColumn<>("image path");
        imagePathColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
       

        tablePub.getColumns().addAll( texteColumn, dateDePublicationColumn, specialiteColumn, tagsColumn,imagePathColumn);

           
        
     publicationList = FXCollections.observableArrayList();
        tablePub.setItems(publicationList);
        
        
        
        
         tablePub.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // If a new selection is made, fill the form fields with the values of the selected publication
            tfTexte.setText(newSelection.getTexte());
            dPub.setValue(newSelection.getDateDePublication());
            cbSpecialite.setValue(newSelection.getSpecialite());
            tfTags.setText(newSelection.getTags());

            // ... (set other fields if needed)
        } else {
            // If no selection is made, clear the form fields
            tfTexte.clear();
            dPub.setValue(null);
            cbSpecialite.setValue(null);
            tfTags.clear();

            // ... (clear other fields if needed)
        }
    });

        
        
    }    
private void showAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}

    @FXML
    private void ajoutPub(ActionEvent event) {
            
        
         String texte = tfTexte.getText();
      String specialite = cbSpecialite.getValue();
        
        String tags = tfTags.getText();
      
       
        LocalDate dateDePublication = dPub.getValue();
          // Vérification du champ titre
  
    // Vérification du champ texte
    if (texte.isEmpty()) {
        showAlert("Erreur", "Champ Texte Vide", "Le champ texte ne peut pas être vide.");
        return; // Arrête la méthode
    }

    // Vérification du champ tags
    if (!tags.matches("^#\\S+$")) {
        showAlert("Erreur", "Format de Tags Incorrect", "Les tags doivent commencer par '#' et ne pas contenir d'espaces.");
        return; // Arrête la méthode
    }
       
   //int id_med = 2;

    publications P = new publications( texte, dateDePublication, specialite, tags,imagePath,CurrentUser.getLoggedInUser());
    String imagePath = imagePathTextField.getText();
    P.setImagePath(imagePath);
    P.setId_med(CurrentUser.getLoggedInUser());
    
    publicationsCrud pub = new publicationsCrud();
    
    pub.ajouterpublications(P);

    publicationList.add(P);
    
    
    tfTexte.clear();
    dPub.setValue(null);
    cbSpecialite.setValue(null);
    tfTags.clear();
    imageView.setImage(null);
    imagePathTextField.clear();
}      
    

    @FXML
    private void ModifierPub(ActionEvent event) {
        publications selectedPublication = tablePub.getSelectionModel().getSelectedItem();
    
    if (selectedPublication != null) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Modify Publication");
        confirmationAlert.setContentText("Are you sure you want to modify this publication?");
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            // Retrieve the modified data from the input fields
           
            String texte = tfTexte.getText();
            LocalDate dateDePublication = dPub.getValue();
      String specialite = cbSpecialite.getValue();
            String tags = tfTags.getText();
            

            // Update the selected publication directly in the TableView
            
            selectedPublication.setTexte(texte);
            selectedPublication.setDateDePublication(dateDePublication);
            selectedPublication.setSpecialite(specialite);
            selectedPublication.setTags(tags);
            
            
    

    // Vérification du champ texte
    if (tfTexte.getText().isEmpty()) {
        showAlert("Erreur", "Champ Texte Vide", "Le champ texte ne peut pas être vide.");
        return; // Arrête la méthode
    }

    // Vérification du champ tags
    if (!tfTags.getText().matches("^#\\S+$")) {
        showAlert("Erreur", "Format de Tags Incorrect", "Les tags doivent commencer par '#' et ne pas contenir d'espaces.");
        return; // Arrête la méthode
    }
  int id_med = selectedPublication.getId_med().getId();
            // Update the database with the modified data using your CRUD method
            publicationsCrud pub = new publicationsCrud();
             int idPublicationAModifier = selectedPublication.getId();
            pub.modifierpublications(selectedPublication, idPublicationAModifier,id_med);

            // Clear the input fields after the modification
          
            tfTexte.clear();
            dPub.setValue(null);
            cbSpecialite.setValue(null);
            tfTags.clear();
            

            // Refresh the TableView to see the modifications
            tablePub.refresh();
        }
   
            
            
      
    }
        
        
        
        
    }

    


@FXML
private void SupprimerPub(ActionEvent event) {
    publications selectedPublication = tablePub.getSelectionModel().getSelectedItem();
    
    if (selectedPublication != null) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Publication");
        alert.setContentText("Are you sure you want to delete this publication?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
              int id_med = selectedPublication.getId_med().getId();
            // User confirmed deletion, call your CRUD method to delete the publication from the database
            publicationsCrud pub = new publicationsCrud();
            pub.supprimerpublications(selectedPublication.getId());

            // Now you can remove the selected item from the TableView
            tablePub.getItems().remove(selectedPublication);
        }
    }
}
    

    @FXML
    private void parcourirImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    
    // Show the file chooser dialog
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        // Load the selected image and set it in the ImageView
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
        
        String imagePath = selectedFile.getAbsolutePath();
        imagePathTextField.setText(imagePath);
        
        
        publicationsCrud pub = new publicationsCrud();
        publications selectedPublication = tablePub.getSelectionModel().getSelectedItem();
        if (selectedPublication != null) {
            imagePathTextField.setText(selectedFile.getAbsolutePath());
            pub.modifierpublications(selectedPublication, selectedPublication.getId(),2);
        }
    } else {
        
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annulation de la sélection");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez annulé la sélection du fichier. Continuer ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose to continue
            // You can add additional handling if needed
        } else {
            // User canceled the action
            // You can add additional handling or do nothing
        }
       
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


    
