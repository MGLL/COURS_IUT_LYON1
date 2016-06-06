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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mListView = (ListView) findViewById(R.id.listView);
        Button btnGoogle = (Button) findViewById(R.id.buttonGoogle);
        Button btnTestConnection = (Button) findViewById(R.id.buttonTestConnection);
        Button buttonPref = (Button) findViewById(R.id.buttonPreferences);
        TextView textConnexion = (TextView) findViewById(R.id.textViewConnexion);
        listeSeisme = new ArrayList<>();
        jSonCut = new JsonParser();

        listeToView = new ArrayList<>();



        Intent intentReçu = getIntent();

        sharePref = getSharedPreferences(PreferencesActivity.PREFS_NAME, Context.MODE_PRIVATE);

        if(intentReçu.getStringExtra("StringBuilder")!=null){
            String jsonToParse = intentReçu.getStringExtra("StringBuilder");

            if(jsonToParse.equals("vide")){
                textConnexion.setText("Pas de connexion");
            }else{
                listeSeisme = jSonCut.parseJson(jsonToParse);

                if(sharePref.getString("limiteMag","0")!=null){
                    if(!(sharePref.getString("limiteMag","0").equals(""))){
                        limiteMangitude = Double.parseDouble(sharePref.getString("limiteMag","0"));
                        for(Seisme s: listeSeisme){
                            double magnitudeToTest = Double.parseDouble(s.getMagnitude());
                            if(magnitudeToTest>=limiteMangitude){
                                System.out.println("magnitudeToTest : "+magnitudeToTest);
                                System.out.println("limiteMagnitude : "+limiteMangitude);
                                listeToView.add(s);
                            }
                        }
                        adapter = new CustomAdapter(getApplicationContext(), listeToView);
                    }else{
                        adapter = new CustomAdapter(getApplicationContext(), listeSeisme);
                    }
                }

                for(Seisme t : listeToView){
                    System.out.println("Voici le titre de t : "+t.getTitle()+
                            "Voici la magnitude de t : "+t.getMagnitude());
                }

                String messageToToast = "Vos préférences : \nMangitude min : "+sharePref.getString("limiteMag","0");
                Toast.makeText(MainActivity.this, messageToToast, Toast.LENGTH_LONG).show();

                mListView.setAdapter(adapter);

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

        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestConnection();
            }
        });

        buttonPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPref = new Intent (MainActivity.this, PreferencesActivity.class);
                startActivity(intentPref);
                finish();
            }
        });


        TestConnection();

    }

    public void TestConnection() {
        ConnectivityManager connectivity =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        TextView textConnexion = (TextView) findViewById(R.id.textViewConnexion);
        //On récupère l'état de la connexion
        NetworkInfo netWork = connectivity.getActiveNetworkInfo();
        boolean connect = netWork != null && netWork.isConnectedOrConnecting();
        assert netWork != null;


        //Affiche le résultat
        if (connect) {
            boolean wifi = netWork.getType() == ConnectivityManager.TYPE_WIFI;
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
                Toast.makeText(this, "Connexion perdu", Toast.LENGTH_LONG).show();
                Intent intentRestart = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intentRestart);
                finish();
            }else{
                Toast.makeText(this, "Vous n'êtes pas connecté", Toast.LENGTH_LONG).show();
                textConnexion.setText("Aucune connexion");
            }
        }

    }

    private ListView mListView;
    private JsonParser jSonCut;
    private CustomAdapter adapter;
    private double limiteMangitude;
    SharedPreferences sharePref;
    private java.util.ArrayList<Seisme> listeSeisme;
    private java.util.ArrayList<Seisme> listeToView;
}
