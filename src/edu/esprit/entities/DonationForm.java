/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import edu.esprit.entities.enumVille.ville;

public class DonationForm {
    private int idDon;
    private Float montant;
    private String email;
    private String nom;
    private String prenom;
    private ville ville;
    private String carteBancaire;

    public DonationForm() {
        // Default constructor
    }

    public DonationForm(int idDon, Float montant, String email, String nom, String prenom, ville ville, String carteBancaire) {
        this.idDon = idDon;
        this.montant = montant;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.carteBancaire = carteBancaire;
    }

    public int getIdDon() {
        return idDon;
    }

    public void setIdDon(int idDon) {
        this.idDon = idDon;
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

    public ville getVille() {
        return ville;
    }

    public void setVille(ville ville) {
        this.ville = ville;
    }

    public String getCarteBancaire() {
        return carteBancaire;
    }

    public void setCarteBancaire(String carteBancaire) {
        this.carteBancaire = carteBancaire;
    }
    @Override
public String toString() {
    return "DonationForm{" +
            "id=" + idDon +
            ", montant=" + montant +
            ", email='" + email + '\'' +
            ", nom='" + nom + '\'' +
            ", prenom='" + prenom + '\'' +
            ", ville=" + ville +
            ", carte_bancaire='" + carteBancaire + '\'' +
            '}';
}
}