/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.DonationForm;
import edu.esprit.entities.enumVille.ville;
import edu.esprit.utilis.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class donationCRUD {

    private Connection cnx;

    public donationCRUD() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void ajouterdonationForm(DonationForm donationForm) {
        try {
            String query = "INSERT INTO donationforms (	id ,montant, email, nom, prenom, ville, carte_bancaire) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, donationForm.getIdDon());
            pst.setFloat(2, donationForm.getMontant());
            pst.setString(3, donationForm.getEmail());
            pst.setString(4, donationForm.getNom());
            pst.setString(5, donationForm.getPrenom());
            pst.setString(6, donationForm.getVille().name());
            pst.setString(7, donationForm.getCarteBancaire());

            pst.executeUpdate();
            System.out.println("DonationForm ajouté avec succès!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<DonationForm> getDonationForms() {
        List<DonationForm> donationForms = new ArrayList<>();

        try {
            String query = "SELECT * FROM donationForms";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                DonationForm donationForm = new DonationForm();
                donationForm.setIdDon(rs.getInt("id"));
                donationForm.setMontant(rs.getFloat("montant"));
                donationForm.setEmail(rs.getString("email"));
                donationForm.setNom(rs.getString("nom"));
                donationForm.setPrenom(rs.getString("prenom"));
                donationForm.setVille(ville.valueOf(rs.getString("ville"))); // Assuming you store the enum name as a string
                donationForm.setCarteBancaire(rs.getString("carte_bancaire"));
                donationForms.add(donationForm);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return donationForms;
    }

    public void modifierDonationForm(DonationForm donationForm) {
        try {
            String query = "UPDATE donationforms SET montant=?, email=?, nom=?, prenom=?, ville=?, carte_bancaire=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setFloat(1, donationForm.getMontant());
            pst.setString(2, donationForm.getEmail());
            pst.setString(3, donationForm.getNom());
            pst.setString(4, donationForm.getPrenom());
            pst.setString(5, donationForm.getVille().name()); // Assuming 'ville' is an enum
            pst.setString(6, donationForm.getCarteBancaire());
            pst.setInt(7, donationForm.getIdDon());

            pst.executeUpdate();
            System.out.println("DonationForm modifié avec succès!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimerDonationForm(int idDon) {
        try {
            String query = "DELETE FROM donationforms WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, idDon);
            pst.executeUpdate();
            System.out.println("DonationForm supprimé avec succès!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
}
