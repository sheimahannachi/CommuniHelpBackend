/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import edu.esprit.entities.NotificationManager;
import edu.esprit.entities.Produits;
import edu.esprit.utilis.MyConnection;

/**
 *
 * @author USER
 */
public class ProduitsCRUD {
    Connection cnx2;
    

    public ProduitsCRUD() {
        
        cnx2= MyConnection.getInstance().getCnx();
    }
    
  
    public void addProd(Produits P) {
    try {
        if (P.getNomDuProduit().isEmpty() || P.getDescription().isEmpty() || P.getStatut() == null) {
            // Afficher une alerte si des champs obligatoires sont manquants
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
        } else {
            // Vérifier si le produit existe déjà (utilisez une requête SQL pour cela)
            String checkData = "SELECT nom_prod FROM produits_info WHERE nom_prod = ?";
            PreparedStatement checkStatement = cnx2.prepareStatement(checkData);
            checkStatement.setString(1, P.getNomDuProduit());
            ResultSet result = checkStatement.executeQuery();

            if (result.next()) {
                // Afficher une alerte si le produit existe déjà
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR Message");
                alert.setHeaderText(null);
                alert.setContentText("Le produit avec le nom " + P.getNomDuProduit() + " existe déjà.");
                alert.showAndWait();
            } else {
                // Insérer le produit dans la base de données
                String insertData = "INSERT INTO produits_info(nom_prod, prix_prod, desc_prod, statut_prod,image) VALUES (?, ?, ?, ?,?)";
                PreparedStatement pst = cnx2.prepareStatement(insertData);
                pst.setString(1, P.getNomDuProduit());
                pst.setInt(2, P.getPrix());
                pst.setString(3, P.getDescription());
                pst.setString(4, P.getStatut());
                 pst.setString(5, P.getImage());

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    // Afficher un message de succès si l'insertion a réussi
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Ajout effectué avec succès.");
                    alert.showAndWait();
                } else {
                    // Afficher une alerte si l'insertion a échoué
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de l'ajout du produit.");
                    alert.showAndWait();
                }
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    public List<Produits> afficherP() {
        List<Produits> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM produits_info";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Produits p = new Produits();
            p.setNomDuProduit(rs.getString("nom_prod")); // Utilisez le nom de la colonne de la base de données
            p.setPrix(rs.getInt("prix_prod")); // Utilisez le nom de la colonne de la base de données
            p.setDescription(rs.getString("desc_prod")); // Utilisez le nom de la colonne de la base de données
            p.setStatut(rs.getString("statut_prod")); 
            p.setImage(rs.getString("image"));
            myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }
public List<Produits> getProduitsList() {
    List<Produits> produitsList = new ArrayList<>();
    String selectData = "SELECT * FROM produits_info"; // Requête SQL pour sélectionner les produits

    try {
        PreparedStatement preparedStatement = cnx2.prepareStatement(selectData);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Produits produit = new Produits();
            produit.setNomDuProduit(resultSet.getString("nom_prod"));
            produit.setPrix(resultSet.getInt("prix_prod"));
            produit.setDescription(resultSet.getString("desc_prod"));
            produit.setStatut(resultSet.getString("statut_prod"));
             produit.setImage(resultSet.getString("image"));
            produitsList.add(produit);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }

    return produitsList;
}
public void updateProduit(Produits oldProduit, Produits updatedProduit) {
    try {
        String updateData = "UPDATE produits_info SET nom_prod = ?, prix_prod = ?, desc_prod = ?, statut_prod = ? , image = ? WHERE nom_prod = ?";
        PreparedStatement preparedStatement = cnx2.prepareStatement(updateData);
        preparedStatement.setString(1, updatedProduit.getNomDuProduit());
        preparedStatement.setInt(2, updatedProduit.getPrix());
        preparedStatement.setString(3, updatedProduit.getDescription());
        preparedStatement.setString(4, updatedProduit.getStatut());
        preparedStatement.setString(5, updatedProduit.getImage());
        preparedStatement.setString(6, oldProduit.getNomDuProduit());
        
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            // Successful update
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Mise à jour effectuée avec succès.");
            alert.showAndWait();
        } else {
            // Update failed
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la mise à jour du produit.");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    
    }
}
public boolean isNomProduitUnique(String nomDuProduit) {
    try {
        String checkData = "SELECT COUNT(*) FROM produits_info WHERE nom_prod = ?";
        PreparedStatement checkStatement = cnx2.prepareStatement(checkData);
        checkStatement.setString(1, nomDuProduit);
        ResultSet result = checkStatement.executeQuery();
        
        if (result.next()) {
            int count = result.getInt(1);
            return count == 0; // Si count est égal à zéro, le nom est unique
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    
    return false; // En cas d'erreur, considérez le nom comme non unique
}
public void deleteProduit(Produits produit) {
    try {
        String deleteData = "DELETE FROM produits_info WHERE nom_prod = ?";
        PreparedStatement preparedStatement = cnx2.prepareStatement(deleteData);
        preparedStatement.setString(1, produit.getNomDuProduit());

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            // Suppression réussie
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Produit supprimé avec succès.");
            alert.showAndWait();
        } else {
            // Échec de la suppression
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR Message");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la suppression du produit.");
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
public void updateProduits(Produits produits) {
    
    String updateQuery = "UPDATE produits_info SET statut_prod = ? WHERE id = ?";
    
    try (PreparedStatement preparedStatement = cnx2.prepareStatement(updateQuery)) {
        preparedStatement.setString(1, produits.getStatut());
        preparedStatement.setInt(2, produits.getId());
        
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected == 1) {
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Failed to update product.");
        }
    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    }
}

    public int getProductIdFromDatabase(int selectedProductId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
            connection =  cnx2;

            // Define your SQL query to retrieve the product ID based on the selected product's ID
            String query = "SELECT id FROM produits_info WHERE id = ?";

            // Create a PreparedStatement with the query
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedProductId); // Set the selected product's ID as a parameter

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the product ID from the result set
                int productId = resultSet.getInt("id");
                return productId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1; // Return -1 if no product was found or an error occurred
    }
public void insertNomProduit(String nomProduit, int prodId) {
    try {
        String query = "INSERT INTO idprod_info (nomprodR, prodid_1) VALUES (?, ?)";
        PreparedStatement preparedStatement = cnx2.prepareStatement(query);
        preparedStatement.setString(1, nomProduit);
        preparedStatement.setInt(2, prodId);
        preparedStatement.executeUpdate();

        System.out.println("Nom de produit inséré avec succès dans la base de données.");
    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'insertion du nom de produit : " + ex.getMessage());
    }
}
  public void updateProductsStatusBasedOnLastInsertedNomProdR() {
        Connection connection = cnx2;
        String selectLastNomProdRQuery = "SELECT nomprodR FROM idprod_info ORDER BY id DESC LIMIT 1";
        String updateStatutProdQuery = "UPDATE produits_info SET statut_prod = ? WHERE nom_prod = ?";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectLastNomProdRQuery);

            if (resultSet.next()) {
                String lastNomProdR = resultSet.getString("nomprodR");
                if (lastNomProdR != null) {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateStatutProdQuery);
                    preparedStatement.setString(1, "not_available");
                    preparedStatement.setString(2, lastNomProdR);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  public void insertNomProdAdmin(String nomProduit, String typeChangement) {
    try {
        String query = "INSERT INTO prodadmin (prodname, typechangement) VALUES (?, ?)";
        PreparedStatement preparedStatement = cnx2.prepareStatement(query);
        preparedStatement.setString(1, nomProduit);
        preparedStatement.setString(2, typeChangement);
        preparedStatement.executeUpdate();

        System.out.println("Nom de produit et type de changement enregistrés avec succès dans la table prodadmin.");
    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'insertion du nom de produit et du type de changement dans la table prodadmin : " + ex.getMessage());
    }
}
  
  }

