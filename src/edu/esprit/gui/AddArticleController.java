package edu.esprit.gui;

import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import edu.esprit.entities.Article;
import edu.esprit.services.articleCRUD;
import edu.esprit.utilis.MyConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class AddArticleController implements Initializable {
    @FXML
    private Button btnClear;
    @FXML
    private Button btnImporter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnPublier;

    @FXML
    private Button btnSupprimer;
    @FXML
    private TextField tfVille;

    @FXML
    private TextField tfContact;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfDescription;

    @FXML
    private TableColumn<?, ?> villeColId;
    @FXML
    private TableColumn<?, ?> descriptionColId;

    @FXML
    private TableColumn<?, ?> contactColId;

    @FXML
    private TableColumn<?, ?> creationdateColId;

    @FXML
    private TableView<Article> tableId;
    @FXML
    private ImageView imageView;

    private String imagePath = "";

     Article selectedArticle; 
    @FXML
    private Button btnImporter1;
    @FXML
    private Button FaireUnDonHandleButton;
    @FXML
    private Button btnevent;
    @FXML
    private Button histor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        villeColId.setCellValueFactory(new PropertyValueFactory<>("ville"));
        descriptionColId.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactColId.setCellValueFactory(new PropertyValueFactory<>("contact"));
        creationdateColId.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        // Call the method to populate the table
        populateTable();

        tableId.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableId.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Display the selected data in the text fields
                selectedArticle = newSelection;
                tfVille.setText(selectedArticle.getVille());
                tfDescription.setText(selectedArticle.getDescription());
                tfContact.setText(selectedArticle.getContact());
                LocalDate date = selectedArticle.getCreationDate();

                // Display the selected date in the DatePicker
                tfDate.setValue(date);
                // Display the selected image in the ImageView
                String selectedImage = selectedArticle.getImage();
                if (selectedImage != null) {
                    Image image = new Image(new File(selectedImage).toURI().toString());
                    imageView.setImage(image);
                }
            }
        });

    }

    public void populateTable() {
        // Clear existing items from the table
        tableId.getItems().clear();

        // Retrieve data from your data source (database or elsewhere)
        articleCRUD articleCRUDInstance = new articleCRUD();
        List<Article> articles = articleCRUDInstance.getArticles();

        // Convert the list of articles to an ObservableList
        ObservableList<Article> articleList = FXCollections.observableArrayList(articles);

        // Add the data to the TableView
        tableId.setItems(articleList);
    }
    
    
    
    
    
    
    
    @FXML
    public void handleDonButtonAction(ActionEvent event) {
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
    public void handlePublierButtonAction(ActionEvent event) {
    String ville = tfVille.getText();
    String description = tfDescription.getText();
    String contact = tfContact.getText();
    LocalDate date = tfDate.getValue();

    // Check if both fields are not empty
    if (ville.isEmpty() || description.isEmpty() || contact.isEmpty() || date == null) {
        // Show an error message for missing information
        showValidationError("Missing Information", "Please fill in all the required fields.");
    } else if (!isValidPhoneNumber(contact)) {
        // Show an error message for an invalid phone number
        showValidationError("Invalid Phone Number", "Phone number must be 8 digits.");
    } else if (isPhoneNumberExists(contact)) {
        // Show an error message for duplicate phone numbers
        showValidationError("Duplicate Phone Number", "Phone number already exists.");
    } else {
        // Create a Personne object
        Article newArticle = new Article(ville, description, contact, date, imagePath);
        // Create an instance of PersonneCRUD
        articleCRUD aC = new articleCRUD();

        // Use the instance to add the object to your data source
        aC.ajouterArticle(newArticle);

        // Add the new article to the TableView
        ObservableList<Article> articleList = (ObservableList<Article>) tableId.getItems();
        articleList.add(newArticle);
        // Optionally, clear the input fields after adding
        tfVille.clear();
        tfDescription.clear();
        tfContact.clear();
        tfDate.setValue(null);
        imageView.setImage(null); // Clear the ImageView
        imagePath = ""; // Provide feedback to the user
    }
}

// Custom method to validate a phone number
private boolean isValidPhoneNumber(String phoneNumber) {
    // Check if the phone number is exactly 8 digits
    return phoneNumber.matches("\\d{8}");
}

  public boolean isPhoneNumberExists(String phoneNumber) {
        MyConnection myConnection = MyConnection.getInstance();
        Connection cnx = myConnection.getCnx(); // Get the connection instance

        String query = "SELECT * FROM article WHERE contact = ?";
        
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, phoneNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching record is found
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false; // Handle the exception as needed
        }
    }
    
// Custom method to show a validation error dialog
private void showValidationError(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

    
    
    
    /*void handleAdminButtonAction(ActionEvent event) {
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
    
    
    */
    
    
/*
    public void handlePublierButtonAction(ActionEvent event) {

        String ville = tfVille.getText();
        String description = tfDescription.getText();
        String contact = tfContact.getText();
        LocalDate date = LocalDate.now().from(tfDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Check if both fields are not empty
        if (!ville.isEmpty() && !description.isEmpty() && !contact.isEmpty() && date != null) {
            // Create a Personne object
            Article newArticle = new Article(ville, description, contact, date, imagePath);
            // Create an instance of PersonneCRUD
            articleCRUD aC = new articleCRUD();

            // Use the instance to add the object to your data source
            aC.ajouterArticle(newArticle);

            // Add the new article to the TableView
            ObservableList<Article> articleList = (ObservableList<Article>) tableId.getItems();
            articleList.add(newArticle);
            // Optionally, clear the input fields after adding
            tfVille.clear();
            tfDescription.clear();
            tfContact.clear();
            tfDate.setValue(null);
            imageView.setImage(null); // Clear the ImageView
            imagePath = "";            // Provide feedback to the user

        }
    }
*/
    @FXML
    public void handleImportButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Show the file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
tableId.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Store the selected Article
        this.selectedArticle = newSelection;
    }});
        if (selectedFile != null) {
            // Load and display the selected image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imagePath = selectedFile.getAbsolutePath(); // Store the image path
        }
    }

    @FXML
 public void handleModifierButtonAction(ActionEvent event) {
     
       if (selectedArticle == null) {
        // Show an alert because no row is selected
        showValidationError("No Selection", "Veuillez sélectionner un article à modifier.");
        return; // Exit the method
    }
    if (selectedArticle != null) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cet article?");

        // Show the confirmation alert and wait for the user's response
        if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
        selectedArticle.setVille(tfVille.getText());
        selectedArticle.setDescription(tfDescription.getText());
        selectedArticle.setContact(tfContact.getText());

      LocalDate date = tfDate.getValue();
       selectedArticle.setCreationDate(date);  // Set the LocalDate directly

        // Update the record in the database
        articleCRUD articleCRUDInstance = new articleCRUD();
        articleCRUDInstance.modifierArticle(selectedArticle);

        // Refresh the table
        populateTable();}
    }
}
    
    
 
 
 
 
 
 
 
 
 
    @FXML
    public void handleSupprimerButtonAction(ActionEvent event) {
          if (selectedArticle == null) {
        // Show an alert because no row is selected
        showValidationError("No Selection", "Veuillez sélectionner un article à supprimer.");
        return; // Exit the method
    }
    try {if (selectedArticle != null) {
        int idToDelete = selectedArticle.getId(); // Assuming that the Article class has an `getId` method

        // Create an instance of articleCRUD and call the supprimerArticle method
        articleCRUD articleCRUDInstance = new articleCRUD();
        articleCRUDInstance.supprimerArticle(idToDelete);

        // Refresh the table to reflect the changes
        populateTable();

        // Clear the TextFields and other UI components after deletion (if needed)
        tfVille.clear();
        tfDescription.clear();
        tfContact.clear();
        tfDate.setValue(null);
        imageView.setImage(null);
        imagePath = ""; // Clear any stored image path
    }}catch (Exception e) {
        e.printStackTrace(); // Print the exception stack trace
    }
}
    

 
 
 @FXML
private void handleClearButtonAction(ActionEvent event) {
    // Clear the input fields
    tfVille.clear();
    tfDescription.clear();
    tfContact.clear();
    tfDate.setValue(null);

    // Clear the image view
    imageView.setImage(null);

    // Clear the imagePath variable (if needed)
    imagePath = "";
}

    @FXML
    private void btnevent(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("event.fxml"));
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
    private void hisoaction(ActionEvent event) {
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
 
 
 
}
