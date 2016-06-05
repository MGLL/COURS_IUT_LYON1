package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guillaume on 02/06/2016.
 */
public class JsonParser{
    public JsonParser(){

    }


    public ArrayList<Seisme> parseJson(String sBuilder){
        try{
            listeSeisme = new ArrayList<Seisme>();
            JSONObject jsonObject = new JSONObject(sBuilder);
            JSONArray array = jsonObject.getJSONArray("features");

            for(int i = 0; i<array.length(); i++) {
                JSONObject jSonObj = array.getJSONObject(i);
                JSONObject properties = jSonObj.getJSONObject("properties");
                JSONObject geometry = jSonObj.getJSONObject("geometry");

                Seisme seisme = new Seisme(properties.optString("title"),properties.optString("place"),Long.parseLong(properties.optString("time")),
                        properties.optString("url"),properties.optString("mag"),
                        geometry.optJSONArray("coordinates").getDouble(1),geometry.optJSONArray("coordinates").getDouble(0));

                listeSeisme.add(seisme);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return listeSeisme;
    }

    java.util.ArrayList<Seisme> listeSeisme;
}
