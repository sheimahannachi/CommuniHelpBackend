/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.User;
import edu.esprit.services.UserCrud;
import edu.esprit.utilis.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 
public class AdminController implements Initializable {

    @FXML
    private Button btndeconnexion;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> Nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> email;
    
    @FXML
    private TableColumn<?, ?> numtel;
    @FXML
    private TableColumn<?, ?> adresse;
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnmodifier;
    
     
    @FXML
    private TableView<User> tableviewUser;
    @FXML
    private TableColumn<?, ?> roles;
    @FXML
    private Button actualiser;
    /**

    /**
     * Initializes the controller class.
     */
    
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
     User user = null ; 
    @FXML
    private TextField fxreche;
    @FXML
    private AnchorPane fxFront;
    @FXML
    private TableColumn<?, ?> status;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnajouterUser;
    @FXML
    private Label fxemail;
    @FXML
    private Button btnajASS;
    @FXML
    private Button btn_medcin;
    @FXML
    private TableColumn<?, ?> specialité;
    @FXML
    private Button btnAssociation;
    @FXML
    private Button btnCause;
    @FXML
    private Button btnshop;
    @FXML
    private Button btnevent;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSearch1;
    @FXML
    private Button btnSerASS;
    @FXML
    private Button btnmedadmin;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //actualiserAuto();
         btnsupprimer.setVisible(false);
         actualiser.setVisible(false); 
         btnajouter.setVisible(false);
         btnajASS.setVisible(false);
         btnajouterUser.setVisible(false);
         btnmodifier.setVisible(false);
         btnSearch.setVisible(false);
         btnSerASS.setVisible(false);
         btnSearch1.setVisible(false);
         User loggedInUser = CurrentUser.getLoggedInUser();
         fxemail.setText(loggedInUser.getEmail());
    }    

    
    @FXML
      public void showRec() //affiche une liste utilisateur fe table view 
      {
           btnSearch.setVisible(true);
        btnSerASS.setVisible(false);
        btnSearch1.setVisible(false);
         btnmodifier.setVisible(true);
         btnsupprimer.setVisible(true);
         btnajouterUser.setVisible(true); 
         specialité.setVisible(false);
         btnajASS.setVisible(false);
         btnajouter.setVisible(false);
         
         ObservableList<User> list = getUserList();
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));

      
         tableviewUser.setItems(list);
         
     }
      
      
    
      public  ObservableList<User> getUserList() { //methode affichage mtaa liste utilisateur  fe table view 
         cnx = MyConnection.getInstance().getCnx();
        
        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
                String query2="SELECT * FROM `user` WHERE roles='user'";
                PreparedStatement smt = cnx.prepareStatement(query2);
                User user;
                ResultSet rs= smt.executeQuery();
            while(rs.next()){ //parcour les enregistrement retoune par la requette sql 
                 user = new User(rs.getInt("id"), rs.getString("email"),rs.getString("nom"), rs.getString("prenom"),rs.getString("specialite"), rs.getString("adresse"), rs.getInt("Num_tel"),rs.getString("roles"),rs.getString("status"));
                UserList.add(user);//ajout utilisateur fe liste 
            }
                System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;
   
    }
      
     
      public  ObservableList<User> getMedecinList() { //methode affichage mtaa liste utilisateur  fe table view 
         cnx = MyConnection.getInstance().getCnx();
        
        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
                String query2="SELECT * FROM `user` WHERE roles='medecin'";
                PreparedStatement smt = cnx.prepareStatement(query2);
                User user;
                ResultSet rs= smt.executeQuery();
            while(rs.next()){ //parcour les enregistrement retoune par la requette sql 
                 user = new User(rs.getInt("id"), rs.getString("email"),rs.getString("nom"), rs.getString("prenom"),rs.getString("specialite"), rs.getString("adresse"), rs.getInt("Num_tel"),rs.getString("roles"),rs.getString("status"));
                UserList.add(user);//ajout utilisateur fe liste 
            }
                System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;
   
    }
      
      public  ObservableList<User> getAssociList() { //methode affichage mtaa liste utilisateur  fe table view 
         cnx = MyConnection.getInstance().getCnx();
        
        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
                String query2="SELECT * FROM `user` WHERE roles='association'";
                PreparedStatement smt = cnx.prepareStatement(query2);
                User user;
                ResultSet rs= smt.executeQuery();
            while(rs.next()){ //parcour les enregistrement retoune par la requette sql 
                 user = new User(rs.getInt("id"), rs.getString("email"),rs.getString("nom"), rs.getString("prenom"),rs.getString("specialite"), rs.getString("adresse"), rs.getInt("Num_tel"),rs.getString("roles"),rs.getString("status"));
                UserList.add(user);//ajout utilisateur fe liste 
            }
                System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;
   
    }
    

    @FXML
    private void suprimer(ActionEvent event) {
                UserCrud u=new UserCrud();
  
               User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
    
                u.supprimerUtilisateur(user);
                refresh(); 
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(" :: Succes Message ");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur supprimé");
                alert.showAndWait(); 
    }
    
   
    @FXML
    private void modifier(ActionEvent event) {
                            user = (User) tableviewUser.getSelectionModel().getSelectedItem(); //recuperation du utilisateur selectionné 
                           if (user!=null){
                            FXMLLoader loader = new FXMLLoader ();//creation de FXMLLoader 
                            loader.setLocation(getClass().getResource("ModifierUtilisateur.fxml")); //emplacement du fichier fxml 
                            try {
                                loader.load();
                            } catch (Exception ex) {
                               System.err.println(ex.getMessage());
                            }
                            
                           ModifierUtilisateurController muc = loader.getController(); //recuperation deu controller de modification 
                           //mrc.setUpdate(true);
                           muc.setTextFields(user);
                           muc.setUserData(user);//bech taabili les text field eli hachty bihom 
                           //tkhajlk fenetre mtaa modification 
                            Parent parent = loader.getRoot(); 
                            Stage stage = new Stage(); //affichage de la fenetre 
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            showRec();
                           }
    }

    private void refresh() //mettre a jour du continue du tableView 
    {
          ObservableList<User> list = getUserList();
          ObservableList<User> list2 = getMedecinList(); 
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));
         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
       

         tableviewUser.setItems(list2);
         tableviewUser.setItems(list);
       
    }


    void setNom(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setPrenom(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void Search(ActionEvent event) {
        btnSearch.setVisible(true);
        btnSerASS.setVisible(false);
        btnSearch1.setVisible(false);
           ObservableList<User> list1 = getUserList4(fxreche.getText());
         
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));

        tableviewUser.setItems(list1);
       
        
        
    }
   
    public ObservableList<User> getmedecinSearch(String search) {
    ObservableList<User> userSpec = FXCollections.observableArrayList();
    UserCrud userCrud = new UserCrud();
    
    List<User> allUsers = userCrud.afficherMedecin();
    
    userSpec.addAll(
        allUsers.stream()
            .filter(user -> 
                user.getSpecialite()!= null && user.getSpecialite().contains(search) 
               
            )
            .collect(Collectors.toList())
    );
    return userSpec;
    }
    
      public ObservableList<User> getAssociationSearch(String search) {
    ObservableList<User> userAsso = FXCollections.observableArrayList();
    UserCrud userCrud = new UserCrud();
    
    List<User> allUsers = userCrud.afficherAssociation();
    
    userAsso.addAll(
        allUsers.stream()
            .filter(user -> 
                user.getNom()!= null && user.getNom().contains(search) 
               
            )
            .collect(Collectors.toList())
    );
    return userAsso;
    }

    
     public ObservableList<User> getUserList4(String search) {
    ObservableList<User> userList1 = FXCollections.observableArrayList();
    UserCrud userCrud = new UserCrud();
    
    List<User> allUsers = userCrud.afficherUtilisateurs();
    
    userList1.addAll(
        allUsers.stream()
            .filter(user -> 
                user.getNom() != null && user.getNom().contains(search) ||
                user.getPrenom() != null && user.getPrenom().contains(search)
            )
            .collect(Collectors.toList())
    );

    return userList1;
}

    @FXML
    private void AjouterMedecin(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml); 
    }

    @FXML
    private void AddUser(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("AjouterUser.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml); 
    }

    @FXML
    private void logout(ActionEvent event) {
           
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Confirmation de déconnexion");
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == buttonTypeYes) {
            Stage stage = (Stage) btndeconnexion.getScene().getWindow();
            stage.close();
            Stage home = new Stage();
             try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                        Scene scene = new Scene(fxml);
                        home.setScene(scene);
                        home.show();
                        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage1.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
        }
    }

    @FXML
    private void AjouterAsociation(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AjouterAssociation.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
    }

    @FXML
    private void showMedcin(ActionEvent event) {
        btnajouter.setVisible(true);
        btnmodifier.setVisible(true); 
        btnsupprimer.setVisible(true);
        actualiser.setVisible(true);
        specialité.setVisible(true);
        prenom.setVisible(true);
         btnSearch.setVisible(false);
        btnSerASS.setVisible(false);
        btnSearch1.setVisible(true);
        ObservableList<User> list = getMedecinList();
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));

         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
         tableviewUser.setItems(list);
         

    }

    @FXML
    private void affiAssoci(ActionEvent event) {
        btnajASS.setVisible(true);
        btnmodifier.setVisible(true); 
        btnsupprimer.setVisible(true);
        actualiser.setVisible(true);
        specialité.setVisible(false);
        btnajouter.setVisible(false);
        btnajouterUser.setVisible(false);
        prenom.setVisible(false);
         btnSearch.setVisible(false);
        btnSerASS.setVisible(true);
        btnSearch1.setVisible(false);
        ObservableList<User> list = getAssociList();
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
         tableviewUser.setItems(list);
        
    }

    @FXML
    private void actualiser(ActionEvent event) {
        ObservableList<User> list = getUserList();
          ObservableList<User> medd = getMedecinList(); 
          ObservableList<User> asso = getAssociList(); 
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));
         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
       

         tableviewUser.setItems(asso);
         tableviewUser.setItems(medd);
         tableviewUser.setItems(list);
    }
    
    public void actualiserAuto()
    {
         ObservableList<User> list = getUserList();
          ObservableList<User> medd = getMedecinList(); 
          ObservableList<User> asso = getAssociList(); 
         id.setCellValueFactory(new PropertyValueFactory<>("id"));
         status.setCellValueFactory(new PropertyValueFactory<>("status"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
         Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
         prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
         adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));
         roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
       

         
         tableviewUser.setItems(list);
         tableviewUser.setItems(asso);
         tableviewUser.setItems(medd);
    }

  
    @FXML
    private void ShowCause(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("admin2.fxml"));
        fxFront.getChildren().removeAll();
        fxFront.getChildren().setAll(fxml);
    }

    @FXML
    private void Shop(ActionEvent event) throws IOException {
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
    private void EventShow(ActionEvent event) {
                try {
        // Load the FaireDon.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("eventadmin.fxml"));
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
    private void SearchM(ActionEvent event) {
       
         ObservableList<User> userspec= getmedecinSearch(fxreche.getText());
        
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));

       
        tableviewUser.setItems(userspec);
       
    }

    @FXML
    private void SearchAsso(ActionEvent event) {
      
         ObservableList<User> userspec= getAssociationSearch(fxreche.getText());
        
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        specialité.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));

       
        tableviewUser.setItems(userspec);
    }

    @FXML
    private void adminaction(ActionEvent event) {
       try {
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
