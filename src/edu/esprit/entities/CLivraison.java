/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

/**
 *
 * @author USER
 */
public class CLivraison { 
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private String adresse;
    private String email;

    // Constructeur
    public CLivraison(String nom, String prenom, String numeroTelephone, String adresse, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.adresse = adresse;
        this.email = email;
    }

    public CLivraison() {
    }
    

    // Méthodes pour accéder aux attributs (getters)
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }
    
}
