/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import edu.esprit.entities.enumMedecin.SpecialiteMedicale;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;


/**
 *
 * @author ASUS
 */
public class publications {
    
    private int id ;
    private String texte;
    private LocalDate dateDePublication ;
    private String specialite ;
    private String tags ;
    private String imagePath;
   private BigInteger nbJaime;
   private User id_med;

    public publications(int i, String fffffff, LocalDate MIN, String specialite, String vvvvv, String string) {
       this.id=id;
        this.texte = texte;
        this.dateDePublication = dateDePublication;
        this.specialite = specialite;
        this.tags = tags;
        this.imagePath = imagePath;
        this.nbJaime = BigInteger.ZERO;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  

       public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

  

    public LocalDate getDateDePublication() {
        return dateDePublication;
    }

    public void setDateDePublication(LocalDate dateDePublication) {
        this.dateDePublication = dateDePublication;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

   
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigInteger getNbJaime() {
        return nbJaime;
    }

    public void setNbJaime(BigInteger nbJaime) {
        this.nbJaime = nbJaime;
    }

    public User getId_med() {
        return id_med;
    }

    public void setId_med(User id_med) {
        this.id_med = id_med;
    }

    
    
    

    public publications( String texte, LocalDate dateDePublication, String specialite, String tags, String imagePath,BigInteger nbJaime) {
        
        this.texte = texte;
        this.dateDePublication = dateDePublication;
        this.specialite = specialite;
        this.tags = tags;
        this.imagePath = imagePath;
        this.nbJaime=nbJaime;
    }

    public publications(String texte, LocalDate dateDePublication, String specialite, String tags, String imagePath,  User id_med) {
        this.texte = texte;
        this.dateDePublication = dateDePublication;
        this.specialite = specialite;
        this.tags = tags;
        this.imagePath = imagePath;
        
        this.id_med = id_med;
    }

    public publications(String texte, LocalDate dateDePublication, String specialite, String tags, String imagePath, BigInteger nbJaime, User id_med) {
        this.texte = texte;
        this.dateDePublication = dateDePublication;
        this.specialite = specialite;
        this.tags = tags;
        this.imagePath = imagePath;
        this.nbJaime = nbJaime;
        this.id_med = id_med;
    }
    
    
    
    
    

    public publications() {
    }

    public publications( String texte, LocalDate dateDePublication, String specialite, String tags, String imagePath) {
        
        this.texte = texte;
        this.dateDePublication = dateDePublication;
        this.specialite = specialite;
        this.tags = tags;
        this.imagePath=imagePath;
    }

    
    
    

 
   



    public publications(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "publications{" + "id=" + id + ", texte=" + texte + ", dateDePublication=" + dateDePublication + ", specialite=" + specialite + ", tags=" + tags + ", imagePath=" + imagePath + ", nbJaime=" + nbJaime + ", id_med=" + id_med + '}';
    }

   

  
    

 
    
    

    
    
    
}
