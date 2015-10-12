package cs4720.cs.virginia.edu.discgo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * ############### GOT RID OF MARKER STUFF ########################
 */
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "discgo.db";
    private static final int DATABASE_VERSION = 1;

    // Hole
    public static final String TABLE_HOLE = "hole_table";
    public static final String COLUMN_HOLE_ID = "hole_id";
    public static final String COLUMN_HOLE_NAME = "hole_name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_PAR = "par";
    public static final String COLUMN_STARTING_POINT_URI = "starting_point";
    public static final String COLUMN_ENDING_POINT_URI = "ending_point";

    // Create hole table
    private static final String TABLE_CREATE_HOLE= //SQL statement that creates the hole table
            "CREATE TABLE IF NOT EXISTS " + TABLE_HOLE + " (" +
                    COLUMN_HOLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_HOLE_NAME + " TEXT," +
                    COLUMN_LATITUDE + " FLOAT," +
                    COLUMN_LONGITUDE + " FLOAT," +
                    COLUMN_PAR + " INTEGER," +
                    COLUMN_STARTING_POINT_URI + " TEXT," +
                    COLUMN_ENDING_POINT_URI + " TEXT" + ");";

    // Hole columns
    private static final String[] allHoleColumns = {
            COLUMN_HOLE_ID,
            COLUMN_HOLE_NAME,
            COLUMN_LATITUDE,
            COLUMN_LONGITUDE,
            COLUMN_PAR,
            COLUMN_STARTING_POINT_URI,
            COLUMN_ENDING_POINT_URI
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //If database doesn't already exist, create it
        db.execSQL(TABLE_CREATE_HOLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //If database version is changed, upgrade/update database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOLE);
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
                hole.setLatitude(cursor.getFloat(cursor.getColumnIndex(COLUMN_LATITUDE)));
                hole.setLongitude(cursor.getFloat(cursor.getColumnIndex(COLUMN_LONGITUDE)));
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
        values.put(COLUMN_LATITUDE, hole.getLatitude());
        values.put(COLUMN_LONGITUDE, hole.getLongitude());
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
        values.put(COLUMN_LATITUDE, hole.getLatitude());
        values.put(COLUMN_LONGITUDE, hole.getLongitude());
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
        hole.setLatitude(cursor.getFloat(cursor.getColumnIndex(COLUMN_LATITUDE)));
        hole.setLongitude(cursor.getFloat(cursor.getColumnIndex(COLUMN_LONGITUDE)));
        hole.setPar(cursor.getInt(cursor.getColumnIndex(COLUMN_PAR)));
        hole.setStartingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_STARTING_POINT_URI)));
        hole.setEndingPointUri(cursor.getString(cursor.getColumnIndex(COLUMN_ENDING_POINT_URI)));

        return hole;
    }


}
