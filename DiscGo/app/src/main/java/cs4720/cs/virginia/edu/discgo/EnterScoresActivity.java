package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EnterScoresActivity extends AppCompatActivity {

    private ArrayList<String> players;
    private String courseName;
    private int holeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_scores);
        courseName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (!courseName.equals("Hole"))
            holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
        setTitle("Enter Scores");
        final ArrayList<String> names = new ArrayList<String>();
        players = MyApplication.getPlayerIds();

        for (String p : players) {
            names.add(queryPlayerName(p));
        }
        changeTexts(names);
    }

    public void save(View v) {
        int s1;
        int s2;
        int s3;
        int s4;
        int s5;
        int s6;

        EditText player1Text = (EditText) findViewById(R.id.enter1);
        EditText player2Text = (EditText) findViewById(R.id.enter2);
        EditText player3Text = (EditText) findViewById(R.id.enter3);
        EditText player4Text = (EditText) findViewById(R.id.enter4);
        EditText player5Text = (EditText) findViewById(R.id.enter5);
        EditText player6Text = (EditText) findViewById(R.id.enter6);
        if (String.valueOf(player1Text.getText()).equals(""))
            s1 = -1;
        else
            s1 = Integer.parseInt(String.valueOf(player1Text.getText()));
        if (String.valueOf(player2Text.getText()).equals(""))
            s2 = -1;
        else
            s2 = Integer.parseInt(String.valueOf(player2Text.getText()));
        if (String.valueOf(player3Text.getText()).equals(""))
            s3 = -1;
        else
            s3 = Integer.parseInt(String.valueOf(player3Text.getText()));
        if (String.valueOf(player4Text.getText()).equals(""))
            s4 = -1;
        else
            s4 = Integer.parseInt(String.valueOf(player4Text.getText()));
        if (String.valueOf(player5Text.getText()).equals(""))
            s5 = -1;
        else
            s5 = Integer.parseInt(String.valueOf(player5Text.getText()));
        if (String.valueOf(player6Text.getText()).equals(""))
            s6 = -1;
        else
            s6 = Integer.parseInt(String.valueOf(player6Text.getText()));

        int[] scores = new int[]{s1, s2, s3, s4, s5, s6};
        for (int i = 0; i < players.size(); i++) {
            addScores(players.get(i), scores[i]);
        }

        if (courseName.equals("Hole")) {
            Intent intent = new Intent(EnterScoresActivity.this, PlayMapsActivity.class);
            EnterScoresActivity.this.startActivity(intent);
        } else {
            Intent intent = new Intent(EnterScoresActivity.this, PlayCourseActivity.class);
            holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
            holeNumber++;
            intent.putExtra("holeNumber", holeNumber + "");
            intent.putExtra(Intent.EXTRA_TEXT, courseName);
            EnterScoresActivity.this.startActivity(intent);
        }

    }

    public void changeTexts(ArrayList<String> names) {
        TextView player1 = (TextView) findViewById(R.id.nameLabel1);
        TextView player2 = (TextView) findViewById(R.id.nameLabel2);
        EditText player2Text = (EditText) findViewById(R.id.enter2);
        TextView player3 = (TextView) findViewById(R.id.nameLabel3);
        EditText player3Text = (EditText) findViewById(R.id.enter3);
        TextView player4 = (TextView) findViewById(R.id.nameLabel4);
        EditText player4Text = (EditText) findViewById(R.id.enter4);
        TextView player5 = (TextView) findViewById(R.id.nameLabel5);
        EditText player5Text = (EditText) findViewById(R.id.enter5);
        TextView player6 = (TextView) findViewById(R.id.nameLabel6);
        EditText player6Text = (EditText) findViewById(R.id.enter6);
        player1.setText(names.get(0));
        if (names.size() < 2) {
            player2.setVisibility(View.INVISIBLE);
            player2Text.setVisibility(View.INVISIBLE);
        } else
            player2.setText(names.get(1));
        if (names.size() < 3) {
            player3.setVisibility(View.INVISIBLE);
            player3Text.setVisibility(View.INVISIBLE);
        } else
            player3.setText(names.get(2));
        if (names.size() < 4) {
            player4.setVisibility(View.INVISIBLE);
            player4Text.setVisibility(View.INVISIBLE);
        } else
            player4.setText(names.get(3));
        if (names.size() < 5) {
            player5.setVisibility(View.INVISIBLE);
            player5Text.setVisibility(View.INVISIBLE);
        } else
            player5.setText(names.get(4));
        if (names.size() < 6) {
            player6.setVisibility(View.INVISIBLE);
            player6Text.setVisibility(View.INVISIBLE);
        } else
            player6.setText(names.get(5));
    }

    public String queryPlayerName(String playerId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        try {
            ParseObject player = query.get(playerId);
            return (String) player.get("name");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addScores(String playerId, int score) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        try {
            ParseObject player = query.get(playerId);
            player.add("scores", score);
            player.saveInBackground();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
