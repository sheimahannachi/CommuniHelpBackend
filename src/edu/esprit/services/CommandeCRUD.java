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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import edu.esprit.entities.ListeCData;
import edu.esprit.utilis.MyConnection;

/**
 *
 * @author USER
 */
public class CommandeCRUD {
     Connection cnx2;
      public CommandeCRUD() {
        
        cnx2= MyConnection.getInstance().getCnx();
    }
   public List<ListeCData> afficherListeC() {
    // Créez une liste pour stocker les données de la table listec_
    List<ListeCData> listeCDataList = new ArrayList<>();
    try {
        // Établissez une connexion à la base de données et exécutez la requête SQL
        String selectQuery = "SELECT nomproduit, contact, nomdest, emailc_, adressec_ FROM listec";
        PreparedStatement preparedStatement = cnx2.prepareStatement(selectQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Parcourez les résultats de la requête et ajoutez-les à la liste
        while (resultSet.next()) {
            String prodCo = resultSet.getString("nomproduit");
            String contactCo = resultSet.getString("contact");
            String nomCo = resultSet.getString("nomdest");
            String emailCo = resultSet.getString("emailc_");
            String adresseCo = resultSet.getString("adressec_");
            listeCDataList.add(new ListeCData(prodCo, contactCo, nomCo, emailCo, adresseCo));
        }

        // Fermez la connexion (en utilisant try-with-resources)
        // Ceci permet de libérer automatiquement les ressources de la connexion
        // Une fois que le bloc try est terminé
        // Aucun besoin de fermer la connexion explicitement
    } catch (SQLException ex) {
        // En cas d'erreur, affichez l'erreur
        ex.printStackTrace();
    }

    // Renvoyez la liste des données de listec_
    return listeCDataList;
}

   public void updateProductStatusToAvailable(String productName) {
    try {
        // Mise à jour du statut du produit dans la table produits_info
        String updateQuery = "UPDATE produits_info SET statut_prod = ? WHERE nom_prod = ?";
        PreparedStatement preparedStatement = cnx2.prepareStatement(updateQuery);

        // Définissez le nouveau statut sur "available"
        preparedStatement.setString(1, "available");

        // Spécifiez le nom du produit que vous souhaitez mettre à jour
        preparedStatement.setString(2, productName);

        // Exécutez la mise à jour
        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Statut du produit mis à jour avec succès dans la table produits_info.");
            
            // Maintenant, supprimez le produit de la table listec_
            String deleteQuery = "DELETE FROM listec WHERE nomproduit = ?";
            PreparedStatement deleteStatement = cnx2.prepareStatement(deleteQuery);
            deleteStatement.setString(1, productName);
            
            int rowsDeleted = deleteStatement.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("Produit supprimé avec succès de la table listec.");
            } else {
                System.out.println("Aucun produit supprimé de la table listec.");
            }
        } else {
            System.out.println("Aucun produit mis à jour dans la table produits_info. Vérifiez le nom du produit.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
 
    }
}
   public void insertChangementType(String type, String prodName) {
    try {
        String insertQuery = "INSERT INTO prodadmin (prodname, typechangement) VALUES (?, ?)";
        PreparedStatement preparedStatement = cnx2.prepareStatement(insertQuery);
        preparedStatement.setString(1, prodName);
        preparedStatement.setString(2, type);
        preparedStatement.executeUpdate();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
   }
   
   
}
 