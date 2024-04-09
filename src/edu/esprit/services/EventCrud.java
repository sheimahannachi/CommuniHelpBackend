/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Evennement;
import edu.esprit.utilis.MyConnection;

import java.awt.Event;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 *
 * @author MSI
 */
public class EventCrud {
    public void ajouterEvent(Evennement E,String path){
        LocalDate Date = E.getDate();
        
         try {
             
            String requete = "INSERT INTO test(id,nom, Lieu,date,path)"
                    + "VALUES (0,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                                    .prepareStatement(requete);
           
            pst.setString(1, E.getNom());
            pst.setString(2, E.getLieu());
            
            java.sql.Date sqlDate =  java.sql.Date.valueOf(Date);
            pst.setDate(3,sqlDate);
            pst.setString(4,path);
            
            
             System.out.println(E.getPath());
             
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
}
    public void modifierEvent(Evennement E) {
  int rowsUpdated =0;
        try {
        
        String requete = "UPDATE test SET nom = ?, Lieu = ?, date = ? WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, E.getNom());
        pst.setString(2, E.getLieu());
        LocalDate newDate = E.getDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(newDate);
        pst.setDate(3, sqlDate);
        // Assurez-vous de définir l'ID de l'événement que vous souhaitez mettre à jour
        pst.setInt(4, E.getid()); // Supposons que getId() renvoie l'ID de l'événement

        rowsUpdated = pst.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("L'événement a été modifié avec succès.");
        } else {
            System.out.println("Aucun événement n'a été modifié.");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la modification de l'événement : " + ex.getMessage());
        ex.printStackTrace();
    }
    System.out.println(rowsUpdated);
}
public void supprimerEvent(int eventId) {
    try {
        String requete = "DELETE FROM test WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        
        // Assurez-vous de définir l'ID de l'événement que vous souhaitez supprimer
        pst.setInt(1, eventId);

        int rowsDeleted = pst.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("L'événement a été supprimé avec succès.");
        } else {
            System.out.println("Aucun événement n'a été supprimé (ID non trouvé).");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }}

public int getParticipantCount(int eventId) {
    int count = 0;
    try {
        String query = "SELECT COUNT(nbr) from test where id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setInt(1, eventId);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return count;
}
public void incrementParticipantCount(int id) {
    try {
        String query = "UPDATE test SET nbr = nbr + 1 WHERE id = ?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    public int ajouterParticipant(String nom, String num, String mail, int eventId) {
    int idajout = 0; // Initialisez l'ID à 0 (ou à une autre valeur par défaut)

    try {
        String requete = "INSERT INTO participants(nom, num, mail, id_ev) VALUES (?, ?, ?, ?)";
        Connection conn = MyConnection.getInstance().getCnx();
        PreparedStatement pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, nom);
        pst.setString(2, num);
        pst.setString(3, mail);
        pst.setInt(4, eventId);

        int rowsAffected = pst.executeUpdate();
        
        if (rowsAffected > 0) {
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                idajout = generatedKeys.getInt(1); // Récupérez l'ID généré
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return idajout;

}
 public void ajouterRelation(int id_evenement, int participantId) {
    try {
        String requete = "INSERT INTO relation(id_evenement, id_participant) VALUES (?, ?)";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id_evenement);
        pst.setInt(2, participantId);
        pst.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());

    }}
 public List<String> GetParticipentByEvent (int lastEventId) {
   
        List<String> mailLlist = new ArrayList();
        String requete = "SELECT mail FROM test t JOIN participants p ON t.id = p.id_ev";
       try {
          PreparedStatement pst = MyConnection.getInstance().getCnx()
                                    .prepareStatement(requete);
           
              ResultSet rs =pst.executeQuery(requete);
            
            while (rs.next()){
                mailLlist.add(rs.getString("mail"));
            
            }
      }catch (SQLException e) {
            e.printStackTrace();
        }
     
        return mailLlist ;
        
    

    }
  public static int getLastEventId() {
        int lastEventId = -1; // Initialize with a default value

        String sql = "SELECT MAX(id_ev) AS last_id_event FROM participants";

        try (Connection connection = MyConnection.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                lastEventId = resultSet.getInt("last_id_event");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastEventId;
 
 
 
}}




     


    
    
    
