package cs4720.cs.virginia.edu.discgo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
    }

    public void play(View v) {

    }

    public void createCourse(View v) {
        Intent intent = new Intent(SplashScreen.this, MapsActivity.class);
        SplashScreen.this.startActivity(intent);
    }
}




