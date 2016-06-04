package myapps.androidappapi21;

import java.io.Serializable;

/**
 * Created by Guillaume on 02/06/2016.
 */
public class Seisme implements Serializable {
    public Seisme(){

    }

    public Seisme(String title, String place, long time, String urlDetailUCGS,
                  String magnitude, double latitude, double longitude){
        this.title=title;
        this.place=place;
        this.time=time;
        this.urlDetailUCGS=urlDetailUCGS;
        this.magnitude=magnitude;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getTitle(){
        return this.title;
    }
    public String getPlace(){
        return this.place;
    }
    public String getUrlDetailUCGS(){
        return this.urlDetailUCGS;
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
        this.urlDetailUCGS=urlDetailUCGS;
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

    public String toString(){
        return ""+longitude;
    }

    private String title;
    private String place;
    private String urlDetailUCGS;
    private long time;
    private String magnitude;
    private double latitude;
    private double longitude;
}