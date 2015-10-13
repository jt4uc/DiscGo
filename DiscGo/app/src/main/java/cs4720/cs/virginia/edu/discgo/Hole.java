package cs4720.cs.virginia.edu.discgo;

import android.util.Log;

public class Hole {

    private int id;
    private String name;
    private float latitude;
    private float longitude;
    private int par;
    private String starting_point_uri;
    private String ending_point_uri;

    public Hole() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public String getStartingPointUri() {
        return starting_point_uri;
    }

    public void setStartingPointUri(String starting_point_uri) {
        this.starting_point_uri = starting_point_uri;
    }

    public String getEndingPointUri() {
        return ending_point_uri;
    }

    public void setEndingPointUri(String ending_point_uri) {
        this.ending_point_uri = ending_point_uri;
    }



    public void save() {
        MyApplication.getDBHelper().saveHole(this);
    }

    public void delete() {
        MyApplication.getDBHelper().deleteHole(id);
    }

//    public Hole getHoleById() {
//        return MyApplication.getDBHelper().getHoleById(id);
//    }

//    public Hole getHoleByName() {
//        return MyApplication.getDBHelper().getHoleByName(name);
//    }
}
