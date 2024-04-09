//package edu.esprit.gui;
///*
//import edu.esprit.entities.Article;
//import edu.esprit.services.articleCRUD;
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class FaireDonController implements Initializable {
//    @FXML
//    VBox postContainer;
//    
//   
//@FXML
//    private Button btnImporter1;
//
//
//   
//
//    void handleConsulterButtonAction(ActionEvent event) {/*
//         try {
//        // Load the FaireDon.fxml file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("consulterHistorique.fxml"));
//        Parent root = loader.load();
//
//        // Create a new stage and show the FaireDon.fxml
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        // Close the current stage (Recu.fxml)
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//
//   */ }
//
//    public void displayCards() {
//        try {
//            articleCRUD articleCRUDInstance = new articleCRUD();
//            List<Article> articles = articleCRUDInstance.getArticles();
//            System.out.println("Retrieved articles: " + articles);
//
//            for (Article article : articles) {
//                // Check if the article is not null and has a non-null creationDate
//                if (article != null && article.getCreationDate() != null) {
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
//                        Node postCard = loader.load();
//                        CardController cardController = loader.getController();
//                        
//                        // Set the article data for the card
//                        cardController.setArticle(article);
//
//                        // Add the post card to the container
//                        postContainer.getChildren().add(postCard);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    
//    /*@FXML
//        public void handlethisButtonAction(ActionEvent event) {
//    try {
//        // Load the FaireDon.fxml file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
//        Parent root = loader.load();
//
//        // Create a new stage and show the FaireDon.fxml
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        // Close the current stage (Recu.fxml)
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//        
//      */ 
//        
//        
//        
//        
//    
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Call the method to load and display cards
//        displayCards();
//    }
//}
