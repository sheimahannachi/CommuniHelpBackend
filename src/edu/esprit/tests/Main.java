/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.tests;


import edu.esprit.entities.User;
import edu.esprit.entities.enumMedecin;
import edu.esprit.entities.publications;
import edu.esprit.services.UserCrud;
import edu.esprit.services.publicationsCrud;
import edu.esprit.utilis.MyConnection;
import java.sql.PreparedStatement;
import java.time.LocalDate;


//fxml est un lg de structure de données baseé sur le xml 
public class Main {
    public static void main (String[] args )
    {
        MyConnection mc = MyConnection.getInstance(); 
        MyConnection mc2 = MyConnection.getInstance() ; 
        System.out.println(mc.hashCode()+"-"+mc2.hashCode());
        UserCrud pcd = new UserCrud () ;
        //User u2 = new User("jjj@hm", "hhhh", "hhhh", "aaaa", "eeee", "eeee", 0, "hhhhh", "", "") ; 
        //pcd.ajouterUtilisateur2(u2);
        // pcd.modifierUtilisateur(u2);
         //pcd.supprimerUtilisateur(u2);
        // User u3 = new User(1, "ouma@gmail.com", "ggg", "dhfeh", "test", "tttttt", 123456, "medecin"); 

    publicationsCrud p = new publicationsCrud();
    User medecinUser = p.getMedecinForPublication(1);
    
    if (medecinUser != null) {
        // You have the User object representing the Medecin, so you can use it as needed.
        System.out.println("medecin nom: " + medecinUser.getNom() + " " + medecinUser.getPrenom());
        // ... Other properties
    } else {
        System.out.println("Medecin not found or doesn't have the role 'Medecin'.");
    }
        System.out.println(pcd.afficherUtilisateurs());
        
    }
    
}
