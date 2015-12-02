package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;

public class NamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Enter Names Of Players");
        setContentView(R.layout.activity_names);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_names, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent cameraIntent = new Intent(NamesActivity.this, SplashScreen.class);
        NamesActivity.this.startActivity(cameraIntent);
    }

    public void saveName(View view) {

        ParseObject game = new ParseObject("Game");
        ArrayList<String> names = new ArrayList<String>();

        EditText player1Text = (EditText) findViewById(R.id.name1);
        EditText player2Text = (EditText) findViewById(R.id.name2);
        EditText player3Text = (EditText) findViewById(R.id.name3);
        EditText player4Text = (EditText) findViewById(R.id.name4);
        EditText player5Text = (EditText) findViewById(R.id.name5);
        EditText player6Text = (EditText) findViewById(R.id.name6);
//        if(!String.valueOf(player1Text.getText()).equals(""))
//           MyApplication.getDBHelper().saveName(String.valueOf(player1Text.getText()));
        if(!String.valueOf(player1Text.getText()).equals(""))
            names.add(String.valueOf(player1Text.getText()));
        if(!String.valueOf(player2Text.getText()).equals(""))
            names.add(String.valueOf(player2Text.getText()));
        if(!String.valueOf(player3Text.getText()).equals(""))
            names.add(String.valueOf(player3Text.getText()));
        if(!String.valueOf(player4Text.getText()).equals(""))
            names.add(String.valueOf(player4Text.getText()));
        if(!String.valueOf(player5Text.getText()).equals(""))
            names.add(String.valueOf(player5Text.getText()));
        if(!String.valueOf(player6Text.getText()).equals(""))
            names.add(String.valueOf(player6Text.getText()));
        //MyApplication.getDBHelper().resetScore();

        game.addAll("names", names);
        //game.saveInBackground(); // performed on a separate thread
        try {
            game.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String objectId = game.getObjectId();
        MyApplication.setGameId(objectId);
        Intent intent = new Intent(NamesActivity.this, PlayMapsActivity.class);
        //intent.putExtra(Intent.EXTRA_TEXT, objectId);
        NamesActivity.this.startActivity(intent);
    }
}
