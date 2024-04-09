/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

/**
 *
 * @author USER
 */
public class ListeCData {
    private String nomProduit;
    private String contact;
    private String nomDest;
    private String emailC;
    private String adresseC;

    public ListeCData(String nomProduit, String contact, String nomDest, String emailC, String adresseC) {
        this.nomProduit = nomProduit;
        this.contact = contact;
        this.nomDest = nomDest;
        this.emailC = emailC;
        this.adresseC = adresseC;
    }

    // Getters et setters

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNomDest() {
        return nomDest;
    }

    public void setNomDest(String nomDest) {
        this.nomDest = nomDest;
    }

    public String getEmailC() {
        return emailC;
    }

    public void setEmailC(String emailC) {
        this.emailC = emailC;
    }

    public String getAdresseC() {
        return adresseC;
    }

    public void setAdresseC(String adresseC) {
        this.adresseC = adresseC;
    }

    @Override
    public String toString() {
        return "ListeCData{" + "nomProduit=" + nomProduit + ", contact=" + contact + ", nomDest=" + nomDest + ", emailC=" + emailC + ", adresseC=" + adresseC + '}';
    }
    
}
