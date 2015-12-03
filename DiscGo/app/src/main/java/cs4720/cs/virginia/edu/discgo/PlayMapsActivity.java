package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PlayMapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmaps);
        setUpMapIfNeeded();
        loadMarkers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        loadMarkers();
    }

    @Override
    public void onBackPressed() {
        Intent cameraIntent = new Intent(PlayMapsActivity.this, SplashScreen.class);
        PlayMapsActivity.this.startActivity(cameraIntent);
    }

    public void showScores(View v) {
        Intent intent = new Intent(PlayMapsActivity.this, DisplayScoreActivity.class);
        intent.putExtra("course", "Hole");
        PlayMapsActivity.this.startActivity(intent);
    }

    public void showCourses(View v){
        Intent intent = new Intent(PlayMapsActivity.this, ChooseCourseActivity.class);
        PlayMapsActivity.this.startActivity(intent);
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.playMap))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true); // Enable the MyLocation button at the top right of the screen
        mMap.setOnMarkerClickListener(markerClickListener);
    }

    public void loadMarkers() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Hole");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> holes, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < holes.size(); i++) {
                        ParseGeoPoint point = holes.get(i).getParseGeoPoint("location");
                        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    }
                }
            }
        });
    }

    /**
     * When the user clicks on a marker
     */
    private GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(final Marker marker) {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Hole");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> holes, ParseException e) {
                    if (e == null) {
                        for(int i = 0; i < holes.size(); i++) {
                            ParseGeoPoint point = holes.get(i).getParseGeoPoint("location");
                            if (point.getLatitude() == marker.getPosition().latitude && point.getLongitude() == marker.getPosition().longitude) {
                                String objectId = holes.get(i).getObjectId();
                                Intent intent = new Intent(PlayMapsActivity.this, PlayHoleActivity.class);
                                intent.putExtra(Intent.EXTRA_TEXT, objectId);
                                intent.putExtra("course", "Hole");
                                PlayMapsActivity.this.startActivity(intent);
                                break;
                            }
                        }
                    }
                }
            });

            return false; // does the default behavior - shows info window and centers marker on map
        }
    };
}
