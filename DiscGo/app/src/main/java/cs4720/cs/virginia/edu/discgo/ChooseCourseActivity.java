package cs4720.cs.virginia.edu.discgo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ChooseCourseActivity extends AppCompatActivity {

    String[] courseHoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);
setTitle("Choose a Course");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Courses");
       query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> holes, ParseException e) {

                if (e == null) {
                    courseHoles = new String[holes.size()];
                    for (int i = 0; i < holes.size(); i++) {
                        courseHoles[i] = holes.get(i).getString("courseName");


                    }
                    if(courseHoles != null) {
                        final ArrayAdapter<String> holesAdapter =
                                new ArrayAdapter<String>(getApplicationContext(),R.layout.list_style, courseHoles);
                        ListView listView = (ListView) findViewById(R.id.listViewChoose);
                        listView.setAdapter(holesAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override



                            public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {

                                String chosenCourse = holesAdapter.getItem(position);

                                  Intent intent = new Intent(ChooseCourseActivity.this, PlayCourseActivity.class);
                              // Toast.makeText(getApplicationContext(), "chosenHole", Toast.LENGTH_SHORT).show();
                                intent.putExtra(Intent.EXTRA_TEXT, chosenCourse);
                                intent.putExtra("holeNumber", "1");
                                  startActivity(intent);

                            }

                        });
                    }
                }
            }
        });

    }
   /* public void courseChosen(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
    }
*/


}