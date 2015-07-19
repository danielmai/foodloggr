package com.foodlabelapp.foodlabel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;


public class DetailActivity extends ActionBarActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        File image = new File(getIntent().getStringExtra(MainActivity.PHOTO_KEY));
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_nutrition, R.id.nutrition_text_view);
        ListView list = (ListView) findViewById(R.id.detail_list_view);
        list.setAdapter(adapter);

        FetchOCRImageResponseTask task = new FetchOCRImageResponseTask(getApplicationContext(), adapter);
        task.execute(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
