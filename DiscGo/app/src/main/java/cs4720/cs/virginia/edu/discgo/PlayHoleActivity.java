package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayHoleActivity extends AppCompatActivity {
    String mCurrentPhotoPath;
    ImageView imageView;

    private int par = 0;
    private int holeId;
    private String holeName = "";
    private String starting_path = "";
    private String ending_path = "";

    //private Hole hole;
    private ParseObject hole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String objectId = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Hole");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    holeName = object.getString("holeName");
                    par = object.getInt("par");
                    setTitle(holeName);
                    setContentView(R.layout.activity_playhole);
                    TextView parText = (TextView) findViewById(R.id.playpartext);
                    parText.setText("Par: " + par);

                    ArrayList<String> colNames = new ArrayList<String>();
                    colNames.add("startPic");
                    colNames.add("endPic");
                    for (int i = 0; i < colNames.size(); i++) {
                        final ParseFile file = (ParseFile) object.get(colNames.get(i));
                        file.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    // data has the bytes for the resume
                                    FileOutputStream fos = null;
                                    try {
                                        createImageFile();
                                        fos = new FileOutputStream(mCurrentPhotoPath);
                                        fos.write(data);
                                        fos.close();
                                        if (file.getName().contains("start")) {
                                            starting_path = mCurrentPhotoPath;
                                            imageView = (ImageView) findViewById(R.id.firstpic);
                                            setPic();
                                        } else {
                                            ending_path = mCurrentPhotoPath;
                                            imageView = (ImageView) findViewById(R.id.secondpic );
                                            setPic();
                                        }
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    } catch (IOException e2) {
                                        // TODO Auto-generated catch block
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

//        holeName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
//        setTitle(holeName);
//        holeId = Integer.parseInt(getIntent().getStringExtra("ID"));
//        hole = MyApplication.getDBHelper().getHoleById(holeId);
//        setContentView(R.layout.activity_playhole);
//        TextView parText = (TextView) findViewById(R.id.playpartext);
//        parText.setText("Par: " + hole.getPar());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (mCurrentPhotoPath != null) {
            mCurrentPhotoPath = starting_path;
            imageView = (ImageView) findViewById(R.id.firstpic);
            setPic();

            mCurrentPhotoPath = ending_path;
            imageView = (ImageView) findViewById(R.id.secondpic);
            setPic();
        }


//            mCurrentPhotoPath = hole.getStartingPointUri();
//            imageView = (ImageView) findViewById(R.id.firstpic);
//            setPic();
//
//            mCurrentPhotoPath = hole.getEndingPointUri();
//            imageView = (ImageView) findViewById(R.id.secondpic);
//            setPic();

    }

    @Override
    public void onBackPressed() {
        Intent cameraIntent = new Intent(PlayHoleActivity.this, PlayMapsActivity.class);
        PlayHoleActivity.this.startActivity(cameraIntent);
    }

    public void enterScore(View v) {

        Intent enterIntent = new Intent(PlayHoleActivity.this, EnterScoresActivity.class);
        PlayHoleActivity.this.startActivity(enterIntent);

    }

    private void createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
    }

    private void setPic() {
        // Get the dimensions of the View

        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
