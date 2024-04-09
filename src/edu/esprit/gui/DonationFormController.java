/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.Article;
import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.DonationForm;
import edu.esprit.entities.User;
import edu.esprit.entities.enumVille;
import edu.esprit.entities.enumVille.ville;
import edu.esprit.entities.history;
import edu.esprit.services.donationCRUD;
import edu.esprit.services.historyCRUD;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import edu.esprit.services.donationCRUD;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class DonationFormController {

    @FXML
    private TextArea descriptionText;

    private Connection cnx;

    @FXML
    private Button idButtonPay;

    @FXML
    private TextField idCarteBancaire;

    @FXML
    private TextField idEmail;

    @FXML
    private TextField idMontant;

    @FXML
    private TextField idNom;

    @FXML
    private TextField idPrenom;

    @FXML
    private ComboBox<enumVille.ville> idVille;
    // Text component to display the description

    private String description;

    private int articleId;

    public DonationFormController() {
        // Initialize the connection by obtaining it from MyConnectionAssociations
        cnx = MyConnection.getInstance().getCnx();
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    User user;

    public void initialize() {
        if (user == null) {
            user = CurrentUser.getLoggedInUser();
        }
        if (user != null) {
            setTextFields(user);
            CurrentUser.setLoggedInUser(user);
            User loggedInUser = CurrentUser.getLoggedInUser();
            int loggedInUserId = loggedInUser.getId();

            setTextFields(loggedInUser);

            
            
            
             String email = user.getEmail(); // Get the email from the logged-in user

        // Query your database to get the name and surname based on the email
        String query = "SELECT nom, prenom  FROM user WHERE email = ?";
        Connection conn = MyConnection.getInstance().getCnx();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                idNom.setText(nom); // Set the retrieved name
                idPrenom.setText(prenom); // Set the retrieved surname
            } else {
                // Handle the case when the user with the given email is not found in the database
            }
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
        }
        
        
            // Get the enum values and convert them to a list
            enumVille.ville[] villes = enumVille.ville.values();
            ObservableList<enumVille.ville> villeList = FXCollections.observableArrayList(villes);

            // Set the items in the ComboBox
            idVille.setItems(villeList);
            // TODO
        }
    }

    public void setDescription(String description) {
        String genericText = "Vous aidez la cause : " + description + "\nMerci de remplir votre formulaire de don. \nNous vous remercions pour cette précieuse aide.";
        descriptionText.setText(genericText);
    }

    void setTextFields(User loggedInUser) ///taabili le fenetre bel les donnes 
    {
        idNom.setText(loggedInUser.getEmail());
        idPrenom.setText(loggedInUser.getNom());
        idEmail.setText(loggedInUser.getPrenom());

    }

    @FXML
    public void handlePaymentButtonAction(ActionEvent event) {
        // Get the user inputs
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirm Your Donation");
        confirmationAlert.setContentText("Are you sure you want to make this donation?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            String montantString = idMontant.getText();
            String email = idEmail.getText();
            String nom = idNom.getText();
            String prenom = idPrenom.getText();
            enumVille.ville selectedVille = idVille.getSelectionModel().getSelectedItem();
            String carteBancaire = idCarteBancaire.getText();

            // Validate and parse the Montant (amount) as a Float
            Float montant = 0.0f;
            try {
                montant = Float.parseFloat(montantString);
                if (montant <= 0) {
                    showValidationError("Invalid Montant", "Montant must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                showValidationError("Invalid Montant", "Please enter a valid numeric value for Montant.");
                return;
            }

            // Validate other fields
            if (email.isEmpty() || !isValidEmail(email)) {
                showValidationError("Invalid Email", "Please enter a valid email address.");
                return;
            }

            if (nom.isEmpty()) {
                showValidationError("Missing Information", "Nom is required.");
                return;
            }

            if (prenom.isEmpty()) {
                showValidationError("Missing Information", "Prenom is required.");
                return;
            }

            if (selectedVille == null) {
                showValidationError("Missing Information", "Please select a Ville.");
                return;
            }

            if (carteBancaire.isEmpty() || !isValidCreditCardNumber(carteBancaire)) {
                showValidationError("Missing Information", "Carte Bancaire is required.");
                return;
            }

            // If all validations pass, proceed with creating the DonationForm and showing the confirmation dialog
            DonationForm donationForm = new DonationForm();
            donationForm.setMontant(montant);
            donationForm.setEmail(email);
            donationForm.setNom(nom);
            donationForm.setPrenom(prenom);
            donationForm.setVille(selectedVille);
            donationForm.setCarteBancaire(carteBancaire);

            donationCRUD donationCRUD = new donationCRUD();
            donationCRUD.ajouterdonationForm(donationForm);

            int donationId = obtainDonationId(montantString, email, nom, prenom);

            if (donationId != -1) {
                // Record the donation in the history table
                recordDonationInHistory(articleId, donationId);
            }

            showConfirmationDialog("Donation Successful", "Donation has been added successfully.");

            clearDonationFormFields();

            try {
                // Load the Recu.fxml file and display the receipt
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Recu.fxml"));
                Parent root = loader.load();
                RecuController recuController = loader.getController();
                recuController.setDonationData(montantString, email, nom, prenom, selectedVille, carteBancaire);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void recordDonationInHistory(int articleId, int donationId) {
        historyCRUD historyCRUD = new historyCRUD();

        // Obtain the donation form and article data
        DonationForm donationForm = getDonationFormById(donationId);
        Article article = getArticleById(articleId);

        if (donationForm != null && article != null) {
            history historyRecord = new history();

            historyRecord.setArticleId(articleId);
            historyRecord.setArticleVille(article.getVille());
            historyRecord.setDescription(article.getDescription());
            historyRecord.setContact(article.getContact());
            historyRecord.setImage(article.getImage());
            historyRecord.setDonationId(donationId);
            historyRecord.setMontant(donationForm.getMontant());
            historyRecord.setEmail(donationForm.getEmail());
            historyRecord.setNom(donationForm.getNom());
            historyRecord.setPrenom(donationForm.getPrenom());
            historyRecord.setDonationVille(donationForm.getVille().name());
            historyRecord.setCarteBancaire(donationForm.getCarteBancaire());

            historyCRUD.insertHistoryRecord(historyRecord);

            // Now you have inserted the donation into the history table
        }
    }

    private int obtainDonationId(String montant, String email, String nom, String prenom) {
        int donationId = -1;

        // Your code to query the database based on the donation details
        try {
            Connection connection = MyConnection.getInstance().getCnx();
            String query = "SELECT id FROM donationforms WHERE montant = ? AND email = ? AND nom = ? AND prenom = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, montant);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, nom);
            preparedStatement.setString(4, prenom);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                donationId = resultSet.getInt("id");
            }

            // Print the obtained donationId for testing
            System.out.println("Obtained donationId: " + donationId);

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donationId;
    }

    private boolean isValidEmail(String email) {
        // Define a simple regular expression pattern for email validation
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Use the matches() method to check if the email matches the pattern
        return email.matches(emailPattern);
    }

    private boolean isValidCreditCardNumber(String cardNumber) {
        // Remove any spaces or non-digit characters from the card number
        cardNumber = cardNumber.replaceAll("[^0-9]", "");

        // Check if the card number is the desired length (e.g., 16 digits for most credit cards)
        return cardNumber.length() == 16; // Adjust the length based on your specific validation criteria
    }

    private void showConfirmationDialog(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showValidationError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearDonationFormFields() {
        idMontant.clear();
        idEmail.clear();
        idNom.clear();
        idPrenom.clear();
        idVille.setValue(null);
        idCarteBancaire.clear();
    }

    @FXML
    public void RetourButtonAction(ActionEvent event) {
        try {
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

    public DonationForm getDonationFormById(int donationId) {
        DonationForm donationForm = null;

        try {
            String query = "SELECT * FROM donationforms WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, donationId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                donationForm = new DonationForm();
                donationForm.setIdDon(rs.getInt("id"));
                donationForm.setMontant(rs.getFloat("montant"));
                donationForm.setEmail(rs.getString("email"));
                donationForm.setNom(rs.getString("nom"));
                donationForm.setPrenom(rs.getString("prenom"));
                donationForm.setVille(ville.valueOf(rs.getString("ville"))); // Assuming you store the enum name as a string
                donationForm.setCarteBancaire(rs.getString("carte_bancaire"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return donationForm;
    }

    public Article getArticleById(int articleId) {
        Article article = null;

        try {
            String query = "SELECT * FROM article WHERE id=?";
            System.out.println("SQL Query: " + query); // Print the SQL query
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, articleId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                article = new Article();
                article.setId(rs.getInt("id"));
                article.setVille(rs.getString("ville"));
                article.setDescription(rs.getString("description"));
                article.setContact(rs.getString("contact"));
                article.setImage(rs.getString("image"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("Retrieved Article: " + article);

        return article;
    }

}
