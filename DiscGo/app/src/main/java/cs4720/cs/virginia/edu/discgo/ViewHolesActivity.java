package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ViewHolesActivity extends AppCompatActivity {

    String[] courseHoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_holes);
        String courseName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        setTitle(courseName);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(courseName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> holes, ParseException e) {

                if (e == null) {
                    courseHoles = new String[holes.size()];
                    for (int i = 0; i < holes.size(); i++) {
                        courseHoles[i] = holes.get(i).getString("holeName");

                    }
                    if(courseHoles != null) {
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getApplicationContext(), R.layout.list_style, courseHoles);
                        ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setAdapter(itemsAdapter);
                    }
                }
            }
        });

        }

}
