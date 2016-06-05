package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Guillaume on 02/06/2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConnectivityManager connectivity =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //On récupère l'état de la connexion
        NetworkInfo netWork = connectivity.getActiveNetworkInfo();
        final boolean connect = netWork != null && netWork.isConnectedOrConnecting();
        assert netWork != null;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(connect){
                    new catchData().execute();
                }else{
                    Intent intentEnvoie = new Intent(SplashScreen.this,MainActivity.class);
                    stringVide="vide";
                    intentEnvoie.putExtra("StringBuilder", stringVide);
                    startActivity(intentEnvoie);
                    finish();
                }

            }

        }, SPLASH_TIME_OUT);
    }

    private class catchData extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                String lineToRead = null;

                while ((lineToRead = reader.readLine()) != null)
                {
                    stringBuilder.append(lineToRead + "\n");
                }
            }catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return stringBuilder.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intentEnvoie = new Intent(SplashScreen.this,MainActivity.class);
            intentEnvoie.putExtra("StringBuilder", stringBuilder.toString());
            startActivity(intentEnvoie);

            finish();
        }
    }
    static StringBuilder stringBuilder;
    private String stringVide;
    private static int SPLASH_TIME_OUT = 0;
}