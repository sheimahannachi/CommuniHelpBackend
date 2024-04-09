/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package edu.esprit.services;


import edu.esprit.utilis.MyConnection;
import edu.esprit.entities.Article;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List; 
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class articleCRUD {
   Connection  cnx ;

    public articleCRUD() { 
    cnx = MyConnection.getInstance().getCnx();
    }
  
    public void ajouterArticle(Article article) {
    try {
        String query = "INSERT INTO article (id, ville, description, contact, creation_date, image) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, article.getId());
        pst.setString(2, article.getVille());
        pst.setString(3, article.getDescription());
        pst.setString(4, article.getContact());

        // Convert LocalDate to String and insert it as VARCHAR
        String creationDateStr = article.getCreationDate().toString();
        pst.setString(5, creationDateStr);

        pst.setString(6, article.getImage());

        pst.executeUpdate();
        System.out.println("Article ajouté avec succès!");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
    
    
    
    
    
    
    
    
   
    
    public List<Article> getArticles() {
    List<Article> articleList = new ArrayList<>();

    try {
        String query = "SELECT * FROM article";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setVille(rs.getString("ville"));
            article.setDescription(rs.getString("description"));
            article.setContact(rs.getString("contact"));

           //  Retrieve the date as a String from the database
           String dateString = rs.getString("creation_date");
            LocalDate creationDate = LocalDate.parse(dateString); // Convert the String to LocalDate

            article.setCreationDate(creationDate);
            article.setImage(rs.getString("image"));
            articleList.add(article);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return articleList;
}


   public void modifierArticle(Article article) {
    try {
        String query = "UPDATE article SET ville=?, description=?, contact=?, creation_date=?, image=? WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(query);

        pst.setString(1, article.getVille());
        pst.setString(2, article.getDescription());
        pst.setString(3, article.getContact());

        // Convert LocalDate to String and update it as VARCHAR
        String creationDateStr = article.getCreationDate().toString();
        pst.setString(4, creationDateStr);

        pst.setString(5, article.getImage());
        pst.setInt(6, article.getId());

        pst.executeUpdate();
        System.out.println("Article modifié avec succès!");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

public void supprimerArticle(int id) {
    try {
        String query = "DELETE FROM article WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Article supprimé avec succès!");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

    

    
}
