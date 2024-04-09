package edu.esprit.gui;

import edu.esprit.entities.Medecin;
import edu.esprit.entities.User;
import edu.esprit.entities.publications;
import edu.esprit.services.publicationsCrud;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;
import java.nio.charset.StandardCharsets;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

public class UserPostsController implements Initializable {
    
    @FXML
    private VBox postContainer;
    @FXML
    private ImageView chatbot;
    @FXML
    private Button eventbtn3;
    @FXML
    private Button espaceadminbtn3;
    @FXML
    private Button retourbtn3;
    @FXML
    private Button gotoouma;
    @FXML
    private Button dec;
    public void setPostContainer (VBox postContainer){
    this.postContainer = postContainer;}

    
    
    public void displayCards() {
        try {
            publicationsCrud publicationCRUDInstance = new publicationsCrud();
            List<publications> publications = publicationCRUDInstance.afficherpublications();
            
            for (publications publication : publications) {
                if (publication != null && publication.getDateDePublication() != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CARDsheima.fxml"));
                        Region postCard = loader.load();
                        CARDControllersheima cardController = loader.getController();
                        
                        User medecin = publicationCRUDInstance.getMedecinForPublication(publication.getId());
                        cardController.setPublication(publication, medecin);
                        
                      
                        
                        postContainer.getChildren().add(postCard);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         chatbot.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                openChatbotDialog();
            }
        });
        
        
        
        displayCards();
    }

public String getChatbotResponse(String userMessage) {
    String apiKey = "4a31578ac5msh4fb73389bc8fb33p1714d7jsn047dfd800e53"; // Replace with your actual API key
    String host = "ai-chatbot.p.rapidapi.com";

    try {
        String encodedMessage = URLEncoder.encode(userMessage, "UTF-8");
        String apiUrl = "https://ai-chatbot.p.rapidapi.com/chat/free?message=" + encodedMessage + "&uid=user1";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(apiUrl);
        request.setHeader("X-RapidAPI-Key", apiKey);
        request.setHeader("X-RapidAPI-Host", host);

        CloseableHttpResponse response = client.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        response.close();
        client.close();

        return responseBody;
    } catch (Exception e) {
        e.printStackTrace();
        return "An error occurred while communicating with the chatbot.";
    }
}
public String chatWithChatbot(String userMessage) {
    String chatbotResponse;

    // Call the chatbot API and get the full response
    String fullApiResponse = getChatbotResponse(userMessage);

    // Define the separator that separates the query and the response
    String separator = "\",\"response\":\"";

    // Find the position of the separator
    int separatorIndex = fullApiResponse.indexOf(separator);

    if (separatorIndex != -1) {
        // Extract the response from the API's response
        chatbotResponse = fullApiResponse.substring(separatorIndex + separator.length());

        // Remove the closing quote and brace from the response
        chatbotResponse = chatbotResponse.replace("\"}}", "");
    } else {
        chatbotResponse = "An error occurred while communicating with the chatbot.";
    }

    return chatbotResponse;
}



       public void openChatbotDialog() {
    // Create a new Dialog
    Dialog<Void> chatbotDialog = new Dialog<>();
    chatbotDialog.setTitle("Chat with Chatbot");

    // Create a DialogPane for the chat interface
    DialogPane dialogPane = new DialogPane();
    dialogPane.getButtonTypes().add(ButtonType.CLOSE);

    // Create a VBox to hold the chat interface components
    VBox chatPane = new VBox();

    // Create a TextArea for chatbot messages
    TextArea chatArea = new TextArea();
    chatArea.setEditable(false);
    chatArea.setWrapText(true);
    chatPane.getChildren().add(chatArea);

    // Create a TextField for user input
    TextField userInputField = new TextField();
    userInputField.setPromptText("Type your question here...");
    chatPane.getChildren().add(userInputField);

    // Create a Button to send the user's question
    Button sendButton = new Button("Send");
    chatPane.getChildren().add(sendButton);

    dialogPane.setContent(chatPane);

    // Set the content of the dialog
    chatbotDialog.setDialogPane(dialogPane);

    // Handle dialog close request
    chatbotDialog.setOnCloseRequest(e -> {
        // Add any cleanup or handling when the dialog is closed
    });

    // Show the dialog
    chatbotDialog.show();

    // Event handler for the send button
    sendButton.setOnAction(event -> {
        String userMessage = userInputField.getText();
        chatArea.appendText("User: " + userMessage + "\n");

        // Get the chatbot response
        String chatbotResponse = chatWithChatbot(userMessage);
        chatArea.appendText("Chatbot: " + chatbotResponse + "\n");

        // Clear the user input field
        userInputField.clear();
    });
}

   
    

    @FXML
    private void eventaction(ActionEvent event) {    try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paricipe.fxml"));
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
    private void adminaction1(ActionEvent event) {    try {
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
    private void gotoouma(ActionEvent event) {try {
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