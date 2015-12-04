package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DisplayScoreActivity extends AppCompatActivity {

    int holeNum = -1;
    private ArrayList<TextView> playerTextViews;
    private ArrayList<int[]> scoresList;

    private String courseName;
    private int holeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Total Scores");
        setContentView(R.layout.activity_display_score);

        playerTextViews = new ArrayList<TextView>();
        playerTextViews.add((TextView) findViewById(R.id.score1));
        playerTextViews.add((TextView) findViewById(R.id.score2));
        playerTextViews.add((TextView) findViewById(R.id.score3));
        playerTextViews.add((TextView) findViewById(R.id.score4));
        playerTextViews.add((TextView) findViewById(R.id.score5));
        playerTextViews.add((TextView) findViewById(R.id.score6));

        ArrayList<TextView> nameTextViews = new ArrayList<TextView>();
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel1));
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel2));
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel3));
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel4));
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel5));
        nameTextViews.add((TextView) findViewById(R.id.scoreLabel6));

        ArrayList<String> playerIds = MyApplication.getPlayerIds();
        scoresList = new ArrayList<int[]>();
        for (int i = 0; i < 6; i++) {
            if (i > playerIds.size() - 1) {
                playerTextViews.get(i).setVisibility(View.INVISIBLE);
                nameTextViews.get(i).setVisibility(View.INVISIBLE);
            } else {
                nameTextViews.get(i).setText(queryPlayerName(playerIds.get(i)));
                int[] score = queryScores(playerIds.get(i));
                if (score != null) {
                    scoresList.add(queryScores(playerIds.get(i)));
                } else {
                    scoresList = null;
                    break;
                }
            }
        }
        if (scoresList == null) {
            for (TextView tv : playerTextViews) {
                tv.setText("0");
            }
        } else {
            displayTotals();
        }

        courseName = getIntent().getStringExtra("course");
        if(!courseName.equals( "Hole"))
            holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
    }

    @Override
    public void onBackPressed() {
        if(courseName.equals("Hole")) {
            Intent intent = new Intent(DisplayScoreActivity.this, PlayMapsActivity.class);
            DisplayScoreActivity.this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(DisplayScoreActivity.this, PlayCourseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, courseName);
          //  holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
            intent.putExtra("holeNumber", holeNumber+"");
            DisplayScoreActivity.this.startActivity(intent);
        }

    }

    public void next(View view){

        if (scoresList != null) {
            if (holeNum < scoresList.get(0).length - 1) {
                holeNum += 1;
            }

            if (holeNum <= scoresList.get(0).length - 1) {
                setTitle("Hole " + (holeNum + 1));
                for(int i = 0; i < scoresList.size(); i++) {
                    playerTextViews.get(i).setText(Integer.toString(scoresList.get(i)[holeNum]));
                }
            }
        }
    }

    public void previous(View view){

        if (scoresList != null) {
            if (holeNum > -1) {
                holeNum -= 1;
            }
            if (holeNum > -1) {
                setTitle("Hole " + (holeNum + 1));
                for(int i = 0; i < scoresList.size(); i++) {
                    playerTextViews.get(i).setText(Integer.toString(scoresList.get(i)[holeNum]));
                }
            } else {
                setTitle("Total Scores");
                displayTotals();
            }
        }
    }

    public void displayTotals() {
        for(int i = 0; i < scoresList.size(); i++) {
            int[] scores = scoresList.get(i);
            int sum = 0;
            for(int score : scores) {
                sum += score;
            }
            playerTextViews.get(i).setText(Integer.toString(sum));
        }
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

    public int[] queryScores(String playerId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        try {
            ParseObject player = query.get(playerId);
            List scoresList = player.getList("scores");
            if (scoresList != null) {
                int[] scores = new int[scoresList.size()];
                for (int i = 0; i < scoresList.size(); i ++) {
                    scores[i] = (int) scoresList.get(i);
                }
                return scores;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void done(View v ){
        if(courseName.equals("Hole")) {
            Intent intent = new Intent(DisplayScoreActivity.this, PlayMapsActivity.class);
            DisplayScoreActivity.this.startActivity(intent);
        }
        else{
            Intent intent = new Intent(DisplayScoreActivity.this, PlayCourseActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, courseName);
            //  holeNumber = Integer.parseInt(getIntent().getStringExtra("holeNumber"));
            intent.putExtra("holeNumber", holeNumber+"");
            DisplayScoreActivity.this.startActivity(intent);
        }

    }
}
