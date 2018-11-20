package pt.ipbeja.aula5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import pt.ipbeja.aula5.data.ChatDatabase;
import pt.ipbeja.aula5.data.Contact;
import pt.ipbeja.aula5.data.Coordinates;

public class CreateContactActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText contactNameInput;


    private Marker contactMarker = null;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateContactActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        contactNameInput = findViewById(R.id.contact_name_input);
    }

    public void createContact(View view) {

        String name = contactNameInput.getText().toString();
        if(!name.isEmpty() && contactMarker != null) {
            Coordinates coordinates = new Coordinates(contactMarker.getPosition().latitude, contactMarker.getPosition().longitude);
            ChatDatabase.getInstance(this).contactDao().insert(new Contact(0, name, coordinates));
            finish();
        }
        else {
            if(name.isEmpty()) Snackbar.make(findViewById(android.R.id.content), R.string.create_contact_empty_name_alert, Toast.LENGTH_SHORT).show();
            else Snackbar.make(findViewById(android.R.id.content), R.string.create_contact_no_location_alert, Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLngBounds bounds = new LatLngBounds(new LatLng(36.568494, -10.448224),new LatLng(42.572301, -5.578860));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(contactMarker != null) {
                    contactMarker.remove();
                }

                contactMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

    }
}
