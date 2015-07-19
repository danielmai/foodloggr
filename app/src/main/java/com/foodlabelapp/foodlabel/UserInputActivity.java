package com.foodlabelapp.foodlabel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UserInputActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = UserInputActivity.class.getSimpleName();

    EditText nameField;
    EditText ageField;
    EditText heightField;
    EditText weightField;
    Spinner genderSpinner;
    Spinner dailyActivitySpinner;
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Called onPause");
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();

        Log.d(LOG_TAG, "nameField.getText().toString() = " + nameField.getText().toString());
        Log.d(LOG_TAG, "ageField.getText().toString() = " + ageField.getText().toString()); 
        editor.putString(Constants.PREFS_NAME_KEY, nameField.getText().toString());
        editor.putString(Constants.PREFS_AGE_KEY, ageField.getText().toString());
        editor.putString(Constants.PREFS_NAME_KEY, nameField.getText().toString());
        editor.putString(Constants.PREFS_WEIGHT_KEY, weightField.getText().toString());
        editor.putString(Constants.PREFS_HEIGHT_KEY, heightField.getText().toString());
        editor.putInt(Constants.PREFS_GENDER_KEY, genderSpinner.getSelectedItemPosition());
        editor.putInt(Constants.PREFS_ACTIVITY_KEY, dailyActivitySpinner.getSelectedItemPosition());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        //Do handling here
        nameField = (EditText)findViewById(R.id.EditTextName);
        ageField = (EditText)findViewById(R.id.EditTextAge);
        heightField = (EditText)findViewById(R.id.EditTextHeight);
        weightField = (EditText)findViewById(R.id.EditTextWeight);
        genderSpinner = (Spinner)findViewById(R.id.SpinnerFeedbackType);
        dailyActivitySpinner = (Spinner)findViewById(R.id.SpinnerActivityRange1);

        updateViews();


        String age = ageField.getText().toString();
        String name = nameField.getText().toString();
        String weight = weightField.getText().toString();
        String height = heightField.getText().toString();

//        String[] mArray;
//        mArray = getResources().getStringArray(R.array.feedbacktypelist);

       // genderSpinner.setOnItemSelectedListener(this);


        //dailyActivitySpinner.setOnItemSelectedListener(this);

//        Spinner weeklyActivitySpinner = (Spinner)findViewById(R.id.SpinnerActivityRange2);
//        weeklyActivitySpinner.setOnItemSelectedListener(this);

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, );
//        String[] mArray2;
//        mArray2 = getResources().getStringArray(R.array.actvitylist1);
//        String[] mArray3;
//        mArray3 = getResources().getStringArray(R.array.actvitylist2);



//        dailyActivitySpinner.setAdapter(dataAdapter);

        Integer genderPos = genderSpinner.getSelectedItemPosition();
        Integer dailyActPos = dailyActivitySpinner.getSelectedItemPosition();
//        Integer weeklyActPos = weeklyActivitySpinner.getSelectedItemPosition();

        double EER = 0;
        if(!age.equals("") && !weight.equals("") && !height.equals("")){
            EER = Utilities.calculateEER(genderPos, Integer.parseInt(age), dailyActPos, Integer.parseInt(weight), Integer.parseInt(height));
        }
        double carbohydrates = (EER * .65)/4;
        double sodium = 2300;
        double totalFat = (EER * .35) /9;
//        Utilities.calculateBMR(genderPos, Integer.parseInt(age), weeklyActPos, Integer.parseInt(weight), Integer.parseInt(height));

    }

    private void updateViews() {
        Log.d(LOG_TAG, "Called updateViews");
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        String name = prefs.getString(Constants.PREFS_NAME_KEY, "");
        String age = prefs.getString(Constants.PREFS_AGE_KEY, "");
        String weight = prefs.getString(Constants.PREFS_WEIGHT_KEY, "");
        String height = prefs.getString(Constants.PREFS_HEIGHT_KEY, "");
        int gender = prefs.getInt(Constants.PREFS_GENDER_KEY, 0);
        int activity = prefs.getInt(Constants.PREFS_ACTIVITY_KEY, 0);

        nameField.setText(name);
        ageField.setText(age);
        weightField.setText(weight);
        heightField.setText(height);
        genderSpinner.setSelection(gender);
        dailyActivitySpinner.setSelection(activity);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
