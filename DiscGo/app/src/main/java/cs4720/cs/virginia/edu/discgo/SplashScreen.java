package cs4720.cs.virginia.edu.discgo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.ArrayList;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    public void play(View v) {
        Intent intent = new Intent(SplashScreen.this, NamesActivity.class);
        SplashScreen.this.startActivity(intent);
    }

    public void createHole(View v) {
        Intent intent = new Intent(SplashScreen.this, CreateMapsActivity.class);
        SplashScreen.this.startActivity(intent);
    }
    public void createCourse(View v) {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
        builder.setMessage("Name your course");
        final EditText editText = new EditText(getApplicationContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        editText.setTextColor(Color.BLACK); // so the cursor is white... lolol let's not fix it now ;)
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                String courseName = String.valueOf(editText.getText());

                Intent intent = new Intent(SplashScreen.this, CreateCourseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, courseName);
                ParseObject course = new ParseObject("Courses");
                course.put("courseName", courseName);
                intent.setType("text/plain");

                SplashScreen.this.startActivity(intent);

            }
        });*/
       // ParseObject course = new ParseObject("Courses");
        //course.put("courseName", "test");
        Intent intent = new Intent(SplashScreen.this, CreateCourseActivity.class);
        intent.putExtra("numberOfHoles", "-1");
        SplashScreen.this.startActivity(intent);

    }
}



