package myapps.androidappapi21;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/* La MainActivity envoie le flux Json sous forme de String à JsonParser pour extraire les données et récupérer une ArrayList
d'objet. Elle gère aussi l'affichage dans la listView par l'intermédiaire de "listToView" et de l'adaptateur (CustomAdapter).
Elle donne accès aux préférences, à la google map général (tous les séismes) et au test de connexion. */


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //On récupère les éléments du Layout.
        mListView = (ListView) findViewById(R.id.listView);
        Button btnGoogle = (Button) findViewById(R.id.buttonGoogle);
        Button btnTestConnection = (Button) findViewById(R.id.buttonTestConnection);
        Button buttonPref = (Button) findViewById(R.id.buttonPreferences);
        TextView textConnexion = (TextView) findViewById(R.id.textViewConnexion);

        // On initialise listeSeisme et jSonCut pour récupérer les informations du Json.
        listeSeisme = new ArrayList<>();
        jSonCut = new JsonParser();

        // listeToView sera la liste affichée dans l'application (listView).
        listeToView = new ArrayList<>();

        //On récupère l'intent du SplashScreen avec le String contenant le flux Json.
        Intent intentReçu = getIntent();

        //Gestion des préférences gardées en mémoires.
        sharePref = getSharedPreferences(PreferencesActivity.PREFS_NAME, Context.MODE_PRIVATE);

        // Test pour savoir si le String reçu est null ou vide.
        // C'est à dire, si l'application a été lancée sans connexion ou qu'une erreur est
        // survenue (évite le crash de l'application).
        if(intentReçu.getStringExtra("StringBuilder")!=null){

            //On récupère le String.
            jsonToParse = intentReçu.getStringExtra("StringBuilder");

            //Si jamais c'est vite, la connexion n'a pas eu lieu.
            if(jsonToParse.equals("vide")){
                textConnexion.setText("Pas de connexion");
            }else{

                //Sinon on récupère les informations.
                listeSeisme = jSonCut.parseJson(jsonToParse);

                if(sharePref.getString("limiteMag","0")!=null){
                    //Selon la préférence de magnitude minimal de l'utilisateur, on récupère les séismes concernés.
                    if(!(sharePref.getString("limiteMag","0").equals(""))){
                        limiteMangitude = Double.parseDouble(sharePref.getString("limiteMag","0"));
                        for(Seisme s: listeSeisme){
                            magnitudeToTest = Double.parseDouble(s.getMagnitude());
                            if(magnitudeToTest>=limiteMangitude){
                                listeToView.add(s);
                            }
                        }

                        // On prépare l'adapter spécifique.
                        adapter = new CustomAdapter(getApplicationContext(), listeToView);
                    }else{
                        adapter = new CustomAdapter(getApplicationContext(), listeSeisme);
                    }
                }

                // On applique l'adapter pour la listView.
                mListView.setAdapter(adapter);

                // On met un listener pour la gestion de la sélection utilisateur sur les séismes.
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Seisme selection = listeToView.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        intent.putExtra("SeismeInList", selection);
                        startActivity(intent);
                    }
                });

            }

            // Gestion de la google map selon la connexion de l'utilisateur et de la liste des séismes.
            if(jsonToParse.equals("vide")){
                btnGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Aucune connexion", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                btnGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("listeSeisme", listeSeisme);
                        startActivity(intent);
                    }
                });
            }
        }

        // Gestion du test de connexion.
        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestConnection();
            }
        });

        // Gestion de la sélection de préférence.
        buttonPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPref = new Intent (MainActivity.this, PreferencesActivity.class);
                startActivity(intentPref);
                finish();
            }
        });

    }

    // Fonction pour tester la connexion de l'utilisateur.
    public void TestConnection() {
        ConnectivityManager connectivity =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        TextView textConnexion = (TextView) findViewById(R.id.textViewConnexion);
        //On récupère l'état de la connexion.
        NetworkInfo netWork = connectivity.getActiveNetworkInfo();
        connect = netWork != null && netWork.isConnectedOrConnecting();
        assert netWork != null;


        // On affiche le résultat selon le type de connexion ou si aucune connexion.
        if (connect) {
            wifi = netWork.getType() == ConnectivityManager.TYPE_WIFI;
            if (wifi) {
                Toast.makeText(this, "Vous êtes connecté en Wifi", Toast.LENGTH_LONG).show();
                textConnexion.setText("Connexion WiFi");
            } else {
                Toast.makeText(this, "Vous êtes connecté en 3G", Toast.LENGTH_LONG).show();
                textConnexion.setText("Connexion 3G");
            }
            if(listeSeisme.size()==0){
                Intent intentRestart = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intentRestart);
                finish();
            }
        } else {
            if(listeSeisme.size()!=0){
                // Si l'utilisateur se déconnecte et qu'il lance le test de connexion on rafraîchis la liste (si elle
                // n'est pas vide).
                Toast.makeText(this, "Connexion perdu", Toast.LENGTH_LONG).show();
                Intent intentRestart = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intentRestart);
                finish();
            }else{
                textConnexion.setText("Aucune connexion");
            }
        }

    }

    // Déclaration de variable.

    private ListView mListView;
    private JsonParser jSonCut;
    private CustomAdapter adapter;
    private String jsonToParse;
    private double magnitudeToTest;
    private double limiteMangitude;
    private boolean wifi;
    private boolean connect;
    SharedPreferences sharePref;
    private java.util.ArrayList<Seisme> listeSeisme;
    private java.util.ArrayList<Seisme> listeToView;
}
