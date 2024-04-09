/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.Article;
import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.User;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class FaireDon2Controller implements Initializable {

    @FXML
    private Button btnImporter1;
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private GridPane menu_gridPane;
     private ObservableList<Article> cardListData = FXCollections.observableArrayList();
    @FXML
    private Button eventsbtn;
    @FXML
    private Button shopbtnuser;
    @FXML
    private Label fxemail;
    @FXML
    private AnchorPane fxFront;
    @FXML
    private Button btnpubM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardListData = menuGetData();
        displayCards();   
     User loggedInUser = CurrentUser.getLoggedInUser();
     fxemail.setText(loggedInUser.getEmail());
     

    }    
    
    
    
    
    
    public ObservableList<Article> menuGetData() {
    ObservableList<Article> listData = FXCollections.observableArrayList();

    MyConnection connection = MyConnection.getInstance();

    // Fetch data from your database table here
    try {
        Connection cnx = connection.getCnx();
        String query = "SELECT * FROM article";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String ville = resultSet.getString("ville");
            String description = resultSet.getString("description");
            String contact = resultSet.getString("contact");
            LocalDate creationDate = resultSet.getDate("creation_date").toLocalDate(); // Assuming creationDate is stored as a date in the database
            String image = resultSet.getString("image");

            Article article = new Article(id, ville, description, contact, creationDate, image);
            listData.add(article);
        }
        
        // Close resources (e.g., resultSet and statement) here
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return listData;
}

    
    
  
    
 private void setGridConstraints() {
        // Set the maximum number of columns per row
        int maxColumns = 3;

        // Clear any existing constraints
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int i = 0; i < maxColumns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / maxColumns);
            menu_gridPane.getColumnConstraints().add(column);
        }

        // No need to set RowConstraints as it will be determined automatically
    }

    private void displayCards() {
        int column = 0;
        int row = 0;

        for (Article article : cardListData) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                AnchorPane cardPane = loader.load();
                CardoumaController cardController = loader.getController();

                // Pass the article data to the card controller
                cardController.setArticle(article);

                // Add the card to the grid pane
                menu_gridPane.add(cardPane, column, row);

                column++; // Move to the next column

                if (column == menu_gridPane.getColumnConstraints().size()) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void gotoshop(ActionEvent event) throws IOException {
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
    private void pubMedecin(ActionEvent event) {
        
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
    private void eventsbtnaction(ActionEvent event) {
         try {
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



}
