package cs4720.cs.virginia.edu.discgo;

import android.app.Application;

import com.parse.Parse;

public class MyApplication extends Application {

    //private static DBHelper databaseHelper;
    private static String gameId;

    @Override
    public void onCreate(){
        super.onCreate();
//        if (databaseHelper == null) {
//            this.databaseHelper = new DBHelper(this);
//        }
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

}
