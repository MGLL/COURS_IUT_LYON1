package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Guillaume on 05/06/2016.
 */

/* Cette class permet de gérer les préférences saisies par l'utilisateur dans la PreferencesActivity.
Les préférences sont enregistrées, si l'utilisateur quitte l'application (kill) et revient, les préférences seront toujours
les mêmes. */

public class PreferencesActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //On prépare la gestion / stockage des préférences.
        sharePref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        //On récupère les éléments du layout.
        Button btnAnnuler = (Button) findViewById(R.id.buttonAnnulerPref);
        Button btnValider = (Button) findViewById(R.id.buttonValiderPref);
        editMangitude = (EditText) findViewById(R.id.saisieMagnitude);

        // On récupère les radioButton du layout.
        radio1 = (RadioButton) findViewById(R.id.radioButtonRecent);
        radio2 = (RadioButton) findViewById(R.id.radioButtonJournee);
        radio3 = (RadioButton) findViewById(R.id.radioButtonSemaine);

        // On regarde des les préférences enregistrées si des valeurs existent.
        // Si oui on coche le bouton et on donne la valeur correspondant à celle actuellement stockée.
        if(sharePref.getString("urlAffiche","").equals("Récent")){
            urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_hour.geojson";
            urlToAffiche = "Récent";
            radio1.setChecked(true);
        }else if (sharePref.getString("urlAffiche","").equals("Journée")){
            urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
            urlToAffiche = "Journée";
            radio2.setChecked(true);
        }else if (sharePref.getString("urlAffiche","").equals("Semaine")){
            urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson";
            urlToAffiche = "Semaine";
            radio3.setChecked(true);
        } else {
            urlToAffiche = "Aucun";
        }


        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annulerAction();
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerAction();
            }
        });

        //On affiche dans un Toast les préférences actuelles de l'utilisateur.
        messageToToast = "Vos préférences : \nMangitude min : "+sharePref.getString("limiteMag","0")+"\nChargement : "+sharePref.getString("urlAffiche","");
        Toast.makeText(PreferencesActivity.this, messageToToast, Toast.LENGTH_LONG).show();
    }

    // Si l'utilisateur à coché un des trois choix, on donne les données correspondantes.s
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.radioButtonRecent :
                if (checked){
                    urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_hour.geojson";
                    urlToAffiche = "Récent";
                }
                break;
            case R.id.radioButtonJournee :
                if (checked){
                    urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
                    urlToAffiche = "Journée";
                }
                break;
            case R.id.radioButtonSemaine :
                if (checked){
                    urlToCharge = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.geojson";
                    urlToAffiche = "Semaine";
                }
                break;
        }
    }

    private void annulerAction(){
        //Gestion de l'annulation.
        Intent intentPref = new Intent(PreferencesActivity.this, SplashScreen.class);
        startActivity(intentPref);
        finish();
    }

    private void validerAction(){
        //Si validation
        SharedPreferences.Editor sPrefEdit = sharePref.edit();

        // Si (seulement si) l'utilisateur à changé de préférence.
        // On récupère la préférence de magnitude
        // Et on la stock.
        if(!(editMangitude.getText().toString().equals(""))){
            limiteMangitude = editMangitude.getText().toString();
            sPrefEdit.putString("limiteMag",limiteMangitude);
        }

        // Si (seulement si) l'utilisateur à changé de préférence, on stock les nouvelles.
        if(radio1.isChecked() || radio2.isChecked() || radio3.isChecked()){
            //On récupère aussi le lien choisis.
            sPrefEdit.putString("urlToCharge",urlToCharge);
            sPrefEdit.putString("urlAffiche",urlToAffiche);
        }

        sPrefEdit.commit();

        //Puis on met à jour l'application avec les préférences.
        Intent intentPref = new Intent(PreferencesActivity.this, SplashScreen.class);
        startActivity(intentPref);
        finish();
    }

    private String limiteMangitude;
    private EditText editMangitude;
    private String messageToToast;
    private String urlToCharge;
    private String urlToAffiche;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private SharedPreferences sharePref;
    static String PREFS_NAME = "preferences";

}
