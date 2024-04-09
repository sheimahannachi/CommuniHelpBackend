/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.commentaire;
import edu.esprit.utilis.MyConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author ASUS
 */
public class commentaireCrud {
    
    
    MyConnection myc =MyConnection.getInstance();
        Connection cnx  = myc.getCnx();
        
        
    public void ajouterCommentaire(commentaire c, int publicationId, int medecinId) {
    String requete1 = "INSERT INTO commentaire(contenuCommentaire, id, id_mededcin) VALUES(?, ?, ?)";
    try {
        PreparedStatement pst = cnx.prepareStatement(requete1);
        pst.setString(1, c.getContenuCommentaire());
        pst.setInt(2, publicationId); // Use the correct publicationId
        pst.setInt(3, CurrentUser.getLoggedInUser().getId()); // Use the correct medecinId

        pst.executeUpdate();
        System.out.println("Votre commentaire est ajouté");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

        public void supprimerCommentaire(int id_comm) {
    String requete2 = "DELETE FROM commentaire WHERE id_comm = ?";
    try {
        PreparedStatement pst = cnx.prepareStatement(requete2);
        pst.setInt(1, id_comm);
        pst.executeUpdate();
        System.out.println("Votre commentaire est supprimé");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

public void modifierCommentaire(commentaire c, int id_comm, int medecinId) {
    
    String query = "UPDATE commentaire SET contenuCommentaire=?, id_mededcin=? WHERE id_comm=?";
    try {
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setString(1, c.getContenuCommentaire());
        pst.setInt(2, CurrentUser.getLoggedInUser().getId()); // Use the correct medecinId
        pst.setInt(3, id_comm); // Use the provided id_comm parameter
        pst.executeUpdate();
        System.out.println("Votre commentaire est modifié");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}


        public List<commentaire> afficherCommentaires(int currentPublicationId) {
    List<commentaire> myList = new ArrayList<>();
    try {
        String requete4 = "SELECT * FROM commentaire WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(requete4);
        ps.setInt(1, currentPublicationId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            commentaire c = new commentaire(rs.getInt("id_comm"), rs.getString("contenuCommentaire"), rs.getInt("id"));
            myList.add(c);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}}


    

