package com.foodlabelapp.foodlabel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.foodlabelapp.foodlabel.picasso.GrayscaleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;

    private ImageView mImageView;
    String mCurrentPhotoPath;

    public static final String PHOTO_KEY = "photos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.take_picture_button);
        mImageView = (ImageView) findViewById(R.id.snapshot_image_view);
        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString(PHOTO_KEY);
            if (mCurrentPhotoPath != null) {
                displayGrayscaleImage();
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dispatchTakePictureIntent();
                //galleryAddPic();
            }
        });

        WebView calorieWebView = (WebView) findViewById(R.id.chart_carolies_webview);
        WebView fatWebView = (WebView) findViewById(R.id.chart_fat_webview);
        WebView sodiumWebView = (WebView) findViewById(R.id.chart_sodium_webview);
        WebView carbsWebView = (WebView) findViewById(R.id.chart_carbs_webview);
        InputStream is = getResources().openRawResource(R.raw.chart);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();

        try {
            String readLine = null;
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null) {
                buffer.append(readLine);
            }

            // Close the InputStream and BufferedReader
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Loading all the webviews for Batman
        /*String chartHtml = buffer.toString();
        webView.loadData(chartHtml, "text/html", null);*/
        String formatCaloriesUrl = "http://chart.apis.google.com/chart?cht=bhg&chs=200x125&chd=t:%f|%f&chco=4d89f9,c6d9fd&chl=Caloric Intake&chds=0,%f";
        String formatFatUrl = "http://chart.apis.google.com/chart?cht=bhg&chs=200x125&chd=t:%f|%f&chco=4d89f9,c6d9fd&chl=Total Fat&chds=0,%f";
        String formatSodiumUrl = "http://chart.apis.google.com/chart?cht=bhg&chs=200x125&chd=t:%f|%f&chco=4d89f9,c6d9fd&chl=Sodium Intake&chds=0,%f";
        String formatCarbsUrl = "http://chart.apis.google.com/chart?cht=bhg&chs=200x125&chd=t:%f|%f&chco=4d89f9,c6d9fd&chl=Carbohydrates Intake&chds=0,%f";

        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        String age = prefs.getString(Constants.PREFS_AGE_KEY, "");
        String weight = prefs.getString(Constants.PREFS_WEIGHT_KEY, "");
        String height = prefs.getString(Constants.PREFS_HEIGHT_KEY, "");
        int genderPos = prefs.getInt(Constants.PREFS_GENDER_KEY, 0);
        int dailyActPos = prefs.getInt(Constants.PREFS_ACTIVITY_KEY, 0);



        double EER = 0;
        if(!age.equals("") && !weight.equals("") && !height.equals("")){
            EER = Utilities.calculateEER(genderPos, Integer.parseInt(age), dailyActPos, Integer.parseInt(weight), Integer.parseInt(height));
        }
        double carbohydrates = (EER * .65)/4;
        double sodium = 2300;
        double totalFat = (EER * .35) /9;
        String urlCalories = String.format(formatCaloriesUrl, 10.0, EER,EER);
        String urlFat = String.format(formatFatUrl, 20.0, totalFat, totalFat);
        String urlSodium = String.format(formatSodiumUrl, 30.0, sodium, sodium);
        String urlCarbs = String.format(formatCarbsUrl, 40.0, carbohydrates, carbohydrates);
        calorieWebView.loadUrl(urlCalories);
        fatWebView.loadUrl(urlFat);
        sodiumWebView.loadUrl(urlSodium);
        carbsWebView.loadUrl(urlCarbs);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PHOTO_KEY, mCurrentPhotoPath);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                // ...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri fileUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            displayGrayscaleImage();
        }
    }

    private void displayGrayscaleImage() {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mImageView.setImageBitmap(bitmap);
                Drawable image = mImageView.getDrawable();

                if (mImageView != null && image != null) {
                    Log.d(LOG_TAG, "Saving new image view");
                    Bitmap bm = ((BitmapDrawable) image).getBitmap();
                    File file = new File(mCurrentPhotoPath);

                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(this).load("url").into(target);
        Picasso picasso = Picasso.with(this);
        picasso.load(new File(mCurrentPhotoPath))
                .transform(new GrayscaleTransformation(picasso))
                .resize(300, 500)
                .into(target);

        Log.d(LOG_TAG, "mImageView == null = " + (mImageView == null));
        Log.d(LOG_TAG, "mImageView.getDrawable() == null = " + (mImageView.getDrawable() == null));

        Button detailButton = (Button) findViewById(R.id.show_details_button);
        detailButton.setVisibility(View.VISIBLE);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra(PHOTO_KEY, mCurrentPhotoPath);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent i = new Intent(this, UserInputActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Adds the photo to the system photo gallery (viewable from the Photos app)
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
