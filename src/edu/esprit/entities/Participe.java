/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;
/**
 *
 * @author MSI
 */
public class Participe {
    int id_p;

    public Participe() {
    }
    String nom;

    public Participe(int id_p, String nom, String num, String mail, int eventId) {
        this.id_p = id_p;
        this.nom = nom;
        this.num = num;
        this.mail = mail;
        this.eventId = eventId;
    }
    String num;

     public Participe(String nom, String num, String mail) {
        this.nom = nom;
        this.num = num;
        this.mail = mail;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    String mail;
    int eventId;
            

}