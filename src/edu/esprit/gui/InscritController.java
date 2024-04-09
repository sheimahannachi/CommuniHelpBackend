package edu.esprit.gui;

import edu.esprit.entities.CurrentUser;
import edu.esprit.services.EventCrud;
import edu.esprit.entities.Evennement;
import edu.esprit.entities.Participe;
import edu.esprit.entities.User;
import edu.esprit.services.SendMail;
import edu.esprit.services.EventCrud;
import edu.esprit.services.timer;
import edu.esprit.utilis.MyConnection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class InscritController implements Initializable {

    @FXML
    private TextField nomp;
    @FXML
    private TextField nump;
    @FXML
    private TextField mailp;
    @FXML
    private Button ajoutp;
    @FXML
    private Label labelid;

    EventCrud EC = new EventCrud();
    int eventId;
    int idajout ;
    @FXML
    private VBox badge;
    @FXML
    private ImageView imageView;

 User user ; 
    @FXML
    private Button telechargerBadge;
    @FXML
    private Button RT;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            String query = "SELECT nom,Num_tel ,email FROM user WHERE email = ?";
            Connection conn = MyConnection.getInstance().getCnx();

            try ( PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("email");
                    
                    int Num_Tel =rs.getInt("Num_tel");
                   nomp.setText(nom); // Set the retrieved name
                   nump.setText(String.valueOf(Num_Tel));
                   mailp.setText(prenom);
                
                
                
                
                
                
                
                } else {
                    // Handle the case when the user with the given email is not found in the database
                }
            } catch (SQLException e) {
                // Handle database errors
                e.printStackTrace();
            }
        }
        
        
        
    }
    
    void setTextFields(User loggedInUser) ///taabili le fenetre bel les donnes 
    {
        nomp.setText(loggedInUser.getNom());
        nump.setText(String.valueOf(loggedInUser.getNum_tel()));
        mailp.setText(loggedInUser.getPrenom());

        
        
    }

    public void setid(int id) {
        eventId = id;
        labelid.setText("ID de l'événement : " + eventId);
    }
    public void afficherSeulementId() {
    labelid.setText(String.valueOf(eventId));
}

    @FXML
  

private void ajouterparti(ActionEvent event) {
  
    String nom = nomp.getText();
    String num = nump.getText();
    String mail = mailp.getText();

    if (eventId > 0) {
        idajout = EC.ajouterParticipant(nom, num, mail, eventId);

        if (idajout > 0) {
            System.out.println("Participant ajouté avec succès avec l'ID : " + idajout);

            // Créez des étiquettes avec les données du formulaire
            Label nomLabel = new Label("Nom : " + nom);
            Label numLabel = new Label("Numéro : " + num);
            Label mailLabel = new Label("Email : " + mail);

            // Chargez l'image depuis le package "image"
  Image image = new Image(getClass().getResource("/images/logo.png").toExternalForm());
   ImageView imageView = new ImageView(image);

            // Ajoutez les étiquettes et l'image à la VBox
            badge.getChildren().addAll(nomLabel, numLabel, mailLabel, imageView);

            // Effacez les champs de formulaire après l'ajout
            nomp.clear();
            nump.clear();
            mailp.clear();
        } else {
            System.out.println("Échec de l'ajout du participant.");
        }
    } else {
        System.out.println("Erreur : ID de l'événement non défini.");
    }
}

   

   
   /* private void generateFile(String nom, String num, String mail) {
    try {
        // Spécifiez le chemin du fichier où vous souhaitez enregistrer les données
        File file = new File("C:\\Users\\MSI\\Desktop\\data.txt");

        // Créez un BufferedWriter pour écrire dans le fichier
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        // Écrivez les données dans le fichier
        writer.write("Nom : " + nom);
        writer.newLine(); // Pour passer à la ligne suivante
        writer.write("Numéro : " + num);
        writer.newLine();
        writer.write("Email : " + mail);

        // Fermez le BufferedWriter
        writer.close();

        System.out.println("Données enregistrées dans le fichier.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
*/
    @FXML
    private void telechargerBadge(ActionEvent event) {
        try {
        // Créez une capture d'écran de la VBox
        WritableImage snapshot = badge.snapshot(null, null);

        // Enregistrez la capture d'écran sous forme de fichier (par exemple, en PNG)
        File file = new File("badge.png");
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        System.out.println("Badge enregistré sous forme de fichier : " + file.getAbsolutePath());
    } catch (IOException e) {
        e.printStackTrace();
    }
        
    }


   

    /*@FXML
    private void retournn(ActionEvent event) { try {
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
    }*/

    @FXML
    private void retourtopra(ActionEvent event) { try {
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


    }}