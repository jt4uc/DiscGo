package cs4720.cs.virginia.edu.discgo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    public void play(View v) {
        //ArrayList<Hole> holes = MyApplication.getDBHelper().getAllHoles();
//        if(holes.size() == 0) {
//            Toast toast = Toast.makeText(getApplicationContext(), "There aren't any holes! Go create some!", Toast.LENGTH_SHORT);
//            toast.show();
//        } else {
            Intent intent = new Intent(SplashScreen.this, NamesActivity.class);
            SplashScreen.this.startActivity(intent);
       // }
//        MyApplication.getDBHelper().resetScore();
//        MyApplication.getDBHelper().resetNames();


    }

    public void createCourse(View v) {
        Intent intent = new Intent(SplashScreen.this, CreateMapsActivity.class);
        SplashScreen.this.startActivity(intent);
    }
}




