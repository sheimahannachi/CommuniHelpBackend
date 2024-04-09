/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.Medecin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.entities.enumMedecin;
import edu.esprit.entities.publications;
import edu.esprit.utilis.MyConnection;


/**
 *
 * @author ASUS
 */
public class medecinCrud {
    MyConnection myc =MyConnection.getInstance();
        Connection cnx  = myc.getCnx();
        
        
        public void ajouterMedecin(Medecin m){
        String requete1 ="INSERT INTO medecin(nom,prenom,adresse,telephone,email, mdp,specialité)"
                +"VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete1);
           
             pst.setString(1, m.getNom());
               pst.setString(2, m.getPrenom());
                pst.setString(3, m.getAdresse());
                pst.setInt(4,m.getTelephone());
                 pst.setString(5, m.getEmail());
                  pst.setString(6, m.getMdp());
                 pst.setString(7, m.getSpecialité().name());
               
               pst.executeUpdate();
               System.out.println("Votre medecin est ajouté");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
            
        }}

        public void supprimerMedecin(Medecin  m){
        String requete2 ="DELETE FROM medecin WHERE id=?";
               
        try {
            PreparedStatement pst = cnx.prepareStatement(requete2);
               pst.setInt(1, m.getId());
               pst.executeUpdate();
               System.out.println("Votre medecin est supprimé");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }}
public void modifierMedecin(Medecin m ,int id){
        String requete3 ="UPDATE medecin SET nom=?,prenom=?,adresse=?,telephone=?,email=?, mdp=?,specialité=? WHERE id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete3);
            
          
             pst.setString(1, m.getNom());
               pst.setString(2, m.getPrenom());
                pst.setString(3, m.getAdresse());
                pst.setInt(4,m.getTelephone());
                 pst.setString(5, m.getEmail());
                  pst.setString(6, m.getMdp());
                 pst.setString(7, m.getSpecialité().name());
              
               pst.setInt(8, id);
               pst.executeUpdate();
               System.out.println("Votre medecin est modifié");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }}

         public List<Medecin>afficherMedecins(){
         List<Medecin> myList=new ArrayList<>();
        try {
           
            String requete4="SELECT * FROM medecin";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete4);
            while(rs.next()){
            Medecin m  = new Medecin ();
            
          
             m.setNom(rs.getString("Nom"));
            m.setPrenom(rs.getString("Prenom"));
            m.setAdresse(rs.getString("Adresse"));
            m.setEmail(rs.getString("email"));
            m.setMdp(rs.getNString("mdp"));
            String specialiteString = rs.getString("specialité");
            m.setSpecialité(enumMedecin.SpecialiteMedicale.valueOf(specialiteString));
            
            myList.add(m);  
        }
              
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            
        }
        return myList;  
    }
    








}




