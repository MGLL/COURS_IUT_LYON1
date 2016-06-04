/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectfich;

/**
 *
 * @author Guillaume
 */

//Class qui n'est pas nécessaire mais qui après réflexion a été jugé utile pour le traitement
//des diverses informations.
public class Etudiant {
    
    //Constructeur de l'objet étudiant avec ses trois paramètres.
    public Etudiant(String nomPrenom, double note1, double note2){
        this.nomPrenom=nomPrenom;
        this.note1=note1;
        this.note2=note2;
    }
    
    //Getters and Setters
    public String getNomPrenom(){return nomPrenom;}
    public double getNote1(){return note1;}
    public double getNote2(){return note2;}
    public double getMoyenne(){return moyenne;}
    
    public void setNomPrenom(String nomPrenom){this.nomPrenom = nomPrenom;}
    public void setNote1(double note1){this.note1 = note1;}
    public void setNote2(double note2){this.note2=note2;}
    public void setMoyenne(double moyenne){this.moyenne=moyenne;}
    
    private String nomPrenom;
    private double note1;
    private double note2;
    private double moyenne;
}
