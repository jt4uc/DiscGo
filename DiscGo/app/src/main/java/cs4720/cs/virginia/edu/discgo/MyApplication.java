package cs4720.cs.virginia.edu.discgo;

import android.app.Application;

import com.parse.Parse;

import java.util.ArrayList;

public class MyApplication extends Application {

    //private static DBHelper databaseHelper;
    private static String gameId;
    private static ArrayList<String> playerIds;

    @Override
    public void onCreate(){
        super.onCreate();
//        if (databaseHelper == null) {
//            this.databaseHelper = new DBHelper(this);
//        }

        playerIds = new ArrayList<String>();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "nZBOqTUWl6JHqSRUQnLkEnJCxHf8agq6tuQ01M7b", "tqO47wZpOLJN7XOl46V9KT2wov7FKooMDgASJEzu");

    }

//    public static DBHelper getDBHelper() {
//        return databaseHelper;
//    }

    public static String getGameId() {
        return gameId;
    }

    public static void setGameId(String gameId_) {
        gameId = gameId_;
    }

    public static ArrayList<String> getPlayerIds() {
        return playerIds;
    }

    public static void setPlayerIds(ArrayList<String> playerIds_) {
        playerIds = playerIds_;
    }

}
