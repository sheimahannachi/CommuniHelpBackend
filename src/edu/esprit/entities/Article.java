/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class Article {
    

    private int id;
    private String ville;
    private String description;
    private String contact;
    private LocalDate creationDate;
    private String image;
    
    public Article() {
    }

    public Article(int id, String ville, String description, String contact, LocalDate creationDate, String image) {
        this.id = id;
        this.ville = ville;
        this.description = description;
        this.contact = contact;
        this.creationDate = creationDate;
        this.image = image;
    }

    public Article(String ville, String description, String contact, LocalDate creationDate, String image) {
        this.ville = ville;
        this.description = description;
        this.contact = contact;
        this.creationDate = creationDate;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", ville=" + ville + ", description=" + description + ", contact=" + contact + ", creationDate=" + creationDate + ", image=" + image + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
