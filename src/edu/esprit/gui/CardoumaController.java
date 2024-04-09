package edu.esprit.gui;

import edu.esprit.entities.Article;
import edu.esprit.entities.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardoumaController implements Initializable {
    private Article article;
    @FXML
private Button donateButton;

    @FXML
    private Text postContentText;
    @FXML
    private ImageView postImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code here
    }

   public void setArticle(Article article) {
    this.article = article;

    // Check for null values before setting text
    String ville = article.getVille();
    String description = article.getDescription();
    String contact = article.getContact();
    LocalDate creationDate = article.getCreationDate();
    
    StringBuilder text = new StringBuilder("Ville: ");
    text.append(ville != null ? ville : "N/A");
    text.append("\nDescription: ");
    text.append(description != null ? description : "N/A");
    text.append("\nContact: ");
    text.append(contact != null ? contact : "N/A");

    if (creationDate != null) {
        text.append("\nDate de création de cette cause: ").append(creationDate.toString());
    }

    postContentText.setText(text.toString());

    // Set the image based on the article's image URL
    String imageUrl = article.getImage();
    if (imageUrl != null) {
        Image image = new Image("file:" + imageUrl);
        postImageView.setImage(image);
    }
}
@FXML
private void handleDonateButtonClick(ActionEvent event) {
    // Get the description from the article
    String description = article.getDescription();
        System.out.println("Selected Article ID: " + article.getId());
    
    // Load the DonationForm.fxml
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DonationForm.fxml"));
         FXMLLoader loader1 = new FXMLLoader(getClass().getResource("DonationForm.fxml"));
        Parent root = loader.load();
       
        // Access the DonationFormController and pass the description
        DonationFormController donationFormController = loader.getController();
        
        donationFormController.setDescription(description);
        
        donationFormController.setArticleId(article.getId());
        // Create a new stage for the donation form
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Donation Form");
        stage.show();

        // Close the current stage (FaireDon.fxml)
        Stage currentStage = (Stage) donateButton.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}


}