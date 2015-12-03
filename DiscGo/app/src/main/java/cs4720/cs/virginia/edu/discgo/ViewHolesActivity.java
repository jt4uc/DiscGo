package cs4720.cs.virginia.edu.discgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ViewHolesActivity extends AppCompatActivity {

    ArrayList<String> courseHoles;
    String courseName;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_holes);
        courseName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        courseHoles = new ArrayList<String>();
        setTitle(courseName);
        makeList();
    }


    public void makeList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(courseName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> holes, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < holes.size(); i++) {
                        courseHoles.add(holes.get(i).getString("holeName"));

                    }
                    if (courseHoles != null) {
                        final ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getApplicationContext(), R.layout.list_style, courseHoles);
                        final ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(itemsAdapter);
                    }
                }
            }
        });
    }

                    public void onBackPressed () {

                        Intent intent = new Intent(ViewHolesActivity.this, CreateCourseActivity.class);
                        intent.putExtra("courseName", courseName);
                        intent.putExtra("numberOfHoles", courseHoles.size() + "");
                        ViewHolesActivity.this.startActivity(intent);
                    }
                }
