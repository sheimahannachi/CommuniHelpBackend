/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class commentaire {
    private int id_comm ;
    private String contenuCommentaire ;
   private int id ; 
   private User  id_mededcin;

    public int getId_comm() {
        return id_comm;
    }

    public void setId_comm(int id_comm) {
        this.id_comm = id_comm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getId_mededcin() {
        return id_mededcin;
    }

    public void setId_mededcin(User id_mededcin) {
        this.id_mededcin = id_mededcin;
    }

    
    

 

    public String getContenuCommentaire() {
        return contenuCommentaire;
    }

    public void setContenuCommentaire(String contenuCommentaire) {
        this.contenuCommentaire = contenuCommentaire;
    }

    public commentaire(int id_comm, String contenuCommentaire, int id) {
        this.id_comm = id_comm;
        this.contenuCommentaire = contenuCommentaire;
        this.id = id;
    }

  

    public commentaire( String contenuCommentaire) {
        
        this.contenuCommentaire = contenuCommentaire;
       
    }

    public commentaire() {
    }

    public commentaire(int id_comm) {
        this.id_comm = id_comm;
    }

    @Override
    public String toString() {
        return "commentaire{" + "id_comm=" + id_comm + ", contenuCommentaire=" + contenuCommentaire + '}';
    }

    

    
    

    
    
    
    
}
