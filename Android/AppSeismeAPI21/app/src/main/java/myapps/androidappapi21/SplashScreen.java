package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

/* Cette class / Activity s'affiche à l'ouverture de l'application. Elle test la connexion, si l'utilisateur est connecté on récupère
le flux Json dans un String avec BufferedReader par l'intermédiaire de l'AsyncTask. Ensuite on lance la MainActivity avec la liste des Séismes.
Si l'utilisateur n'est pas connecté on lance directement la MainActivity avec une liste vide.
Le splash screen permet aussi de rafraîchir les informations selon les préférences de l'utilisateur.
*/

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // On récupère les informations correspondant au lien à charger dans les préférences.
        // Si c'est la première fois que l'utilisateur lance (donc url = "") on prend la journée par défaut.
        // Sinon on récupère les préférences.
        sharePref = getSharedPreferences(PreferencesActivity.PREFS_NAME, Context.MODE_PRIVATE);
        if (sharePref.getString("urlToCharge","").equals("")){
            urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
        } else {
            urlToCharge = sharePref.getString("urlToCharge","");
        }


        ConnectivityManager connectivity =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //On récupère l'état de la connexion pour la tester.
        NetworkInfo netWork = connectivity.getActiveNetworkInfo();
        connect = netWork != null && netWork.isConnectedOrConnecting();
        assert netWork != null;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Si on est connecté, on exécute l'AsyncTask pour récupérer le flux Json.
                if(connect){
                    new catchData().execute();
                }else{
                    //Sinon on lance la MainActivity avec une liste "vide".
                    Intent intentEnvoie = new Intent(SplashScreen.this,MainActivity.class);
                    stringVide="vide";
                    intentEnvoie.putExtra("StringBuilder", stringVide);
                    startActivity(intentEnvoie);
                    finish();
                }

            }

        }, SPLASH_TIME_OUT);
    }

    // Cette fonction permet de se connecter et de récupérer le flux Json contenant les informations des séismes.
    private class catchData extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            reader = null;

            try {
                //On utilise l'URL des séismes récents.
                System.out.println(urlToCharge);
                url = new URL(urlToCharge);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //On se connecte.
                urlConnection.connect();

                //On prépare la lecture.
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                stringBuilder = new StringBuilder();
                lineToRead = null;

                while ((lineToRead = reader.readLine()) != null)
                {
                    //On récupère les informations pour chaque ligne.
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
            //Après l'exécution du doInBackground, on lance la MainActivity envoyant le String contenant toutes les informations.

            Intent intentEnvoie = new Intent(SplashScreen.this,MainActivity.class);
            intentEnvoie.putExtra("StringBuilder", stringBuilder.toString());
            startActivity(intentEnvoie);
            finish();
        }
    }

    /* Déclaration de variable */

    private StringBuilder stringBuilder;
    private String stringVide;
    private String lineToRead;
    private boolean connect;
    private URL url;
    private BufferedReader reader;
    private String urlToCharge;
    SharedPreferences sharePref;
    private int SPLASH_TIME_OUT = 0;
}