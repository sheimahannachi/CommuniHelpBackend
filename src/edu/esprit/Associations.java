/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package edu.esprit;


import edu.esprit.entities.Article;
import edu.esprit.entities.DonationForm;
import edu.esprit.services.articleCRUD;
import edu.esprit.services.donationCRUD;
import edu.esprit.utilis.MyConnection;
import edu.esprit.entities.enumVille;
import edu.esprit.entities.enumVille.ville;
import static edu.esprit.entities.enumVille.ville.TUNIS;

import java.util.Date;

    public class Associations {

    public static void main(String[] args) {
        ville tunis = TUNIS;
        // Create a new instance of MyConnection
        MyConnection mc = MyConnection.getInstance();

        // Create an instance of AssociationCRUD
        articleCRUD associationCRUD = new articleCRUD();

    // Article a = new Article(4, "sina", "sheima", "4444", new Date(), "");
     //  associationCRUD.ajouterArticle(a);
        // Display the list of Associations
      //  System.out.println("List of Associations:");
      //  associationCRUD.getArticles().forEach(System.out::println);
        
        // Modify an Association (assuming an existing Association with the specified ID)
        Article modifiedArticle = new Article();
        modifiedArticle.setId(2); // Replace with the actual ID of the Association to modify
        modifiedArticle.setVille("Modified Ville");
        modifiedArticle.setDescription("Modified Description");
        modifiedArticle.setContact("9876543210");

        associationCRUD.modifierArticle(modifiedArticle);

        // Display the updated list of Associations
      //  System.out.println("Updated List of Associations:");
     //   associationCRUD.getArticles().forEach(System.out::println);

        // Delete an Association by ID (assuming an existing Association with the specified ID)
        int idToDelete = 4; // Replace with the actual ID of the Association to delete
       associationCRUD.supprimerArticle(idToDelete);

        // Display the list of Associations after deletion
     //   System.out.println("List of Associations after Deletion:");
             donationCRUD DonationCRUD = new donationCRUD();
//DonationForm df = new DonationForm(7, 50.0f, "email", "ouma", "mess", TUNIS, "589");        
       // DonationCRUD.ajouterdonationForm(df);
        donationCRUD donationCRUD = new donationCRUD(); // Create an instance of donationCRUD
        // Display the list of DonationForms
       // System.out.println("List of DonationForms:");
       // donationCRUD.getDonationForms().forEach(System.out::println);
        DonationForm modifiedDonationForm = new DonationForm();
         modifiedDonationForm.setIdDon(1); // Replace with the actual ID of the DonationForm to modify
        modifiedDonationForm.setMontant(75.0f); // Modify the montant
                modifiedDonationForm.setEmail("new maaaaaail");

        modifiedDonationForm.setNom("Modified Nom");
        modifiedDonationForm.setPrenom("Modified Prenom");

        // Assuming 'ville' is an enum, set it accordingly
        modifiedDonationForm.setVille(enumVille.ville.BIZERTE); // Replace with the actual Ville

        modifiedDonationForm.setCarteBancaire("Modified Carte Bancaire");

        donationCRUD.modifierDonationForm(modifiedDonationForm);

        // Display the updated list of DonationForms
        System.out.println("Updated List of DonationForms:");
         int idDonToDelete = 7; // Replace with the actual ID of the DonationForm to delete
        donationCRUD.supprimerDonationForm(idDonToDelete);

        // Display the list of DonationForms after deletion
        System.out.println("List of DonationForms after Deletion:");
    }
    }

