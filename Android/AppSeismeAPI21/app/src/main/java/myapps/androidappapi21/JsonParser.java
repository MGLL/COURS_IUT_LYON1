package myapps.androidappapi21;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/06/2016.
 */

/* La class JsonParser permet de récupérer le stringBuilder  contenant toutes les informations du flux Json
qui est envoyé depuis la MainActivity. On récupère le String pour le découper en fonction des informations que l'on
souhaite récupérer (title, place, magnitude, url, time et coordonnées).

On stock chaque donnée dans un objet Séisme spécifique que l'on stock ensuite dans une ArrayList (contenant tous les séismes)
et qui est renvoyé à la MainActivity. */

public class JsonParser{
    public JsonParser(){
        //Constructeur vide.
    }

    //Fonction pour découper le Json (sous forme de String) afin d'extraire des informations.
    public ArrayList<Seisme> parseJson(String sBuilder){

        // Bloque try pour éviter que toutes données null plante l'application.
        try{
            //ArrayList pour stocker les séismes.
            listeSeisme = new ArrayList<Seisme>();

            JSONObject jsonObject = new JSONObject(sBuilder);
            JSONArray array = jsonObject.getJSONArray("features");

            for(int i = 0; i<array.length(); i++) {

                JSONObject jSonObj = array.getJSONObject(i);
                // properties, pour récupérer les données : title, place, time, url, magnitude.
                JSONObject properties = jSonObj.getJSONObject("properties");
                // geometry, pour récupérer longitude et latitude.
                JSONObject geometry = jSonObj.getJSONObject("geometry");

                // Stock dans l'objet séisme.
                Seisme seisme = new Seisme(properties.optString("title"),properties.optString("place"),Long.parseLong(properties.optString("time")),
                        properties.optString("url"),properties.optString("mag"),
                        geometry.optJSONArray("coordinates").getDouble(1),geometry.optJSONArray("coordinates").getDouble(0));

                // Stock le séisme dans l'ArrayList.
                listeSeisme.add(seisme);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // On renvoie à MainActivity.
        return listeSeisme;
    }

    java.util.ArrayList<Seisme> listeSeisme;
}
