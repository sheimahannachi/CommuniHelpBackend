
package edu.esprit.gui;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import edu.esprit.entities.Produits;
import edu.esprit.services.ProduitsCRUD;
import edu.esprit.utilis.MyConnection;
import static edu.esprit.utilis.MyConnection.getInstance;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class Livraison1testController implements Initializable {

 
    private ImageView imageviewlivraisonid;
    @FXML
    private ScrollPane menu_scrollPaneshop;
    @FXML
    private GridPane menu_gridPaneshop;
    @FXML
    private Button donbtn;
    @FXML
    private Button eventbtn;
    @FXML
    private Button pubbtn;
    @FXML
    private Button dec;


  public void initialize(URL url, ResourceBundle rb) {
        // Fetch data for Cardshop cards
        List<Produits> cardshopProducts = getCardshopProducts();

        // Set grid constraints for the GridPane
        setGridConstraints();

        // Display the Cardshop cards
        displayCardshopCards(cardshopProducts);
    }   
  private void setGridConstraints() {
        int maxColumns = 3; // Number of columns per row

        // Clear existing constraints
        menu_gridPaneshop.getColumnConstraints().clear();
        menu_gridPaneshop.getRowConstraints().clear();

        // Set column constraints
        for (int i = 0; i < maxColumns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / maxColumns);
            menu_gridPaneshop.getColumnConstraints().add(column);
        }
    }
  public List<Produits> getCardshopProducts() {
    List<Produits> cardshopProducts = new ArrayList<>();
        MyConnection  connection = MyConnection.getInstance();


    try {
                Connection cnx = connection.getCnx();

        String query = "SELECT * FROM produits_info WHERE statut_prod = 'available'";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nomDuProduit = resultSet.getString("nom_prod");
            int prix = resultSet.getInt("prix_prod");
            String description = resultSet.getString("desc_prod");
            String statut = resultSet.getString("statut_prod");
            String image = resultSet.getString("image");

            Produits produit = new Produits(nomDuProduit, prix, description, statut, id, image);
            cardshopProducts.add(produit);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cardshopProducts;
}
 private void displayCardshopCards(List<Produits> cardshopProducts) {
    int row = 0;

    for (Produits produit : cardshopProducts) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cardshop.fxml"));
            AnchorPane cardPane = loader.load();
            CardshopController cardController = loader.getController();

            // Set the data for the Cardshop card
            cardController.setProduit(produit);

            // Add the card to the GridPane
            menu_gridPaneshop.add(cardPane, 0, row); // Always add to the first column (0)

            row++; // Move to the next row for the next card
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


   


    public List<Produits> getProduitsList() {
        List<Produits> produitsList = new ArrayList<>();
        ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();
        produitsList = produitsCRUDInstance.getProduitsList();
        return produitsList;
    }

    private void swingbtn(ActionEvent event) {/*
         String selectedProductName = getSelectedProductName();
    if (!selectedProductName.isEmpty()) {
        // Utilisez le nom du produit sélectionné comme vous le souhaitez
        System.out.println("Produit sélectionné : " + selectedProductName);
         // Vérifier que vous avez une instance de ProduitsCRUD
    ProduitsCRUD produitsCRUDInstance = new ProduitsCRUD();

    // Appeler la méthode insertNomProduit pour insérer le nom dans la base de données
    produitsCRUDInstance.insertNomProduit(selectedProductName,1);
        // Ajoutez ici le code pour passer à l'interface suivante ou effectuer d'autres actions avec le nom du produit sélectionné
    }
    
    // Vérifier si un produit est sélectionné dans le tableau
    Produits selectedProduit = prod1_view.getSelectionModel().getSelectedItem();
    if (selectedProduit == null) {
        // Aucun produit n'a été sélectionné, afficher une alerte
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Sélectionnez un produit avant de passer à l'interface suivante.");
        alert.showAndWait();
        return; // Ne passez pas à l'interface 2 si aucun produit n'est sélectionné
    }

    try {
        // Charger l'interface 2 depuis le fichier FXML (inscri.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscri.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la scène actuelle à partir de n'importe quel élément racine
        Stage currentStage = (Stage) prod1_view.getScene().getWindow();

        // Remplacer la scène actuelle par la nouvelle scène (interface 2)
        currentStage.setScene(scene);
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }

    public String getSelectedProductName() {
    Produits selectedProduit = prod1_view.getSelectionModel().getSelectedItem();

    if (selectedProduit != null) {
        return selectedProduit.getNomDuProduit();
    }

    // Si aucun produit n'est sélectionné, renvoyez une chaîne vide ou une valeur par défaut
    return ""; */
}

    @FXML
    private void donaction(ActionEvent event) {
        
        
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

    @FXML
    private void eventaction(ActionEvent event) {
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

    @FXML
    private void publicationaction(ActionEvent event) {
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