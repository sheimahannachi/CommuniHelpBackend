/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;



/**
 *
 * @author Lenovo
 */
public class history {
private int articleId;
    private String articleVille; 
    private String description;
    private String contact;
private String image; 
    private int donationId;
    private Float montant; 
    private String email; 
    private String nom; 
    private String prenom; 
    private String donationVille; 
    private String carteBancaire; 

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleVille() {
        return articleVille;
    }

    public void setArticleVille(String articleVille) {
        this.articleVille = articleVille;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDonationVille() {
        return donationVille;
    }

    public void setDonationVille(String donationVille) {
        this.donationVille = donationVille;
    }

    public String getCarteBancaire() {
        return carteBancaire;
    }

    public void setCarteBancaire(String carteBancaire) {
        this.carteBancaire = carteBancaire;
    }

    public history(int articleId, String articleVille, String description, String contact, String image, int donationId, Float montant, String email, String nom, String prenom, String donationVille, String carteBancaire) {
        this.articleId = articleId;
        this.articleVille = articleVille;
        this.description = description;
        this.contact = contact;
        this.image = image;
        this.donationId = donationId;
        this.montant = montant;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.donationVille = donationVille;
        this.carteBancaire = carteBancaire;
    }

    @Override
    public String toString() {
        return "history{" + "article_id=" + articleId + ", article_ville=" + articleVille + ", description=" + description + ", contact=" + contact + ", image=" + image + ", donation_id=" + donationId + ", montant=" + montant + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", donation_ville=" + donationVille + ", carte_bancaire=" + carteBancaire + '}';
    }



    public history() {
    }
    
    public String getFormattedMontant() {
        // Format the "montant" with "dt"
        return String.format("%.2f dt", montant);
    }
}
