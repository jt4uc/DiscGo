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

    int firstPointer = 0;
    int hole = 1;

    int holeNum = -1;
    private ArrayList<TextView> playerTextViews;
    private ArrayList<int[]> scoresList;

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

//        ArrayList<Integer> scores = MyApplication.getDBHelper().getAllScores();
//        ArrayList<String> names = MyApplication.getDBHelper().getAllNames();
//        firstPointer = 0;

        ArrayList<String> playerIds = MyApplication.getPlayerIds();
        scoresList = new ArrayList<int[]>();
        for (int i = 0; i < 6; i++) {
            if (i > playerIds.size() - 1) {
                playerTextViews.get(i).setVisibility(View.INVISIBLE);
                nameTextViews.get(i).setVisibility(View.INVISIBLE);
            } else {
                nameTextViews.get(i).setText(queryPlayerName(playerIds.get(i)));
                scoresList.add(queryScores(playerIds.get(i)));
            }
        }

        displayTotals();

//        name1.setText(names.get(0));
//        if(MyApplication.getDBHelper().getNumberOfPlayers() < 2) {
//            player2.setVisibility(View.INVISIBLE);
//            name2.setVisibility(View.INVISIBLE);
//        }else
//        name2.setText(names.get(1));
//        if(MyApplication.getDBHelper().getNumberOfPlayers() < 3) {
//            player3.setVisibility(View.INVISIBLE);
//            name3.setVisibility(View.INVISIBLE);
//        }else
//            name3.setText(names.get(2));
//        if(MyApplication.getDBHelper().getNumberOfPlayers() < 4) {
//            player4.setVisibility(View.INVISIBLE);
//            name4.setVisibility(View.INVISIBLE);
//        }else
//            name4.setText(names.get(3));
//        if(MyApplication.getDBHelper().getNumberOfPlayers() < 5) {
//            player5.setVisibility(View.INVISIBLE);
//            name5.setVisibility(View.INVISIBLE);
//        }else
//            name5.setText(names.get(4));
//        if(MyApplication.getDBHelper().getNumberOfPlayers() < 6) {
//            player6.setVisibility(View.INVISIBLE);
//            name6.setVisibility(View.INVISIBLE);
//        }else
//            name6.setText(names.get(5));

       // int numOfPlayers =
//        for(int i = 0; i < scores.size(); i+=6){
//            totalPlayer1 += scores.get(i);
//            totalPlayer2 += scores.get(i+1);
//            totalPlayer3 += scores.get(i+2);
//            totalPlayer4 += scores.get(i+3);
//            totalPlayer5 += scores.get(i+4);
//            totalPlayer6 += scores.get(i+5);
//        }
//        player1.setText(Integer.toString(totalPlayer1));
//        player2.setText(Integer.toString(totalPlayer2));
//        player3.setText(Integer.toString(totalPlayer3));
//        player4.setText(Integer.toString(totalPlayer4));
//        player5.setText(Integer.toString(totalPlayer5));
//        player6.setText(Integer.toString(totalPlayer6));
    }

    @Override
    public void onBackPressed() {
        Intent cameraIntent = new Intent(DisplayScoreActivity.this, PlayMapsActivity.class);
        DisplayScoreActivity.this.startActivity(cameraIntent);
    }

    public void next(View view){

        if (holeNum < scoresList.get(0).length - 1) {
            holeNum += 1;
        }

        if (holeNum <= scoresList.get(0).length - 1) {
            setTitle("Hole " + (holeNum + 1));
            for(int i = 0; i < scoresList.size(); i++) {
                playerTextViews.get(i).setText(Integer.toString(scoresList.get(i)[holeNum]));
            }
        }


//        int numOfP = 6;
//        TextView player1 = (TextView) findViewById(R.id.score1);
//        TextView player2 = (TextView) findViewById(R.id.score2);
//        TextView player3 = (TextView) findViewById(R.id.score3);
//        TextView player4 = (TextView) findViewById(R.id.score4);
//        TextView player5 = (TextView) findViewById(R.id.score5);
//        TextView player6 = (TextView) findViewById(R.id.score6);
//        ArrayList<Integer> scores = MyApplication.getDBHelper().getAllScores();
//        if(scores.size()-1 > firstPointer) {
//
//            player1.setText(Integer.toString(scores.get(firstPointer)));
//            player2.setText(Integer.toString(scores.get(firstPointer + 1)));
//            player3.setText(Integer.toString(scores.get(firstPointer + 2)));
//            player4.setText(Integer.toString(scores.get(firstPointer + 3)));
//            player5.setText(Integer.toString(scores.get(firstPointer + 4)));
//            player6.setText(Integer.toString(scores.get(firstPointer + 5)));
//            if(scores.size()-1 > firstPointer+numOfP) {
//                firstPointer += numOfP;
//                hole++;
//            }
//        }
    }

    public void previous(View view){

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

//        int numOfP =  6;
//        TextView player1 = (TextView) findViewById(R.id.score1);
//        TextView player2 = (TextView) findViewById(R.id.score2);
//        TextView player3 = (TextView) findViewById(R.id.score3);
//        TextView player4 = (TextView) findViewById(R.id.score4);
//        TextView player5 = (TextView) findViewById(R.id.score5);
//        TextView player6 = (TextView) findViewById(R.id.score6);

//        ArrayList<Integer> scores = MyApplication.getDBHelper().getAllScores();
//        if(0 < firstPointer) {
//            firstPointer -= numOfP;
//            hole--;
//            setTitle("Hole " + Integer.toString(hole));
//            player1.setText(Integer.toString(scores.get(firstPointer)));
//            player2.setText(Integer.toString(scores.get(firstPointer + 1)));
//            player3.setText(Integer.toString(scores.get(firstPointer + 2)));
//            player4.setText(Integer.toString(scores.get(firstPointer + 3)));
//            player5.setText(Integer.toString(scores.get(firstPointer + 4)));
//            player6.setText(Integer.toString(scores.get(firstPointer + 5)));
//
//
//        } else if(firstPointer == 0){
//            hole = 1;
//            setTitle("Total Scores");
//            player1.setText(Integer.toString(totalPlayer1));
//            player2.setText(Integer.toString(totalPlayer2));
//            player3.setText(Integer.toString(totalPlayer3));
//            player4.setText(Integer.toString(totalPlayer4));
//            player5.setText(Integer.toString(totalPlayer5));
//            player6.setText(Integer.toString(totalPlayer6));
//        }
//        else {
//            hole = 1;
//            firstPointer = 0;
//            setTitle("Total Scores");
//            player1.setText(Integer.toString(totalPlayer1));
//            player2.setText(Integer.toString(totalPlayer2));
//            player3.setText(Integer.toString(totalPlayer3));
//            player4.setText(Integer.toString(totalPlayer4));
//            player5.setText(Integer.toString(totalPlayer5));
//            player6.setText(Integer.toString(totalPlayer6));
//        }
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
            int[] scores = new int[scoresList.size()];
            for (int i = 0; i < scoresList.size(); i ++) {
                scores[i] = (int) scoresList.get(i);
            }
            return scores;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
