package myapps.androidappapi21;

import java.io.Serializable;

/**
 * Created by Guillaume on 02/06/2016.
 */

/* Cette class permet de stocker les informations extraites du flux Json dans un objet "Séisme". On stock : title, place,
time, urlDetailsUSGS, magnitude, latitude et longitude.
Cette objet sera ensuite stocké dans une ArrayList dans MainActivity*/

public class Seisme implements Serializable {

    public Seisme(){
        // Constructeur vide.
    }

    // Constructeur.
    public Seisme(String title, String place, long time, String urlDetailUSGS,
                  String magnitude, double latitude, double longitude){
        this.title=title;
        this.place=place;
        this.time=time;
        this.urlDetailUSGS=urlDetailUSGS;
        this.magnitude=magnitude;
        this.latitude=latitude;
        this.longitude=longitude;
    }

      /*                    */
     /* SETTERS & GETTERS  */
    /*                    */

    public String getTitle(){
        return this.title;
    }
    public String getPlace(){
        return this.place;
    }
    public String getUrlDetailUCGS(){
        return this.urlDetailUSGS;
    }
    public long getTime(){
        return this.time;
    }
    public String getMagnitude(){
        return this.magnitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setPlace(String place){
        this.place=place;
    }
    public void setUrlDétailUCGS(String urlDetailUCGS){
        this.urlDetailUSGS=urlDetailUCGS;
    }
    public void setTime(long time){this.time=time;}
    public void setMagnitude(String magnitude){
        this.magnitude=magnitude;
    }
    public void setLatitude(double latitude){
        this.latitude=latitude;
    }
    public void setLongitude(double longitude){
        this.longitude=longitude;
    }

    /* Déclaration de variable */

    private String title;
    private String place;
    private String urlDetailUSGS;
    private long time;
    private String magnitude;
    private double latitude;
    private double longitude;
}