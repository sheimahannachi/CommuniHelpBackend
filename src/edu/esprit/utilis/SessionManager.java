/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.utilis;

/**
 *
 * 
 */
public class SessionManager {
    
    private static SessionManager instance;
    private static int id;
    private static String email ; 
    private static String nom ; 
    private static String prenom ; 
    private static String password ; 
    private static  String specialite; 
    private static  String adresse ; 
    private static  int num_tel ; 
    private static String status ; 
    private static  String roles ;

    public SessionManager() {
    }
    
    private SessionManager(int id , String nom,String prenom,String email,String password,String adresse,String status,String roles ,int num_tel){
        SessionManager.id=id;
        SessionManager.nom=nom;
        SessionManager.prenom=prenom; 
        SessionManager.email=email;
        SessionManager.password=password;
        SessionManager.adresse=adresse;
        SessionManager.status=status;
        SessionManager.roles=roles;
        SessionManager.num_tel=num_tel; 
                
    }
   public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }
     public static void cleanUserSession() {
    id=0;
    email="" ; 
    nom="" ; 
    prenom="" ; 
    password="" ; 
    specialite=""; 
    adresse="" ; 
    num_tel=0 ; 
     roles=""; 
    }
    @Override
    public String toString() {
        return "SessionManager{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", specialite=" + specialite + ", adresse=" + adresse + ", num_tel=" + num_tel + ", roles=" + roles + '}';
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        SessionManager.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        SessionManager.prenom = prenom;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SessionManager.password = password;
    }

    public static String getSpecialite() {
        return specialite;
    }

    public static void setSpecialite(String specialite) {
        SessionManager.specialite = specialite;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        SessionManager.adresse = adresse;
    }

    public static int getNum_tel() {
        return num_tel;
    }

    public static void setNum_tel(int num_tel) {
        SessionManager.num_tel = num_tel;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String roles) {
        SessionManager.roles = roles;
    }

    
    
}
