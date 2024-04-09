/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.CurrentUser;
import edu.esprit.entities.Medecin;
import edu.esprit.entities.User;
import edu.esprit.entities.enumMedecin;
import edu.esprit.entities.publications;
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
public class publicationsCrud {

    MyConnection myc = MyConnection.getInstance();
    Connection cnx = myc.getCnx();

    public void ajouterpublications(publications P) {
        String query = "INSERT INTO publications(texte,dateDePublication,specialiteassocie,tags, imagePath,id_med)"
                + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setString(1, P.getTexte());
            pst.setDate(2, Date.valueOf(P.getDateDePublication()));
            pst.setString(3, P.getSpecialite());
            pst.setString(4, P.getTags());
            pst.setString(5, P.getImagePath());
            System.out.println(P.getImagePath());

            pst.setInt(6,CurrentUser.getLoggedInUser().getId());
            
            pst.executeUpdate();
            System.out.println("Votre publication est ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

    public void supprimerpublications(int id) {
        String requete2 = "DELETE FROM publications WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Votre commentaire est supprimé");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifierpublications(publications p, int id, int id_med) {

        String query = "UPDATE publications SET texte=?, dateDePublication=?, specialiteassocie=?,tags=?, imagePath=?,id_med=? WHERE id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, p.getTexte());
            pst.setDate(2, Date.valueOf(p.getDateDePublication()));
            pst.setString(3, p.getSpecialite());
            pst.setString(4, p.getTags());
            pst.setString(5, p.getImagePath());
            pst.setInt(6, CurrentUser.getLoggedInUser().getId()); // Set the value for nbJaime
            pst.setInt(7, id);

            pst.executeUpdate();
            System.out.println("Votre publication est modifiée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

public List<publications> afficherpublications() {
    List<publications> myList = new ArrayList<>();
    try {
        String query = "SELECT id, texte, dateDePublication, specialiteassocie,tags, imagePath FROM publications";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            publications P = new publications();

            P.setId(rs.getInt("id"));
            P.setTexte(rs.getString("texte"));
            P.setDateDePublication(rs.getDate("dateDePublication").toLocalDate());
            P.setSpecialite(rs.getString("specialiteassocie")); // Set the specialite directly as a String
            P.setTags(rs.getString("tags"));
            P.setImagePath(rs.getString("imagePath"));

            myList.add(P);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return myList;
}


  public User getMedecinForPublication(int publicationId) {
    String query = "SELECT u.* FROM publications p \n"
            + "JOIN user u ON p.id_med = u.id \n"
            + "WHERE p.id = ? AND u.roles = 'medecin';";

    try {
        PreparedStatement preparedStatement = cnx.prepareStatement(query);
        preparedStatement.setInt(1, publicationId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            User medecinUser = new User();
            medecinUser.setId(resultSet.getInt("id"));
            medecinUser.setNom(resultSet.getString("nom"));
            medecinUser.setPrenom(resultSet.getString("prenom"));
            medecinUser.setAdresse(resultSet.getString("Adresse"));
            medecinUser.setEmail(resultSet.getString("email"));
            medecinUser.setSpecialite(resultSet.getString("specialite"));
            medecinUser.setRoles("medecin");

            System.out.println("Medecin ID: " + medecinUser.getId());
            System.out.println("Medecin Nom: " + medecinUser.getNom());
            System.out.println("Medecin Prenom: " + medecinUser.getPrenom());

            return medecinUser;
        } else {
            System.out.println("No medecin found for publication ID: " + publicationId);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle the exception as needed
    }
    return null;
}


}
