/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

/**
 *
 * @author USER
 */
public class Produits { // Attributs de la classe Produit

    private String nomDuProduit;
    private int prix;
    private String description; // Changement de l'attribut "Bénéficiaire" à "Description"
    private String statut;
     private int id;
     private String image ;
   public Produits (){
       
}
    // Constructeur de la classe Produit

    public Produits(String nomDuProduit, int prix, String description, String statut, String image) {
        this.nomDuProduit = nomDuProduit;
        this.prix = prix;
        this.description = description;
        this.statut = statut;
        this.image = image;
    }

    public Produits(String nomDuProduit, int prix, String description, String statut, int id, String image) {
        this.nomDuProduit = nomDuProduit;
        this.prix = prix;
        this.description = description;
        this.statut = statut;
        this.id = id;
        this.image = image;
    }

    public Produits(String nomDuProduit, int prix, String description, String statut) {
        this.nomDuProduit = nomDuProduit;
        this.prix = prix;
        this.description = description;
        this.statut = statut;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomDuProduit() {
        return nomDuProduit;
    }

    public void setNomDuProduit(String nomDuProduit) {
        this.nomDuProduit = nomDuProduit;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override

    
    public String toString() {
        return "Produits{" + "nomDuProduit=" + nomDuProduit + ", prix=" + prix + ", description=" + description + ", statut=" + statut + ", id=" + id + ", image=" + image + '}';
    }}
  

