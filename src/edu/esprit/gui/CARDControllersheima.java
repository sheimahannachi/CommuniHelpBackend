/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;


import edu.esprit.entities.Medecin;
import edu.esprit.entities.User;
import edu.esprit.entities.commentaire;
import edu.esprit.entities.publications;
import edu.esprit.services.commentaireCrud;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;




import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URI;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;





import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import org.apache.http.HttpRequest;




/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CARDControllersheima implements Initializable {
    
     private UserPostsController userPostsController; // Ajoutez cette référence
    
    
    
    @FXML
    private Label nomUser;
    @FXML
    private ImageView heart;
    @FXML
    private ImageView comment;
    @FXML
    private Label labelText;
    
    @FXML
    private ImageView doctor;
    private boolean liked = false;
    
    
    
    
    

// Create a text field for entering comments
private TextField commentField = new TextField();

// Create a list view for displaying comments
private ListView<String> commentListView = new ListView<>();

// Create buttons for adding, deleting, and modifying comments
private Button addButton = new Button("Ajouter");
private Button deleteButton = new Button("Supprimer");
private Button modifyButton = new Button("Modifier");

// Create the dialog
private Dialog<Void> dialogueCommentaire = new Dialog<>();
    @FXML
    private Button traduire;
    

         
    
    
    public void setUserPostsController(UserPostsController userPostsController) {
        this.userPostsController = userPostsController;
    }
    


    @FXML
    private ImageView imageView1;

    @FXML
    private Label labeltexte1;


 
    private publications publication;
    private Medecin medecin ; 
    private int currentPublicationId;
    
  
    public void setPublication(publications publication,User medecin) {
       
        this.publication = publication;
        this.currentPublicationId = publication.getId();
        String texte = publication.getTexte();
        String tags = publication.getTags();
        LocalDate dateDePublication =publication.getDateDePublication();
        String nom =medecin.getNom();
        String prenom = medecin .getPrenom();

        System.out.println(" " + texte);
        System.out.println(" " + tags);
        System.out.println(" " + dateDePublication);
        System.out.println(" " + nom);
        System.out.println(" " + prenom);
        
        
         labelText.setText(texte != null ? texte : "N/A"); // Set "texte" to labelText
         labeltexte1.setText("Date: " + dateDePublication + ", Tags: " + (tags != null ? tags : "N/A"));
         nomUser.setText(nom + " " + prenom);
        String imageUrl = publication.getImagePath();

        System.out.println("Image Path: " + imageUrl);

        if (imageUrl != null) {
            Image image = new Image("file:" + imageUrl);
            imageView1.setImage(image);
        }
    }

    

        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String cheminImage4 = "/images/heart.png";

        // Chargez l'image à partir du fichier
        Image image4= new Image(getClass().getResourceAsStream(cheminImage4));

        // Attribuez l'image à l'ImageView
        heart.setImage(image4);
        
         String cheminImage5 = "/images/comment.png";

        // Chargez l'image à partir du fichier
        Image image5 = new Image(getClass().getResourceAsStream(cheminImage5));

        // Attribuez l'image à l'ImageView
        comment.setImage(image5);
        
        String cheminImage6 = "/images/doctor.png";

        // Chargez l'image à partir du fichier
        Image image6 = new Image(getClass().getResourceAsStream(cheminImage6));

        // Attribuez l'image à l'ImageView
        doctor.setImage(image6);

        
        
        // Add a click event handler to the heart image
    heart.setOnMouseClicked(event -> {
        toggleHeart();
    });
    
    
    
  comment.setOnMouseClicked(event -> {
        if (event.getButton() == MouseButton.PRIMARY) {
            createCommentaireApp(currentPublicationId);
        }
    });
 
    

    // ...*/
    }
    
public static String translateText(String text, String fromLanguage, String toLanguage) throws IOException {
    String apiKey = "945a7eb66bmsh4e4c93208c7aa5ap13208ejsn42593e13ef61"; // Replace with your actual API key
    String apiUrl = "https://translation-api4.p.rapidapi.com/translation?from=en&to=fr&query=How%20are%20you";

    try {
        // Encode the text to translate
        String encodedText = URLEncoder.encode(text, "UTF-8");

        // Construct the API URL with parameters
        String urlString = apiUrl +
                "?q=" + encodedText +
                "&source=" + fromLanguage +
                "&target=" + toLanguage +
                "&key=" + apiKey;

        // Create a URL object and open a connection to the API
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method
        connection.setRequestMethod("GET");

        // Read the response from the API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Extract the translated text from the JSON response
        String jsonResponse = response.toString();
        String translatedText = extractTranslatedWord(jsonResponse); // Get the translated text

        return translatedText;
 } catch (IOException e) {
    // Handle the exception and log the error details
    System.err.println("An error occurred while making the HTTP request: " + e.getMessage());
    e.printStackTrace();
}

    return null;
}


// Extract the translated word from the response
public static String extractTranslatedWord(String responseText) {
    // Parse the response to extract the translation
    String translationPrefix = "\"translation\":\"";
    int translationStart = responseText.indexOf(translationPrefix);
    if (translationStart != -1) {
        int translationEnd = responseText.indexOf("\"", translationStart + translationPrefix.length());
        if (translationEnd != -1) {
            return responseText.substring(translationStart + translationPrefix.length(), translationEnd);
        }
    }
    return "";
}

     @FXML
private void translateButtonClicked(ActionEvent event) {
    String textToTranslate1 = labeltexte1.getText();
    String textToTranslate2 = labelText.getText();
    String fromLanguage = "fr"; // Source language
    String toLanguage = "en";   // Target language

    try {
        // Perform the translation (you can use your translation API here)
        String translatedText1 = translateText(textToTranslate1, fromLanguage, toLanguage);
        String translatedText2 = translateText(textToTranslate2, fromLanguage, toLanguage);

        // Update the Labels with the translated texts
        labeltexte1.setText(translatedText1);
        labelText.setText(translatedText2);
    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception or log it as needed
    }
}
    
    
    
    
    
    



 void toggleHeart() {
       if (publication.getNbJaime() == null) {
        publication.setNbJaime(BigInteger.ZERO); // Initialize with zero if it's null
    }

    if (liked) {
        // If liked, decrement nbJaime by 1
        publication.setNbJaime(publication.getNbJaime().subtract(BigInteger.ONE));
        liked = false;
    } else {
        // If not liked, increment nbJaime by 1
        publication.setNbJaime(publication.getNbJaime().add(BigInteger.ONE));
        liked = true;
    }

    // Change the heart image color based on the liked status
    if (liked) {
        // Set the image to the red heart
        heart.setImage(new Image(getClass().getResource("/images/red_heart.png").toExternalForm()));
    } else {
        // Set the image to the gray heart
        heart.setImage(new Image(getClass().getResource("/images/heart.png").toExternalForm()));
    }
}


public void createCommentaireApp(int currentPublicationId) {
    int currentMedecinId = 2; // Set the appropriate value for currentMedecinId

    Stage stage = new Stage();
    stage.setTitle("Commentaire App");
    VBox root = new VBox();
    Scene scene = new Scene(root, 400, 400);
    stage.setScene(scene);

    TextField commentField = new TextField();
    Button addButton = new Button("Add Comment");
    Button deleteButton = new Button("Delete Comment");
    Button modifyButton = new Button("Modify Comment");
    ListView<commentaire> commentListView = new ListView<>();

    addButton.setOnAction(e -> {
        String newComment = commentField.getText().trim();
        if (!newComment.isEmpty()) {
            // Replace 1 with the actual publication ID
                    newComment = censorBadWords(newComment);


            commentaire newCommentaire = new commentaire(currentPublicationId, newComment, currentMedecinId);//2 est le current medecin id 
            commentaireCrud commentCrud = new commentaireCrud();
            commentCrud.ajouterCommentaire(newCommentaire, currentPublicationId,currentMedecinId);

            // Update the ListView with the new comment
            commentListView.getItems().add(newCommentaire);
            commentField.clear();
        }
    });

          deleteButton.setOnAction(e -> {
           commentaire selectedComment = commentListView.getSelectionModel().getSelectedItem();
            if (selectedComment != null) {
            if (selectedComment.getId() == currentMedecinId) {
                commentaireCrud commentCrud = new commentaireCrud();
                commentCrud.supprimerCommentaire(selectedComment.getId_comm());

                // Update the ListView to remove the deleted comment
                commentListView.getItems().remove(selectedComment);
            } else {
                showAlert("Cannot Delete Comment", "You can only delete your own comments.");
            }
        }
    });

         modifyButton.setOnAction(e -> {
        commentaire selectedComment = commentListView.getSelectionModel().getSelectedItem();
        String updatedComment = commentField.getText().trim();
        if (selectedComment != null && !updatedComment.isEmpty()) {
            if (selectedComment.getId() == 2) {
                updatedComment = censorBadWords(updatedComment);
                selectedComment.setContenuCommentaire(updatedComment);
                commentaireCrud commentCrud = new commentaireCrud();
                commentCrud.modifierCommentaire(selectedComment, selectedComment.getId_comm(),currentMedecinId);

                // Refresh the ListView to reflect the modified comment
                commentListView.refresh();
                commentField.clear();
            } else {
                showAlert("Cannot Modify Comment", "You can only modify your own comments.");
            }
        }
    });

    root.getChildren().addAll(commentField, addButton, deleteButton, modifyButton, commentListView);

    // Populate the ListView with data from the database
    populateCommentListView(commentListView);

    stage.show();
}

public void populateCommentListView(ListView<commentaire> commentListView) {
    commentaireCrud commentCrud = new commentaireCrud();
    List<commentaire> commentaires = commentCrud.afficherCommentaires(currentPublicationId);

    // Clear the existing data in the ListView
    commentListView.getItems().clear();

    // Create a custom cell factory to display only the contenuCommentaire
    commentListView.setCellFactory(param -> new ListCell<commentaire>() {
        @Override
        protected void updateItem(commentaire item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                // Set the cell value to the contenuCommentaire
                setText(item.getContenuCommentaire());
            }
        }
    });

    // Add the data from the database to the ListView
    commentListView.getItems().addAll(commentaires);

}


public static void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
public String censorBadWords(String text) {
    // Define a list of bad words
    List<String> badWords = Arrays.asList("hazem", "oumayma", "sheima","nadhir","abcd","azbveh");

    // Split the input text into words
    String[] words = text.split(" ");

    // Create a StringBuilder to build the censored text
    StringBuilder censoredText = new StringBuilder();

    for (String word : words) {
        if (badWords.contains(word.toLowerCase())) {
            // If the word is a bad word, replace each letter with *
            for (char letter : word.toCharArray()) {
                censoredText.append('*');
            }
        } else {
            // If it's not a bad word, keep it as it is
            censoredText.append(word);
        }
        censoredText.append(' '); // Add a space between words
    }

    return censoredText.toString().trim();
}



}