/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;
/**
 *
 * @author USER
 */
public class prodadmin1 {
    private String prodname1;
    private String typechangement;

    public prodadmin1(String prodname1, String typechangement) {
        this.prodname1 = prodname1;
        this.typechangement = typechangement;
    }

    public prodadmin1() {
    }

    public String getProdname1() {
        return prodname1;
    }

    public void setProdname1(String prodname1) {
        this.prodname1 = prodname1;
    }

    public String getTypechangement() {
        return typechangement;
    }

    public void setTypechangement(String typechangement) {
        this.typechangement = typechangement;
    }
}