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
        ArrayList<String> playerObjectIds = new ArrayList<String>();

        EditText player1Text = (EditText) findViewById(R.id.name1);
        EditText player2Text = (EditText) findViewById(R.id.name2);
        EditText player3Text = (EditText) findViewById(R.id.name3);
        EditText player4Text = (EditText) findViewById(R.id.name4);
        EditText player5Text = (EditText) findViewById(R.id.name5);
        EditText player6Text = (EditText) findViewById(R.id.name6);

        ArrayList<EditText> editTexts = new ArrayList<EditText>();
        editTexts.add(player1Text);
        editTexts.add(player2Text);
        editTexts.add(player3Text);
        editTexts.add(player4Text);
        editTexts.add(player5Text);
        editTexts.add(player6Text);

        for (EditText e : editTexts) {
            String name = String.valueOf(e.getText());
            if (!name.equals("")) {
                ParseObject player = new ParseObject("Player");
                player.put("name", name);
                try {
                    player.save();
                    playerObjectIds.add(player.getObjectId());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }

        game.addAll("players", playerObjectIds);
        MyApplication.setPlayerIds(playerObjectIds);
        try {
            game.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String objectId = game.getObjectId();
        MyApplication.setGameId(objectId);
        Intent intent = new Intent(NamesActivity.this, PlayMapsActivity.class);
        NamesActivity.this.startActivity(intent);
    }
}
