package myapps.tp3;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String monPrenom;
    private String monNom;
    private String monAdresse;
    private String monCode;
    private String monEmail;
    private String monTelephone;
    private RadioGroup monRadioG;
    private RadioButton monRadioB;
    private String monSexe;

    private String toto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button OK_boubou = (Button) findViewById(R.id.ok_button);
        if (OK_boubou != null) {
            OK_boubou.setOnClickListener(montreMoi);
        }


    }


    private View.OnClickListener montreMoi = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            EditText monEdit_P = (EditText) findViewById(R.id.prenom);
            EditText monEdit_N = (EditText) findViewById(R.id.nom);
            EditText monEdit_A = (EditText) findViewById(R.id.adress);
            EditText monEdit_C = (EditText) findViewById(R.id.codePostale);
            EditText monEdit_Em = (EditText) findViewById(R.id.mail);
            EditText monEdit_Ph = (EditText) findViewById(R.id.telephone);
            monRadioG = (RadioGroup) findViewById((R.id.RadioB));



            monNom = monEdit_N.getText().toString();
            monPrenom = monEdit_P.getText().toString();
            monAdresse = monEdit_A.getText().toString();
            monCode = monEdit_C.getText().toString();
            monEmail = monEdit_Em.getText().toString();
            monTelephone = monEdit_Ph.getText().toString();
            monRadioB = (RadioButton) findViewById(monRadioG.getCheckedRadioButtonId());
            monSexe = monRadioB.getText().toString();

            Toast.makeText( MainActivity.this,"Nom : "+monNom+" Prenom : "+monPrenom+"\n Sexe : "+monSexe+
                    "\n Adresse : "+monAdresse+" "+monCode+"\n Email : "+monEmail+"\n Telephone : "+monTelephone,Toast.LENGTH_LONG).show();
        }
    };

}
