package cs4720.cs.virginia.edu.discgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class PlayCourseActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
private String courseName;
    private int holeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_course);
        courseName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
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
        Intent cameraIntent = new Intent(PlayCourseActivity.this, SplashScreen.class);
        PlayCourseActivity.this.startActivity(cameraIntent);
    }

    public void showScores(View v) {
        Intent intent = new Intent(PlayCourseActivity.this, DisplayScoreActivity.class);
        intent.putExtra("course", courseName);
        intent.putExtra("holeNumber", holeNumber + "");
        PlayCourseActivity.this.startActivity(intent);
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

   public void showCourseFinishedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayCourseActivity.this);
        builder.setMessage("You've completed the course!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent intent = new Intent(PlayCourseActivity.this, DisplayScoreActivity.class);
                intent.putExtra("course", "Hole");

                PlayCourseActivity.this.startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void loadMarkers() {
        //ArrayList<Hole> holes = MyApplication.getDBHelper().getAllHoles();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(courseName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> holes, ParseException e) {
                if (e == null) {
                    if (holeNumber > holes.size()) {
                        showCourseFinishedDialog();
                    }
                    for (int i = 0; i < holes.size(); i++) {
                        if (holes.get(i).getInt("order") == holeNumber) {
                            ParseGeoPoint point = holes.get(i).getParseGeoPoint("location");
                            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        }
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
//            ArrayList<Hole> holes = MyApplication.getDBHelper().getAllHoles();
//            String holeName = "";
//            int holeId = -1;
//            for(int i = 0; i < holes.size(); i++) {
//                if (holes.get(i).getLatitude() == marker.getPosition().latitude && holes.get(i).getLongitude() == marker.getPosition().longitude) {
//                    holeName = holes.get(i).getName();
//                    holeId = holes.get(i).getId();
//                    break;
//                }
//            }

            ParseQuery<ParseObject> query = ParseQuery.getQuery(courseName);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> holes, ParseException e) {
                    if (e == null) {
                        for(int i = 0; i < holes.size(); i++) {
                            ParseGeoPoint point = holes.get(i).getParseGeoPoint("location");
                            if (point.getLatitude() == marker.getPosition().latitude && point.getLongitude() == marker.getPosition().longitude) {
                                String objectId = holes.get(i).getObjectId();
                                Intent intent = new Intent(PlayCourseActivity.this, PlayHoleActivity.class);
                                intent.putExtra(Intent.EXTRA_TEXT, objectId);
                                intent.putExtra("course", courseName);
                                intent.putExtra("holeNumber", holeNumber+"");
                                PlayCourseActivity.this.startActivity(intent);
                                break;
                            }
                        }
                    }
                }
            });



//            Intent intent = new Intent(PlayMapsActivity.this, PlayHoleActivity.class);
//            intent.putExtra(Intent.EXTRA_TEXT, "blah");
            //intent.putExtra(Intent.EXTRA_TEXT, holeName);
            //intent.putExtra("ID", "" + holeId);
            //PlayMapsActivity.this.startActivity(intent);

            return false; // does the default behavior - shows info window and centers marker on map
        }
    };
}
