/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;


import edu.esprit.entities.NotificationManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javax.management.remote.JMXConnectorFactory.connect;
import edu.esprit.entities.Produits;
import edu.esprit.services.ProduitsCRUD;
import edu.esprit.utilis.MyConnection;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ShopwindController implements Initializable {

    @FXML
    private TextField prod_nom;
    @FXML
    private TextField prod_prix;
    @FXML
    private TextField desc_prod;
    @FXML
    private ComboBox<String> statut_prod;
    @FXML
    private TableView<Produits> product_view;
    @FXML
    private TableColumn<Produits, String> prodcol_nom;
    @FXML
    private TableColumn<Produits, String> prodcol_prix;
    @FXML
    private TableColumn<Produits, String> prod_coldesc;
    @FXML
    private TableColumn<Produits, String> prodcol_statut;

    /**
     * Initializes the controller class.
     */
    private MyConnection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;
    @FXML
    private ImageView imageView;
    private String imagePath="";
    Produits selectedProduit ;
    @FXML
    private Button btnImporter;
    @FXML
    private Button swingtoL;
    @FXML
    private Button vider;
    @FXML
    private Button retriev;
    @FXML
    private Button gotodons;
    @FXML
    private Button gotoevent;
    @FXML
    private Button dec;
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        utilisateurSexeList();
        prodcol_nom.setCellValueFactory(new PropertyValueFactory<>("nomDuProduit"));
        prodcol_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        prod_coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        prodcol_statut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Call the method to populate the table
        populateTable();
       
        product_view.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Remplir les champs de saisie avec les valeurs de la ligne sélectionnée
                Produits selectedProduit = newSelection;
                prod_nom.setText(selectedProduit.getNomDuProduit());
                prod_prix.setText(String.valueOf(selectedProduit.getPrix()));
                desc_prod.setText(selectedProduit.getDescription());
                statut_prod.setValue(selectedProduit.getStatut());
                String selectedImage = selectedProduit.getImage();
                if (selectedImage != null) {
                    Image image = new Image(new File(selectedImage).toURI().toString());
                    imageView.setImage(image);}
            }
        });
      
      
    }

    public void populateTable() {

        // Clear existing items from the table
        product_view.getItems().clear();
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();

        List<Produits> produits = produitsCRUDInstance.afficherP();

        // Convert the list of produits to an ObservableList
        ObservableList<Produits> produitsList = FXCollections.observableArrayList(produits);

        // Add the data to the TableView
        product_view.setItems(produitsList);
    }
    
    @FXML
    private void saveproducts(ActionEvent event) {
        String nom = prod_nom.getText();
        String prixStr = prod_prix.getText(); // Obtenir la valeur du champ prix sous forme de chaîne
        String Desc = desc_prod.getText();
        String statut = (String) statut_prod.getSelectionModel().getSelectedItem();

        // Vérifier si la chaîne du prix n'est pas vide
        if (prixStr.isEmpty()) {
            // Afficher une alerte ou prendre d'autres mesures pour gérer le cas où le prix est vide
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Le champ prix est vide. Veuillez saisir un prix valide.");
            alert.showAndWait();
            return;
        }

        // Convertir la chaîne de prix en un entier
        int prix = 0; // Par défaut, vous pouvez choisir une autre valeur par défaut si nécessaire
        try {
            prix = Integer.parseInt(prixStr);
        } catch (NumberFormatException e) {
            // Gérer le cas où la conversion échoue, par exemple, afficher une alerted
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Le champ prix ne contient pas une valeur numérique valide. Veuillez saisir un prix valide.");
            alert.showAndWait();
            return;
        }

        Produits p1 = new Produits(nom, prix, Desc, statut,imagePath);
        ProduitsCRUD pc = new ProduitsCRUD();
        pc.addProd(p1);
         //NotificationManager.showNotification("Succès", "Le produit a été ajouté avec succès.");
        populateTable();

                 // Appeler votre méthode ici
            
    }
    private String[] statutlist = {"available", "not_available"};

    public void utilisateurSexeList() {
        List<String> sList = new ArrayList<>();
        for (String data : statutlist) {
            sList.add(data);

        }
        ObservableList listData = FXCollections.observableArrayList(sList);
        statut_prod.setItems(listData);

    }

    @FXML
    private void updateproducts(ActionEvent event) {
    String newNom = prod_nom.getText();
    String prixStr = prod_prix.getText();
    String desc = desc_prod.getText();
    String newStatut = statut_prod.getValue();

    Produits selectedProduit = product_view.getSelectionModel().getSelectedItem();

    if (selectedProduit == null) {
        // Gérer le cas où aucun produit est sélectionné
        return;
    }

    // Afficher une boîte de dialogue de confirmation
    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
    confirmationAlert.setTitle("Confirmation de modification");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("Voulez-vous vraiment modifier ce produit ?");

    Optional<ButtonType> result = confirmationAlert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // L'opérateur a confirmé la modification
        performUpdate(selectedProduit, newNom, prixStr, desc, newStatut);
    }
}

    private void performUpdate(Produits selectedProduit, String newNom, String prixStr, String desc, String newStatut) {
        ProduitsCRUD pc = new ProduitsCRUD();
        if (pc.isNomProduitUnique(newNom)) {
            int newPrix = 0;
            try {
                newPrix = Integer.parseInt(prixStr);
            } catch (NumberFormatException e) {
                // Handle the case where the conversion fails
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Le champ prix ne contient pas une valeur numérique valide. Veuillez saisir un prix valide.");
                alert.showAndWait();
                return;
            }

            Produits updatedProduit = new Produits(newNom, newPrix, desc, newStatut);
            pc.updateProduit(selectedProduit, updatedProduit);

            // Refresh the table after the update
            populateTable();
        } else {
            // The new name is not unique, show an alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Le produit avec le nom " + newNom + " existe déjà. Veuillez choisir un nom unique.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteproduct(ActionEvent event) {
        Produits selectedProduit = product_view.getSelectionModel().getSelectedItem();

        if (selectedProduit == null) {
            // Handle the case where no product is selected
            return;
        }

        // Show a confirmation dialog before deleting
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer ce produit ?");
        confirmationAlert.setContentText("Cliquez sur OK pour confirmer la suppression.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // The operator confirmed the deletion
            deleteProduct(selectedProduit);
        }
    }

    private void deleteProduct(Produits produit) {
        ProduitsCRUD pc = new ProduitsCRUD();
        pc.deleteProduit(produit);

        // Refresh the table after the deletion
        populateTable();
    }

    @FXML
    private void handleImportButtonAction(ActionEvent event) {
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif"));
try{
        // Show the file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());
product_view.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        // Store the selected Article
        this.selectedProduit = newSelection;
    }});
        if (selectedFile != null) {
            // Load and display the selected image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imagePath = selectedFile.getAbsolutePath(); // Store the image path
        }
    } catch (Exception e) {
    e.printStackTrace();}

}
 
    @FXML
    private void swingtoL(ActionEvent event) {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
         try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("commande.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
       

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        
        
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    
    }

    @FXML
    private void viderbtn(ActionEvent event) {
         prod_nom.setText("");
    prod_prix.setText("");
    desc_prod.setText("");
    statut_prod.getSelectionModel().clearSelection();
    imageView.setImage(null);
    }

   @FXML
private void retrieveProdadminDataButton(ActionEvent event) {
    Connection cnx = MyConnection.getInstance().getCnx();
    String query = "SELECT prodname, typechangement FROM prodadmin";

    Statement statement = null;

    try {
        statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String prodname = resultSet.getString("prodname");
            String typechangement = resultSet.getString("typechangement");

            // Créer un message pour chaque produit
            String message = "Le produit \"" + prodname + "\" a été \"" + typechangement + "\"";

            // Afficher une notification pour chaque message
            NotificationManager.showNotifications("Notification", message);
        }
        
        // Après avoir affiché les notifications, vous pouvez supprimer les données du tableau prodadmin
        String deleteQuery = "DELETE FROM prodadmin";
        statement.executeUpdate(deleteQuery);

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    @FXML
    private void gotodonact(ActionEvent event) {try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
       

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        
        
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @FXML
    private void gotoevent(ActionEvent event) {try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("event.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
       

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        
        
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
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


    
    
 




