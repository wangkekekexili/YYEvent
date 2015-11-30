package com.yiyangzhu.yyevent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EventCreationActivity extends AppCompatActivity {

    private EditText eventName;
    private EditText eventTime;
    private CheckBox monday;
    private CheckBox tudsday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private Spinner locationPicker;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        eventName = (EditText) findViewById(R.id.event_name_input);
        eventTime = (EditText) findViewById(R.id.event_time_input);
        monday = (CheckBox) findViewById(R.id.event_monday_input);
        tudsday = (CheckBox) findViewById(R.id.event_tudesday_input);
        wednesday = (CheckBox) findViewById(R.id.event_wednesday_input);
        thursday = (CheckBox) findViewById(R.id.event_thursday_input);
        friday = (CheckBox) findViewById(R.id.event_friday_input);
        locationPicker = (Spinner) findViewById(R.id.location_picker);
        done = (Button) findViewById(R.id.done_input);

        // set up location picker spinner
        String[] buildings = getResources().getStringArray(R.array.buildings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, buildings);
        locationPicker.setAdapter(adapter);

    }

    public void done(View view) {
        // check input
        if (eventName.getText().toString().trim().length() == 0) {
            Toast.makeText(EventCreationActivity.this, "Event name cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = getSharedPreferences("events", MODE_PRIVATE).edit();
        editor.putString(eventName.getText().toString(), locationPicker.getSelectedItem().toString());
        editor.commit();
        finish();
    }
}
