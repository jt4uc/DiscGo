package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_scores);
        setTitle("Enter Scores");
        ArrayList<String> names =  MyApplication.getDBHelper().getAllNames();
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
        if(MyApplication.getDBHelper().getNumberOfPlayers()<2) {
            player2.setVisibility(View.INVISIBLE);
            player2Text.setVisibility(View.INVISIBLE);
        }else
        player2.setText(names.get(1));
        if(MyApplication.getDBHelper().getNumberOfPlayers()<3) {
            player3.setVisibility(View.INVISIBLE);
            player3Text.setVisibility(View.INVISIBLE);
        }else
            player3.setText(names.get(2));
        if(MyApplication.getDBHelper().getNumberOfPlayers() < 4) {
            player4.setVisibility(View.INVISIBLE);
            player4Text.setVisibility(View.INVISIBLE);
        }else
            player4.setText(names.get(3));
        if(MyApplication.getDBHelper().getNumberOfPlayers() <5) {
            player5.setVisibility(View.INVISIBLE);
            player5Text.setVisibility(View.INVISIBLE);
        }else
            player5.setText(names.get(4));
        if(MyApplication.getDBHelper().getNumberOfPlayers() <6) {
            player6.setVisibility(View.INVISIBLE);
            player6Text.setVisibility(View.INVISIBLE);
        }else
            player6.setText(names.get(5));
    }

    public void save(View v){
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
        if(String.valueOf(player1Text.getText()).equals(""))
            s1=-1;
        else
            s1 = Integer.parseInt(String.valueOf(player1Text.getText()));
        if(String.valueOf(player2Text.getText()).equals(""))
            s2=-1;
        else
            s2 = Integer.parseInt(String.valueOf(player2Text.getText()));
        if(String.valueOf(player3Text.getText()).equals(""))
            s3=-1;
        else
            s3 = Integer.parseInt(String.valueOf(player3Text.getText()));
        if(String.valueOf(player4Text.getText()).equals(""))
            s4=-1;
        else
            s4 = Integer.parseInt(String.valueOf(player4Text.getText()));
        if(String.valueOf(player5Text.getText()).equals(""))
            s5=-1;
        else
            s5 = Integer.parseInt(String.valueOf(player5Text.getText()));
        if(String.valueOf(player6Text.getText()).equals(""))
            s6=-1;
        else
            s6 = Integer.parseInt(String.valueOf(player6Text.getText()));

        // Toast.makeText(getApplicationContext(), s2, Toast.LENGTH_SHORT).show();
        MyApplication.getDBHelper().saveScore(s1, s2, s3, s4, s5, s6);

        ArrayList<Integer> scores = MyApplication.getDBHelper().getAllScores();
       
        //player1.setText("1");//scores.get(0));
        //player2.setText("2");//scores.get(1));

        Intent intent = new Intent(EnterScoresActivity.this, PlayMapsActivity.class);
        EnterScoresActivity.this.startActivity(intent);

    }

}
