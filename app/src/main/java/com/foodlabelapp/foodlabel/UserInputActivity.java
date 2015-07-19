package com.foodlabelapp.foodlabel;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.ListActivity;

public class UserInputActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_input, menu);
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
    public void sendUserInfo(View button){
        //Do handling here
        final EditText nameField = (EditText)findViewById(R.id.EditTextName);
        String name = nameField.getText().toString();

        final EditText ageField = (EditText)findViewById(R.id.EditTextAge);
        String age = nameField.getText().toString();

        final EditText heightField = (EditText)findViewById(R.id.EditTextHeight);
        String height = nameField.getText().toString();

        final EditText weightField = (EditText)findViewById(R.id.EditTextWeight);
        String weight = nameField.getText().toString();

        Spinner genderSpinner = (Spinner)findViewById(R.id.SpinnerFeedbackType);
        genderSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        String[] mArray;
        mArray = getResources().getStringArray(R.array.feedbacktypelist);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(dataAdapter);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
