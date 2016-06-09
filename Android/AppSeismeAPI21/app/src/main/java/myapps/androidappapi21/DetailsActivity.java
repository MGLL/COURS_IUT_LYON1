package myapps.androidappapi21;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Guillaume on 02/06/2016.
 */

/* DetailsActivity sert à afficher les informations détailées (Titre, place, magnitude, heure, date) d'un Séisme spécifique.
On a aussi accès à un lien et une google map spécifique (qui pointe uniquement sur le séisme en question).
On accède à ce layout / affichage lorsque l'on clique sur un Séisme de la listView (qui est dans la MainAcivity). */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //On récupère les éléments du layout.
        TextView titre_d = (TextView) findViewById(R.id.title_display);
        TextView place_d = (TextView) findViewById(R.id.place_display);
        TextView magnitude_d = (TextView) findViewById(R.id.magnitude_display);
        TextView date_d = (TextView) findViewById(R.id.date_display);
        TextView time_d = (TextView) findViewById(R.id.time_display);
        Button btn_url = (Button) findViewById(R.id.buttonLienUCGS);
        Button btn_view_solo = (Button) findViewById(R.id.buttonViewOnMap);

        // On récupère l'intent et les informations.
        Intent intentRecu = getIntent();
        // On récupère l'objet Séisme.
        seismeRecu = (Seisme) intentRecu.getSerializableExtra("SeismeInList");
        // On récupère time et l'url.
        url = seismeRecu.getUrlDetailUCGS();
        time = seismeRecu.getTime();
        date = getDate(time, "dd/MM/yyyy hh:mm:ss.SSS");


        // Si le séisme n'est pas vide ou null, on récupère les informations pour les afficher.
        if(seismeRecu != null){
            titre_d.setText(seismeRecu.getTitle());
            place_d.setText(seismeRecu.getPlace());
            magnitude_d.setText(seismeRecu.getMagnitude());
            date_d.setText(date.substring(0,10));
            time_d.setText(date.substring(11,19));
        }

        // Permet d'accéder au lien donné pour plus de détails sur le séisme.
        btn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        // Permet d'afficher le séisme sur la google map.
        btn_view_solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                intent.putExtra("Seisme", seismeRecu);
                startActivity(intent);
            }
        });




    }
    //On formate la date reçu en long (millisecond) pour obtenir une date précise et utilisable.
    private String getDate(long milliSeconds, String dateFormat) {
        // On créer un DateFormatter pour afficher la date dans un format spécifique.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // On créer un calendrier pour convertir le temps millisecond en date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    // Déclaration de variable.

    private Seisme seismeRecu;
    private String url;
    private long time;
    private String date;

}