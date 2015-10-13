package cs4720.cs.virginia.edu.discgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "discgo.db";
    private static final int DATABASE_VERSION = 1;

    // Hole
    public static final String TABLE_HOLE = "hole_table";
    public static final String COLUMN_HOLE_ID = "hole_id";
    public static final String COLUMN_HOLE_NAME = "hole_name";
    public static final String COLUMN_PAR = "par";
    public static final String COLUMN_STARTING_POINT_URI = "starting_point";
    public static final String COLUMN_ENDING_POINT_URI = "ending_point";


    // Marker
    //Sets up marker location tables
    public static final String TABLE_MARKER = "marker_table";
    public static final String COLUMN_MARKER_ID = "marker_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    //scores per hole
    public static final String TABLE_SCORE = "score_table";
    public static final String COLUMN_HOLE_SCORE_ID = "hole_id";
    public static final String COLUMN_PLAYER_1 = "player_one";
    public static final String COLUMN_PLAYER_2 = "player_two";
    public static final String COLUMN_PLAYER_3 = "player_three";
    public static final String COLUMN_PLAYER_4 = "player_four";
    public static final String COLUMN_PLAYER_5 = "player_five";
    public static final String COLUMN_PLAYER_6 = "player_six";

    //names
    public static final String TABLE_NAME = "name_table";
    public static final String COLUMN_NAME_ID = "name_id";
    public static final String COLUMN_NAME = "name";

    public int numberOfPlayers = 0;
    // Create hole table
    private static final String TABLE_CREATE_HOLE= //SQL statement that creates the hole table
            "CREATE TABLE IF NOT EXISTS " + TABLE_HOLE + " (" +
                    COLUMN_HOLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_HOLE_NAME + " TEXT," +
                    COLUMN_PAR + " INTEGER," +
                    COLUMN_STARTING_POINT_URI + " TEXT," +
                    COLUMN_ENDING_POINT_URI + " TEXT" + ");";

    // Create marker table
    private static final String TABLE_CREATE_MARKER =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MARKER + " (" +
                    COLUMN_MARKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_LATITUDE + " FLOAT," +
                    COLUMN_LONGITUDE + " FLOAT" + ")";

    private static final String TABLE_CREATE_SCORE= //SQL statement that creates the score table
            "CREATE TABLE IF NOT EXISTS " + TABLE_SCORE + " (" +
                    COLUMN_HOLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PLAYER_1 + " INTEGER,"+
                    COLUMN_PLAYER_2 + " INTEGER,"+
                    COLUMN_PLAYER_3 + " INTEGER,"+
                    COLUMN_PLAYER_4 + " INTEGER,"+
                    COLUMN_PLAYER_5 + " INTEGER,"+
                    COLUMN_PLAYER_6 + " INTEGER"+" );";

    private static final String TABLE_CREATE_NAME =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " STRING" + ");";

    // Hole columns
    private static final String[] allHoleColumns = {
            COLUMN_HOLE_ID,
            COLUMN_HOLE_NAME,
            COLUMN_PAR,
            COLUMN_STARTING_POINT_URI,
            COLUMN_ENDING_POINT_URI
    };

    // Marker columns
    public static final String[] allMarkerColumns = {
            COLUMN_MARKER_ID,
            COLUMN_LATITUDE,
            COLUMN_LONGITUDE
    };

    private static final String[] allScoreColumns = {
            COLUMN_HOLE_SCORE_ID,
            COLUMN_PLAYER_1,
            COLUMN_PLAYER_2,
            COLUMN_PLAYER_3,
            COLUMN_PLAYER_4,
            COLUMN_PLAYER_5,
            COLUMN_PLAYER_6,



    };

    private static final String[] allNameColumns = {
            COLUMN_NAME_ID,
            COLUMN_NAME,

    };
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //If database doesn't already exist, create it
        db.execSQL(TABLE_CREATE_HOLE);
        db.execSQL(TABLE_CREATE_MARKER);
        db.execSQL(TABLE_CREATE_SCORE);
        db.execSQL(TABLE_CREATE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //If database version is changed, upgrade/update database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        db.close();
    }



    public ArrayList<Hole> getAllHoles() {      //Cursor is basically the "pointer" where the database is looking at on the table at a given moment
        ArrayList<Hole> holes = new ArrayList<>();
        open();
        Cursor cursor = db.query(TABLE_HOLE, allHoleColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) { //If successfully moves to new row in table, return true
                Hole hole = new Hole();
                hole.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOLE_ID)));
                hole.setName(cursor.getString(cursor.getColumnIndex(COLUMN_HOLE_NAME)));
                hole.setPar(cursor.getInt(cursor.getColumnIndex(COLUMN_PAR)));
                hole.setStartingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_STARTING_POINT_URI)));
                hole.setEndingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_ENDING_POINT_URI)));
                holes.add(hole);
                cursor.moveToNext();
            }
        }
        return holes;
    }


    public Hole saveHole(Hole hole) {
        open();
        ContentValues values = new ContentValues(); //Don't .put COLUMN_HOLE_ID because it is autoincrementing
        values.put(COLUMN_HOLE_NAME, hole.getName());
        values.put(COLUMN_PAR, hole.getPar());
        values.put(COLUMN_STARTING_POINT_URI, hole.getStartingPointUri());
        values.put(COLUMN_ENDING_POINT_URI, hole.getEndingPointUri());
        int insertid = (int) db.insert(TABLE_HOLE, null, values); // was long
        hole.setId(insertid);
        close();
        return hole;
    }

    public void updateHole(Hole hole) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOLE_NAME, hole.getName());
        values.put(COLUMN_PAR, hole.getPar());
        values.put(COLUMN_STARTING_POINT_URI, hole.getStartingPointUri());
        values.put(COLUMN_ENDING_POINT_URI, hole.getEndingPointUri());
        int update = db.update(TABLE_HOLE, values, COLUMN_HOLE_ID + " = " + hole.getId(), null);
        close();
    }

    public void deleteHole(int hole_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_HOLE, COLUMN_HOLE_ID + " = ?",
                new String[]{String.valueOf(hole_id)});
    }

    public Hole getHoleById(int hole_id) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_HOLE + " WHERE "
                + COLUMN_HOLE_ID + " = " + hole_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Hole hole = new Hole();
        hole.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_HOLE_ID)));
        hole.setName(cursor.getString(cursor.getColumnIndex(COLUMN_HOLE_NAME)));
        hole.setPar(cursor.getInt(cursor.getColumnIndex(COLUMN_PAR)));
        hole.setStartingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_STARTING_POINT_URI)));
        hole.setEndingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_ENDING_POINT_URI)));

        return hole;
    }

    public ArrayList<Integer> getAllScores() {      //Cursor is basically the "pointer" where the database is looking at on the table at a given moment
        ArrayList<Integer> scores = new ArrayList<>();
        open();
        Cursor cursor = db.query(TABLE_SCORE, allScoreColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) { //If successfully moves to new row in table, return true
                cursor.getInt(cursor.getColumnIndex(COLUMN_HOLE_SCORE_ID));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_1)));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_2)));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_3)));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_4)));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_5)));
                scores.add(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_6)));
                cursor.moveToNext();
            }
        }
        return scores;
    }

    public ArrayList<String> getAllNames() {      //Cursor is basically the "pointer" where the database is looking at on the table at a given moment
        ArrayList<String> names = new ArrayList<>();
        open();
        Cursor cursor = db.query(TABLE_NAME, allNameColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) { //If successfully moves to new row in table, return true
                cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                names.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                cursor.moveToNext();
            }
        }
        return names;
    }

    public void deleteHole() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SCORE, COLUMN_HOLE_SCORE_ID + " = ? ", new String[]{String.valueOf(0)});
    }

    public void saveScore(int s1, int s2, int s3, int s4, int s5, int s6) {

        open();
        ContentValues values = new ContentValues(); //Don't .put COLUMN_HOLE_ID because it is autoincrementing
        values.put(COLUMN_PLAYER_1 , s1);
        values.put(COLUMN_PLAYER_2, s2);
        values.put(COLUMN_PLAYER_3, s3);
        values.put(COLUMN_PLAYER_4, s4);
        values.put(COLUMN_PLAYER_5, s5);
        values.put(COLUMN_PLAYER_6, s6);
        int insertid = (int) db.insert(TABLE_SCORE, null, values); // was long
        close();

    }
    public void saveName(String n ) {

        open();
        ContentValues values = new ContentValues(); //Don't .put COLUMN_HOLE_ID because it is autoincrementing
        values.put(COLUMN_NAME, n);
        int insertid = (int) db.insert(TABLE_NAME, null, values);
         numberOfPlayers++;
        close();

    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    public void resetScore(){
        open();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        db.execSQL(TABLE_CREATE_SCORE);
        close();
    }
    public void resetNames(){
        open();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(TABLE_CREATE_NAME);
        numberOfPlayers = 0;
        close();
    }
}
