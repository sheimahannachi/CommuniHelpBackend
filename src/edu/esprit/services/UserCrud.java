/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.User;
import edu.esprit.utilis.MyConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class UserCrud {
    
    Connection cnx2;
    List<User> mylist= new ArrayList<>();

    public UserCrud() {
        cnx2 = MyConnection.getInstance().getCnx(); 
                
    }
     public void supprimerUtilisateur(User user) {
        try {
            String requete = "delete from user where id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, user.getId());
            pst.executeUpdate();

            System.out.println("Utlisateur est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        public void ajouterUtilisateur()//insertion des objets d'une maniere statique 
    {
        try {
            //creation requette 
            String requete = "INSERT INTO User (email , nom , prenom, password, adresse, Num_tel , roles,specialite,status)" + " VALUES ('mimi2@gmail.com','mimi','lolo','123456','hdggdhh','1234567','user','medecin','inactif') ";
            //ramener la requette 
            Statement st = cnx2.createStatement(); //j'ai obtenu l'objet connexion par cerre ligne 
            st.executeUpdate(requete); //change la base de donnes 
            System.out.println("Utilisateur ajouté avec succes ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
        
         public void ajouterUtilisateur2(User u) //ajout dynamiqque 
    {
          
        try //ajout dynamiqque
        {
            String requete2 = "INSERT INTO user (`email`,`password`,`nom`,`prenom`,`adresse`,`Num_tel`,`roles`,`activation_token`,`status`,`specialite`) VALUES (?,?,?,?,?,?,?,?,'inactif',?)";
            PreparedStatement pst = cnx2.prepareStatement(requete2); //objet dedie pour les objet dynamique //statement est long  //PreparedStatemt : envoie une requête sans
            //paramètres à la base de données

            pst.setString(1, u.getEmail());
            pst.setString(2, u.getPassword());
            pst.setString(3, u.getNom());
            pst.setString(4, u.getPrenom());
            pst.setString(5, u.getAdresse());
            pst.setInt(6, u.getNum_tel());
            pst.setString(7, u.getRoles());
            pst.setString(8, u.getActivation_token());
            pst.setString(9,u.getSpecialite()); 
            

            pst.executeUpdate();
            System.out.println("Votre utilisateur est ajouté ");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
         
         public List<User> afficherUtilisateurs() {

        List<User> mylist = new ArrayList<>();

        try {
            String requete3 = "SELECT * FROM user";
            Statement st = cnx2.createStatement(); //L'interface Statement possède les méthodes
            //nécessaires pour réaliser les requêtes sur la base
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                User u = new User();

                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setPassword(rs.getString("password"));
                u.setSpecialite(rs.getString("specialite"));
                u.setNum_tel(rs.getInt("Num_tel"));
                u.setRoles(rs.getString("roles"));
                u.setAdresse(rs.getString("adresse"));
                u.setStatus(rs.getString("status"));

                mylist.add(u);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;

    }
    public void SupprimerUtilisateurid(int id) {
        try {
            Statement st = cnx2.createStatement(); //j'ai obtenu l'objet connexion par cerre ligne 
            String req = "DELETE FROM user WHERE id = " + id + "";
            st.executeUpdate(req);
            System.out.println("L'utilisateur  avec l'id = " + id + " a été supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
       public String hashMotDePasse(String motDePasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(motDePasse.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
     public void modifierUtilisateur(User u) {
        try {
            String requete5 = "UPDATE  user SET  email=?,nom=?,prenom=?,adresse=?,Num_tel=?,roles=?,specialite=? WHERE id=? ";
            PreparedStatement pst = cnx2.prepareStatement(requete5); //objet dedie pour les objet dynamique //statement est long 
            pst.setString(1, u.getEmail());
            pst.setString(2, u.getNom());
            pst.setString(3, u.getPrenom());
            pst.setString(4, u.getAdresse());
            pst.setInt(5, u.getNum_tel());
            pst.setString(6, u.getRoles());
            pst.setString(7, u.getSpecialite());
            pst.setInt(8, u.getId());
            pst.executeUpdate();

            System.out.println("Utlisateur est modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in modifierUtilisateur: " + ex.getMessage());
            ex.printStackTrace();

            
        }
    }
     
      public int chercherUser(String nom) throws SQLException {
        int id = 0;
        String requetee = "SELECT id FROM user where nom ='" + nom + "';";
        PreparedStatement pst = cnx2.prepareStatement(requetee);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
      
       public ObservableList<User> chercherUserR(String chaine) {
        String sql = "select user.id,user.nom ,user.prenom,user.Num_tel,user.adresse,user.email,user.role from user ";

        Connection cnx = MyConnection.getInstance().getCnx();
        String ch = "" + chaine + "%";
        System.out.println(sql);
        ObservableList<User> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);

            System.out.println(stee);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setNum_tel(rs.getInt("Num_tel"));
                u.setRoles(rs.getString("roles"));
                u.setAdresse(rs.getString("adresse"));
                u.setSpecialite(rs.getString("specialite"));
                myList.add(u);
                System.out.println(" utilisateur  trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
       
         public void modifierUtilisateur3(User u) {
        try {
            String req = "UPDATE `user` SET `nom` = '" + u.getNom() + "', `prenom` = '" + u.getPrenom() + "', `email` = '" + u.getEmail() + "',`Num_tel` = '" + u.getNum_tel() + "',`adresse` = '" + u.getAdresse()+"',`specialite=`'"+u.getSpecialite() + "' WHERE `user`.`id` = " + u.getId();
            Statement st = cnx2.createStatement();
            st.executeUpdate(req);
            System.out.println("Personne updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
         
            public User SearchByMail(String s) {
        User p = new User();
        String request = "SELECT * FROM user WHERE email ='" + s + "';";
        try {
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setEmail(rs.getString("email"));
                p.setNum_tel(rs.getInt("Num_tel"));
                p.setSpecialite(rs.getString("specialite"));
                p.setAdresse(rs.getString("adresse"));

                p.setPassword(rs.getString("password"));

                p.setRoles(rs.getString("roles"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("email existe ");
        return p;

    }
            
            public User getUserById(int id) {

        User u = new User();

        try {
            String requete3 = "SELECT * FROM `user` WHERE id="+id;
            Statement st = cnx2.createStatement(); //L'interface Statement possède les méthodes
            //nécessaires pour réaliser les requêtes sur la base
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                

                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setPassword(rs.getString("password"));
               
                u.setNum_tel(rs.getInt("Num_tel"));
                u.setRoles(rs.getString("roles"));
                u.setAdresse(rs.getString("adresse"));
                u.setSpecialite(rs.getString("specialite"));
                u.setStatus(rs.getString("status"));

              

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return u;

    }
            
            
            
            public List<User> afficherMedecin() {

        List<User> mylist = new ArrayList<>();

        try {
            String requete3 ="SELECT * FROM `user` WHERE roles='medecin'";
            Statement st = cnx2.createStatement(); //L'interface Statement possède les méthodes
            //nécessaires pour réaliser les requêtes sur la base
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                User u = new User();

                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setPassword(rs.getString("password"));
                u.setSpecialite(rs.getString("specialite"));
                u.setNum_tel(rs.getInt("Num_tel"));
                u.setRoles(rs.getString("roles"));
                u.setAdresse(rs.getString("adresse"));
                u.setStatus(rs.getString("status"));

                mylist.add(u);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;

    }
            
             public List<User> afficherAssociation() {

        List<User> mylist = new ArrayList<>();

        try {
            String requete3 ="SELECT * FROM `user` WHERE roles='association'";
            Statement st = cnx2.createStatement(); //L'interface Statement possède les méthodes
            //nécessaires pour réaliser les requêtes sur la base
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                User u = new User();

                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setPassword(rs.getString("password"));
                u.setSpecialite(rs.getString("specialite"));
                u.setNum_tel(rs.getInt("Num_tel"));
                u.setRoles(rs.getString("roles"));
                u.setAdresse(rs.getString("adresse"));
                u.setStatus(rs.getString("status"));

                mylist.add(u);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;

    }
             
             
}
