/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.esprit.entities.CLivraison;
import edu.esprit.utilis.MyConnection;

/**
 *
 * @author USER
 */
public class livraisonCRUD {
      
         Connection connection;

    public livraisonCRUD() {
        connection = MyConnection.getInstance().getCnx();
    }

   public void addLivraison(CLivraison livraison) {
    try {
          String insertData = "INSERT INTO livraison_p (nomliv, prenomliv, phonelivr, adresse, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            preparedStatement.setString(1, livraison.getNom());
            preparedStatement.setString(2, livraison.getPrenom());
            preparedStatement.setString(3, livraison.getNumeroTelephone()); // Utilisez setInt pour le prix
            preparedStatement.setString(4, livraison.getAdresse());
            preparedStatement.setString(5, livraison.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Afficher un message de succès si l'insertion a réussi
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Données de livraison ajoutées avec succès.");
                
            } else {
                // Afficher une alerte si l'insertion a échoué
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de l'ajout des données de livraison.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<CLivraison> getAllLivraisons() {
        List<CLivraison> livraisons = new ArrayList<>();

        try {
            String selectData = "SELECT * FROM livraison_p";
            PreparedStatement preparedStatement = connection.prepareStatement(selectData);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nom = resultSet.getString("nomliv");
                String prenom = resultSet.getString("prenomliv");
                String numeroTelephone = resultSet.getString("phonelivr");
                String adresse = resultSet.getString("adresse");
                String email = resultSet.getString("email");

                CLivraison livraison = new CLivraison(nom, prenom, numeroTelephone, adresse, email);
                livraisons.add(livraison);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return livraisons;
    }
   public void insertIntoListec() {
    try {
        // Étape 1 : Récupérer le dernier nom du produit depuis idprod_info
        String selectNomProduit = "SELECT nomprodR FROM idprod_info ORDER BY id DESC LIMIT 1";
        PreparedStatement nomProduitStatement = connection.prepareStatement(selectNomProduit);
        ResultSet nomProduitResultSet = nomProduitStatement.executeQuery();
        
        String dernierNomProduit = null;
        if (nomProduitResultSet.next()) {
            dernierNomProduit = nomProduitResultSet.getString("nomprodR");
        }
        
        // Étape 2 : Récupérer les informations de la dernière ligne de livraison_p
        String selectDerniereLivraison = "SELECT nomliv, prenomliv, phonelivr, adresse, email FROM livraison_p ORDER BY id DESC LIMIT 1";
        PreparedStatement derniereLivraisonStatement = connection.prepareStatement(selectDerniereLivraison);
        ResultSet derniereLivraisonResultSet = derniereLivraisonStatement.executeQuery();
        
        String nomLivraison, prenomLivraison, numeroTelephone, adresse, email;
               if (derniereLivraisonResultSet.next()) {
            nomLivraison = derniereLivraisonResultSet.getString("nomliv");
            prenomLivraison = derniereLivraisonResultSet.getString("prenomliv");
            numeroTelephone = derniereLivraisonResultSet.getString("phonelivr");
            adresse = derniereLivraisonResultSet.getString("adresse");
            email = derniereLivraisonResultSet.getString("email");

            // Modification de cette partie du code pour éviter la troncature de données
          String contact = numeroTelephone;

            // Étape 3 : Insérer ces informations dans listec_
          String insertData = "INSERT INTO listec (nomproduit, contact, nomdest, emailc_, adressec_) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            preparedStatement.setString(1, dernierNomProduit);
            preparedStatement.setString(2, contact); // Utilisez la variable contact
            preparedStatement.setString(3, nomLivraison);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, adresse);

            int rowsAffected = preparedStatement.executeUpdate();


            if (rowsAffected > 0) {
                // Afficher un message de succès si l'insertion a réussi
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Données insérées avec succès dans listec_.");
               
            } else {
                // Afficher une alerte si l'insertion a échoué
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur lors de l'insertion des données dans listec_.");
                alert.showAndWait();
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
   }}
    
    