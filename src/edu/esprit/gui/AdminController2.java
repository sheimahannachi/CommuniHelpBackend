package edu.esprit.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Optional;
import edu.esprit.services.articleCRUD;
import edu.esprit.entities.Article;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AdminController2 implements Initializable {

    @FXML
    private Button appeldonbtn2;
    @FXML
    private Button espaceadminbtn2;
    @FXML
    private TableView<Article> tableview2;
    @FXML
    private TableColumn<Article, String> villecolumnid2;
    @FXML
    private TableColumn<Article, String> descriptioncolumnid2;
    @FXML
    private TableColumn<Article, String> contactcolumnid2;
    @FXML
    private TableColumn<Article, LocalDate> datedecreationcolumnid2;
    @FXML
    private Button btnClear2;
    @FXML
    private Button btnSupprimer2;
    @FXML
    private Button btnModifier2;
    @FXML
    private TextField tfVille2;
    @FXML
    private TextField tfDescription2;
    @FXML
    private TextField tfContact2;
    @FXML
    private DatePicker tfDate2;
    @FXML
    private ImageView imageView2;
    @FXML
    private Button btnImporter2;
    
    private articleCRUD articleService;
    private ObservableList<Article> articlesData;
    @FXML
    private Button eventbtn;
    @FXML
    private Button historiquebtn;
    @FXML
    private Button retourbtn;
    @FXML
    private AnchorPane fxFront;
    @FXML
    private Button dec;
    @FXML
    private Button medid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        articleService = new articleCRUD();
        articlesData = FXCollections.observableArrayList(); // Initialize the observable list

        // Set up the cell value factories to map columns to Article properties
        villecolumnid2.setCellValueFactory(new PropertyValueFactory<>("ville"));
        descriptioncolumnid2.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactcolumnid2.setCellValueFactory(new PropertyValueFactory<>("contact"));
        datedecreationcolumnid2.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        // Load data into your TableView here
        populateTable();

        // Add a listener to handle table selections
        tableview2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {
            @Override
            public void changed(ObservableValue<? extends Article> observable, Article oldValue, Article newValue) {
                if (newValue != null) {
                    // Handle row selection
                    handleRowSelection(newValue);
                }
            }
        });
    }

    private void appelaudon2(ActionEvent event) { try {
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

   /* private void faireaudon2(ActionEvent event) {try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FaireDon.fxml"));
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
    }   }*/

    @FXML
    private void clear2(ActionEvent event) {
        // Handle the "Clear" button click event
        // Clear the input fields, ImageView, and other necessary components
        tfVille2.clear();
        tfDescription2.clear();
        tfContact2.clear();
        tfDate2.setValue(null);
        imageView2.setImage(null);
    }

    @FXML
    private void supprimer2(ActionEvent event) {
        // Handle the "Supprimer" button click event
        Article selectedArticle = tableview2.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            // Show a confirmation dialog before deleting
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cet article ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                articleService.supprimerArticle(selectedArticle.getId());
                populateTable();
                clear2(null); // Clear the input fields
            }
        } else {
            showValidationError("Aucune sélection", "Veuillez sélectionner un article à supprimer.");
        }
    }

    @FXML
    private void modifier2(ActionEvent event) {
        Article selectedArticle = tableview2.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            // Update the selected article with new values
            selectedArticle.setVille(tfVille2.getText());
            selectedArticle.setDescription(tfDescription2.getText());
            selectedArticle.setContact(tfContact2.getText());
            selectedArticle.setCreationDate(tfDate2.getValue());

            articleService.modifierArticle(selectedArticle);
            populateTable();
        } else {
            showValidationError("Aucune sélection", "Veuillez sélectionner un article à modifier.");
        }
    }

    @FXML
    private void importer2(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Load and display the selected image in the ImageView
            imageView2.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    private void populateTable() {
        List<Article> articles = articleService.getArticles();
        articlesData.setAll(articles);
        tableview2.setItems(articlesData);
    }

    private void handleRowSelection(Article article) {
        tfVille2.setText(article.getVille());
        tfDescription2.setText(article.getDescription());
        tfContact2.setText(article.getContact());
        tfDate2.setValue(article.getCreationDate());

        // Load and display the selected image in the ImageView
        String imageUrl = article.getImage();
        if (imageUrl != null ) {
                    Image image = new Image("file:" + imageUrl);

            imageView2.setImage(image);
        } 
    }

    private void showValidationError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    private void consulterhistoriqueaction(ActionEvent event) {
  
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
    private void retourmethod(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
    }

    @FXML
    private void adminshop(ActionEvent event) {
        
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

    @FXML
    private void eventadminaction(ActionEvent event) {
        try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Paricipeadmin.fxml"));
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
    private void medidaction(ActionEvent event) {try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminsheima.fxml"));
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

