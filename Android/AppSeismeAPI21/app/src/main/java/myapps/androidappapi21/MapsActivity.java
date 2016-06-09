package myapps.androidappapi21;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //On récupère les différentes information de l'intent (liste de Séisme (Main) ou Séisme unique (Détails)).
        intent = getIntent();
        listSeisme = intent.getParcelableArrayListExtra("listeSeisme");
        seismeReçu = (Seisme) intent.getSerializableExtra("Seisme");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Si google map est accédé depuis l'accueil on affiche tous les séismes.
        if(listSeisme!=null){
            //On récupère la liste des séismes.
            ArrayList<Seisme> listeDeSeisme = listSeisme;
            seismeLatLng = null;

            for (Seisme objSeisme : listeDeSeisme){
                //Pour chaque séisme on récupère un titre pour le marqueur et les coordonnées.
                latitude = objSeisme.getLatitude();
                longitude = objSeisme.getLongitude();
                name = objSeisme.getTitle();
                seismeLatLng = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(seismeLatLng).title(name));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(seismeLatLng));

        } else {
            //Sinon, google map est accédé depuis DétailsActivity on affiche que le séisme en question.
            seismeCoordonnée = null;

            //Pour le séisme en question on récupère les mêmes informations que vu précédement.
            latitude = seismeReçu.getLatitude();
            longitude = seismeReçu.getLongitude();
            name = seismeReçu.getTitle();
            seismeCoordonnée = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(seismeCoordonnée).title(name));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(seismeCoordonnée));
        }


    }

    //Déclaration de variables.
    private Seisme seismeReçu;
    private LatLng seismeLatLng;
    private LatLng seismeCoordonnée;
    private double latitude;
    private double longitude;
    private String name;
    private ArrayList listSeisme;

}
