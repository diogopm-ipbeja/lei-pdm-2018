package pt.ipbeja.aula5.data;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pt.ipbeja.aula5.ChatActivity;
import pt.ipbeja.aula5.R;

public class ContactsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static void start(Context context) {
        Intent starter = new Intent(context, ContactsMapActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLngBounds bounds = new LatLngBounds(new LatLng(36.568494, -10.448224),new LatLng(42.572301, -5.578860));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        List<Contact> contacts = ChatDatabase.getInstance(getApplicationContext()).contactDao().getAllContacts();

        for (Contact c : contacts) {
            if(c.getCoordinates().isValid()) {
                LatLng latLng = new LatLng(c.getCoordinates().getLatitude(), c.getCoordinates().getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(c.getName())).setTag(c);
            }
        }



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Contact c = (Contact) marker.getTag();
                ChatActivity.start(ContactsMapActivity.this, c.getId());
            }
        });

    }
}
