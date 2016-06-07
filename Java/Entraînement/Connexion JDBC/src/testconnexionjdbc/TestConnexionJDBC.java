/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testconnexionjdbc;

import java.sql.*;

/**
 *
 * @author Guillaume
 */
public class TestConnexionJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Bien penser à importer ojdbc7.jar.
        // Clic droit sur le projet, properties, Libraries, ADD Jar/Folder et sélectionner ojdbc7.jar.

        try{
            java.sql.DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            java.sql.Connection connexion;
            
            //On se connecte avec le lien, l'identifiant et le mot de passe.
            connexion = java.sql.DriverManager.getConnection(URL,USER_ID,PASSWORD);
            //Si la connexion n'a pas échouée on l'indique.
            if(connexion!=null)System.out.println("Connexion établie");
            
            //On prépare une requête et le stockage pour le résultat.
            java.sql.Statement requete;
            requete = connexion.createStatement();
            java.sql.ResultSet resultat;
            resultat = requete.executeQuery(
            "select numero, nom from table");
            int numero;
            String nom;
            
            System.out.println("\tNUM\t\tNOM");
            System.out.println("\t----\t\t---------");
            
            //On affiche les éléments.
            while(resultat.next()){
                numero = resultat.getInt(1);
                nom = resultat.getString(2);
                System.out.println("\t"+numero+"\t\t"+nom);
            }
            
            //On ferme tout avant d'arrêter.
            resultat.close();
            requete.close();
            connexion.close();
        } catch (SQLException e){
            System.out.println("ERREUR : "+e.getMessage());
        }
    }
    static String USER_ID = "_IDENTIFIANT_";
    static String PASSWORD = "_PASSWORD_";
    static String URL = "_URL_CONNEXION_";
}
