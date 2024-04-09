/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;


import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import javafx.stage.FileChooser;
import edu.esprit.entities.enumMedecin;
import edu.esprit.entities.publications;
import edu.esprit.services.publicationsCrud;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class sheimadmin implements Initializable {

    private final publicationsCrud pubCrud = new publicationsCrud();  

  private ObservableList<publications> publicationList;
    @FXML
    private Button bimporter1;
    @FXML
    private Button bModifierPub1;
    @FXML
    private Button bSupprimerPub1;
    @FXML
    private ComboBox<String> cbSpecialite1;
    @FXML
    private TextField tfTexte1;
    @FXML
    private DatePicker dPub1;
    @FXML
    private TextField tfTags1;
    @FXML
    private TextField imagePathTextField;
    @FXML
    private TableView<publications> tablePub1;
    @FXML
    private ImageView imageView1;
    @FXML
    private Button appeldonbtn3;
    @FXML
    private Button eventbtn3;
    @FXML
    private Button espaceadminbtn3;
    @FXML
    private Button historiquebtn3;
    @FXML
    private Button retourbtn3;
    @FXML
    private Button postsMedecin3;
    @FXML
    private AnchorPane fxFront;
    @FXML
    private Button dec;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tablePub1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Récupérez la publication sélectionnée
        publications selectedPublication = tablePub1.getSelectionModel().getSelectedItem();

        // Affichez les données de la publication sélectionnée dans les zones de texte et autres contrôles
        tfTexte1.setText(selectedPublication.getTexte());
        dPub1.setValue(selectedPublication.getDateDePublication());
        cbSpecialite1.setValue(selectedPublication.getSpecialite());
        tfTags1.setText(selectedPublication.getTags());
    } else {
        // Si aucune ligne n'est sélectionnée, effacez les contrôles
        tfTexte1.clear();
        dPub1.setValue(null);
        cbSpecialite1.setValue(null);
        tfTags1.clear();
    }
});
        
        
        
        
        
        
        
            ObservableList<String> specialiteList = FXCollections.observableArrayList(
        "PSYCHIATRE", "OPHTALMOLOGUE", "NEUROLOGUE", "GYNECOLOGUE", "DERMATOLOGUE", "CARDIOLOGUE", "PEDIATRE"
);
cbSpecialite1.setItems(specialiteList);


      
        
        
         
       TableColumn<publications, String> texteColumn = new TableColumn<>("Texte");
        texteColumn.setCellValueFactory(new PropertyValueFactory<>("texte"));
        
       

     
        TableColumn<publications, LocalDate> dateDePublicationColumn = new TableColumn<>("Date de Publication");
        dateDePublicationColumn.setCellValueFactory(new PropertyValueFactory<>("dateDePublication"));

        TableColumn<publications,String> specialiteColumn = new TableColumn<>("specialite");
        specialiteColumn.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        TableColumn<publications, String> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));
      
       TableColumn<publications, String> imagePathColumn = new TableColumn<>("image path");
        imagePathColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
       

        tablePub1.getColumns().addAll( texteColumn, dateDePublicationColumn, specialiteColumn, tagsColumn,imagePathColumn);

           
        
     publicationList = FXCollections.observableArrayList();
        tablePub1.setItems(publicationList);
        
        
        publicationList = FXCollections.observableArrayList();
        tablePub1.setItems(publicationList);

        chargerDonneesDansTableView(); 
        
        
    } 
    
    
    
    
    
    private void chargerDonneesDansTableView() {
        List<publications> publications = pubCrud.afficherpublications(); // Appel de la méthode pour obtenir les publications

        publicationList.clear(); // Effacez les données existantes de la liste
        publicationList.addAll(publications); // Ajoutez les données de la base de données à la liste
    }
    
    
    
    
        private void showAlert(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}
    
        // TODO
       

    @FXML
    private void parcourirImage1(ActionEvent event) {
              FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    
    // Show the file chooser dialog
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        // Load the selected image and set it in the ImageView
        Image image = new Image(selectedFile.toURI().toString());
        imageView1.setImage(image);
        
        String imagePath = selectedFile.getAbsolutePath();
        imagePathTextField.setText(imagePath);
        
        
        publicationsCrud pub = new publicationsCrud();
        publications selectedPublication = tablePub1.getSelectionModel().getSelectedItem();
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
    private void ModifierPub1(ActionEvent event) {
                publications selectedPublication = tablePub1.getSelectionModel().getSelectedItem();
    
    if (selectedPublication != null) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Modify Publication");
        confirmationAlert.setContentText("Are you sure you want to modify this publication?");
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            // Retrieve the modified data from the input fields
           
            String texte = tfTexte1.getText();
            LocalDate dateDePublication = dPub1.getValue();
            String specialite = cbSpecialite1.getValue();
            String tags = tfTags1.getText();
            

            // Update the selected publication directly in the TableView
            
            selectedPublication.setTexte(texte);
            selectedPublication.setDateDePublication(dateDePublication);
             selectedPublication.setSpecialite(specialite);
            selectedPublication.setTags(tags);
            
            
    

    // Vérification du champ texte
    if (tfTexte1.getText().isEmpty()) {
        showAlert("Erreur", "Champ Texte Vide", "Le champ texte ne peut pas être vide.");
        return; // Arrête la méthode
    }

    // Vérification du champ tags
    if (!tfTags1.getText().matches("^#\\S+$")) {
        showAlert("Erreur", "Format de Tags Incorrect", "Les tags doivent commencer par '#' et ne pas contenir d'espaces.");
        return; // Arrête la méthode
    }
 int id_med = selectedPublication.getId_med().getId();
            // Update the database with the modified data using your CRUD method
            publicationsCrud pub = new publicationsCrud();
             int idPublicationAModifier = selectedPublication.getId();
            pub.modifierpublications(selectedPublication, idPublicationAModifier,id_med);

            // Clear the input fields after the modification
          
            tfTexte1.clear();
            dPub1.setValue(null);
            cbSpecialite1.setValue(null);
            tfTags1.clear();
            

            // Refresh the TableView to see the modifications
            tablePub1.refresh();
        }
    }}

           @FXML
           private void SupprimerPub1(ActionEvent event) {
          publications selectedPublication = tablePub1.getSelectionModel().getSelectedItem();
    
    if (selectedPublication != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Publication");
        alert.setContentText("Are you sure you want to delete this publication?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
             int id_med =   selectedPublication.getId_med().getId();
            // User confirmed deletion, call your CRUD method to delete the publication from the database
            publicationsCrud pub = new publicationsCrud();
            pub.supprimerpublications(selectedPublication.getId());

            // Now you can remove the selected item from the TableView
            tablePub1.getItems().remove(selectedPublication);
        }
    }}

    @FXML
    private void donaction(ActionEvent event) {
            try {
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
    private void eventaction(ActionEvent event) {
            try {
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
    }

    }

    @FXML
    private void adminaction1(ActionEvent event) {
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
    private void historyaction(ActionEvent event) {
            try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("consulterHistorique.fxml"));
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
    private void aaaaa(ActionEvent event) {
         try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
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
