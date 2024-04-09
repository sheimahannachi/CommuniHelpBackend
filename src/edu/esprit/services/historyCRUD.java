package edu.esprit.services;

import edu.esprit.entities.history;
import edu.esprit.utilis.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class historyCRUD {
    private Connection cnx;

   
    public historyCRUD() {
        cnx = MyConnection.getInstance().getCnx();
    }

public void insertHistoryRecord(history historyRecord) {
    try {
        String query = "INSERT INTO history (article_id, article_ville, description, contact,  image, donation_id, montant, email, nom, prenom, donation_ville, carte_bancaire) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, historyRecord.getArticleId());
        pst.setString(2, historyRecord.getArticleVille());
        pst.setString(3, historyRecord.getDescription());
        pst.setString(4, historyRecord.getContact());
        
       

        pst.setString(5, historyRecord.getImage());
        pst.setInt(6, historyRecord.getDonationId());
        pst.setFloat(7, historyRecord.getMontant());
        pst.setString(8, historyRecord.getEmail());
        pst.setString(9, historyRecord.getNom());
        pst.setString(10, historyRecord.getPrenom());
        pst.setString(11, historyRecord.getDonationVille());
        pst.setString(12, historyRecord.getCarteBancaire());

        pst.executeUpdate();
        System.out.println("History record ajouté avec succès!");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
public List<history> getHistory() {
    List<history> historyList = new ArrayList<>();

    try {
        String query = "SELECT * FROM history";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            history history = new history();
            history.setArticleId(rs.getInt("article_id"));
            history.setArticleVille(rs.getString("article_ville"));
            history.setDescription(rs.getString("description"));
            history.setContact(rs.getString("contact"));
            
            
            history.setImage(rs.getString("image"));
            history.setDonationId(rs.getInt("donation_id"));
            history.setMontant(rs.getFloat("montant"));
            history.setEmail(rs.getString("email"));
            history.setNom(rs.getString("nom"));
            history.setPrenom(rs.getString("prenom"));
            history.setDonationVille(rs.getString("donation_ville"));
           // history.setCarteBancaire(rs.getString("carte_bancaire"));

            historyList.add(history);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return historyList;
}


 public int getDonationCount() {
    int count = 0;

    try {
        String query = "SELECT COUNT(*) FROM history";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return count;
}
}
