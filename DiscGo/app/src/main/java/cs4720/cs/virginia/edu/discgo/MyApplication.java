package cs4720.cs.virginia.edu.discgo;

import android.app.Application;

public class MyApplication extends Application {

    private static DBHelper databaseHelper;

    @Override
    public void onCreate(){
        super.onCreate();
        if (databaseHelper == null) {
            this.databaseHelper = new DBHelper(this);
        }
    }

    public static DBHelper getDBHelper() {
        return databaseHelper;
    }

}
