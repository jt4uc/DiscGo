package cs4720.cs.virginia.edu.discgo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    ImageView imageView;

    private int par = 0;
    private String holeName = "";
    private String parString = "";
    private String starting_path = "";
    private String ending_path = "";

    private static final String SAVED_PAR = "";
    private static final String STARTING_POINT_URI = "STARTING";
    private static final String ENDING_POINT_URI = "ENDING";

    private boolean savedStateExists = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holeName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        setTitle(holeName);
        setContentView(R.layout.activity_camera);
        if (savedInstanceState != null) { // on orientation change restore the state
            parString = savedInstanceState.getString(SAVED_PAR);
            if(!parString.equals(""))
                par = Integer.parseInt(parString);

            starting_path = savedInstanceState.getString(STARTING_POINT_URI);

            ending_path = savedInstanceState.getString(ENDING_POINT_URI);

            savedStateExists = true;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(savedStateExists) {
            mCurrentPhotoPath = starting_path;
            imageView = (ImageView) findViewById(R.id.imageView);
            setPic();

            mCurrentPhotoPath = ending_path;
            imageView = (ImageView) findViewById(R.id.imageView2);
            setPic();
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {

        EditText parText = (EditText) findViewById(R.id.par);
        if(!parText.getText().equals("")) {
            parString = String.valueOf(parText.getText());
        }
        outState.putString(SAVED_PAR, parString);
        outState.putString(STARTING_POINT_URI, starting_path);
        outState.putString(ENDING_POINT_URI, ending_path);

        super.onSaveInstanceState(outState);

    }

    public void firstImage(View v){
        imageView = (ImageView) findViewById(R.id.imageView);
        dispatchTakePictureIntent(0);
    }

    public void secondImage(View v){
        imageView = (ImageView) findViewById(R.id.imageView2);
        dispatchTakePictureIntent(1);
    }

    public void save(View v) {
        EditText parText = (EditText) findViewById(R.id.par);
        if(String.valueOf(parText.getText()).equals("")) {
            par = 0;
        }
        else {
            par = Integer.parseInt(String.valueOf(parText.getText()));
        }

        Hole h = new Hole();
        h.setName(holeName);
        h.setPar(par);
        h.setStartingPointUri(starting_path);
        h.setEndingPointUri(ending_path);
        h.save();

        ArrayList<Hole> holes = MyApplication.getDBHelper().getAllHoles();
        String holeNames = "";
        for(int i = 0; i < holes.size(); i++) {
            holeNames += holes.get(i).getName();
        }

        Toast toast = Toast.makeText(getApplicationContext(), "Saved to database. Holes in database: " + holeNames, Toast.LENGTH_SHORT);
        toast.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent(int start_or_end) {
        //start_or_end determines which image it is
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        //
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getApplicationContext(), "fail to make file ", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                if(start_or_end == 0) {
                    starting_path = photoFile.getAbsolutePath();
                } else {
                    ending_path = photoFile.getAbsolutePath();
                }
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            galleryAddPic();
            setPic();
        }
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
