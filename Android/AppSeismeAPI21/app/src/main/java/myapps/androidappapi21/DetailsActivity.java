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
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titre_d = (TextView) findViewById(R.id.title_display);
        TextView place_d = (TextView) findViewById(R.id.place_display);
        TextView magnitude_d = (TextView) findViewById(R.id.magnitude_display);
        TextView date_d = (TextView) findViewById(R.id.date_display);
        TextView time_d = (TextView) findViewById(R.id.time_display);
        Button btn_url = (Button) findViewById(R.id.buttonLienUCGS);
        Button btn_view_solo = (Button) findViewById(R.id.buttonViewOnMap);

        Intent intentRecu = getIntent();
        final Seisme seismeRecu;
        seismeRecu = (Seisme) intentRecu.getSerializableExtra("SeismeInList");
        final String url;
        url = seismeRecu.getUrlDetailUCGS();
        long time = seismeRecu.getTime();
        String date = getDate(time, "dd/MM/yyyy hh:mm:ss.SSS");



        if(seismeRecu != null){
            titre_d.setText(seismeRecu.getTitle());
            place_d.setText(seismeRecu.getPlace());
            magnitude_d.setText(seismeRecu.getMagnitude());
            date_d.setText(date.substring(0,10));
            time_d.setText(date.substring(11,19));
        }

        btn_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_view_solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MapsActivity.class);
                intent.putExtra("Seisme", seismeRecu);
                startActivity(intent);
            }
        });




    }
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}