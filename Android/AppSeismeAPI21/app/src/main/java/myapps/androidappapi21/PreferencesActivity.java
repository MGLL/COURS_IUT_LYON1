package myapps.androidappapi21;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        messageToToast = "Vos préférences : \nMangitude min : "+sharePref.getString("limiteMag","0");
        Toast.makeText(PreferencesActivity.this, messageToToast, Toast.LENGTH_LONG).show();
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

        //On récupère les préférences (magnitude) si elle n'est pas null.
        if(!(editMangitude.getText().toString().equals(""))){
            limiteMangitude = editMangitude.getText().toString();
        }

        //Et on la stock.
        sPrefEdit.putString("limiteMag",limiteMangitude);

        sPrefEdit.commit();

        //Puis on met à jour l'application avec les préférences.
        Intent intentPref = new Intent(PreferencesActivity.this, SplashScreen.class);
        startActivity(intentPref);
        finish();
    }

    private String limiteMangitude;
    private EditText editMangitude;
    private String messageToToast;
    private SharedPreferences sharePref;
    static String PREFS_NAME = "preferences";

}
