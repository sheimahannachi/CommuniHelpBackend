/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.time.LocalDate;

/**
 *
 * @author MSI
 */
public class Evennement {
    private int id;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    private String nom;
    private String lieu;
    private LocalDate date;

    public Evennement(int id, String nom, String lieu, LocalDate date, int nombreParticipants, String path) {
        this.id = id;
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.nombreParticipants = nombreParticipants;
        this.path = path;
    }
    private int nombreParticipants;
    private String path;

    public int getNombreParticipants() {
        return nombreParticipants;
    }

    public void setNombreParticipants(int nombreParticipants) {
        this.nombreParticipants = nombreParticipants;
    }

    public String getNom() {
        return nom;
    }
     public int getid() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
     public void setid(int id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Evennement() {
    }

    public Evennement(int id,String nom, String lieu, LocalDate date) {
        this.id =id;
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Evennement{" + "nom=" + nom + ", lieu=" + lieu + ", date=" + date + '}';
    }
    
    
}
