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
public class PreferencesActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        sharePref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

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
    }


    private void annulerAction(){
        Intent intentPref = new Intent(PreferencesActivity.this, SplashScreen.class);
        startActivity(intentPref);
        finish();
    }

    private void validerAction(){
        SharedPreferences.Editor sPrefEdit = sharePref.edit();

        if(!(editMangitude.getText().toString().equals(""))){
            limiteMangitude = editMangitude.getText().toString();
        }

        sPrefEdit.putString("limiteMag",limiteMangitude);


        sPrefEdit.commit();
        Intent intentPref = new Intent(PreferencesActivity.this, SplashScreen.class);
        startActivity(intentPref);
        finish();
    }

    private String limiteMangitude;
    private EditText editMangitude;
    SharedPreferences sharePref;
    public static final String PREFS_NAME = "preferences";

}
