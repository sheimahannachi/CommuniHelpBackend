
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import edu.esprit.entities.enumMedecin.SpecialiteMedicale;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

 
public class User {
    
    private int id;
    private String email ; 
    private String nom ; 
    private String prenom ; 
    private String password ; 
    private String specialite; 
    private String adresse ; 
    private int num_tel ; 
    private String roles ;
    private String Status ; 
    private String activation_token;
    private SpecialiteMedicale  specialite_med;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    

    
    public User(String email, String nom, String password, String adresse, int num_tel, String roles, String Status) {
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
        this.Status = Status;
     
    }

    public User(String email, String nom, String prenom, String password, String specialite, String adresse, int num_tel, String roles, String Status) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = hashMotDePasse(password);
        this.specialite = specialite;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
        this.Status = Status;
    }

    public User(int id, String email, String nom, String prenom, String adresse, int num_tel, String roles) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
    }

    
    public User(String email, String nom, String prenom, String password,String roles, String activation_token,String Status,String adresse,int Num_tel) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password =hashMotDePasse(password) ;
        this.roles=roles;
        this.activation_token=generateCode(); 
        this.Status = Status;
        this.adresse=adresse;
        this.num_tel=Num_tel;
    }
       public static User Current_User; 

    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
       
    public User(int userId, String email, String nom, String userprenom, String userPassword, String role, String status) {
         this.id=userId;
         this.email=email;
         this.nom=nom;
         this.prenom=userprenom;
         this.password=hashMotDePasse(userPassword);
         this.roles=role; 
         this.Status=status;
         
                 
         
         
         
    }

    public User(String email, String nom, String prenom, String password, String roles, String status, String adresse,int num_tel) {
        this.email=email; 
        this.nom=nom;
        this.prenom=prenom;
        this.password=password;
        this.roles=roles;
        this.Status=status;
        this.adresse=adresse;
        this.num_tel=num_tel;
       
    }

    
    public String getActivation_token() {
        return activation_token;
    }

    public void setActivation_token(String activation_token) {
        this.activation_token = activation_token;
    }

   

    
    public User(int id, String email, String nom, String prenom, String specialite, String adresse, int num_tel, String roles, String Status) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
        this.Status = Status;
    }

    
    public User(int id, String email, String nom, String prenom, String specialite, String adresse, int num_tel, String roles) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
    }

    
    public User(String email, String nom, String prenom, String password, String specialite, String adresse, int num_tel, String roles, String Status, String activation_token) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = hashMotDePasse(password);
        this.specialite = specialite;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
        this.Status = Status;
        this.activation_token = generateCode();
    }

    public User(String email, String nom, String password, String adresse, int num_tel, String roles, String Status, String activation_token) {
        this.email = email;
        this.nom = nom;
        this.password = hashMotDePasse(password);
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.roles = roles;
        this.Status = Status;
        this.activation_token = generateCode();
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

   
    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String edresse) {
        this.adresse = edresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    

   

public String generateCode() {

        Random random = new Random();
        int codeInt = random.nextInt(1000000);
        String activation_token = String.format("%06d", codeInt);

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(activation_token);
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(alphabet.length());
            sb.insert(random.nextInt(sb.length() + 1), alphabet.charAt(index));
        }
        activation_token = sb.toString();

        return activation_token;
}
         public String hashMotDePasse(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    
    }

    public SpecialiteMedicale getSpecialite_med() {
        return specialite_med;
    }

    public void setSpecialite_med(SpecialiteMedicale specialite_med) {
        this.specialite_med = specialite_med;
    }
         
         
   
}
  
    
    
    
