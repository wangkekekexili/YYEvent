package com.yiyangzhu.yyevent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventListActivity extends AppCompatActivity {

    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListView = (ListView) findViewById(R.id.event_list);



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences storage = getSharedPreferences("events", MODE_PRIVATE);
        Map<String, ?> allEvents = storage.getAll();

        final List<String> eventNames = new ArrayList<>();
        final List<String> eventLocations = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEvents.entrySet()) {
            if (entry.getValue() instanceof String) {
                eventNames.add(entry.getKey());
                eventLocations.add(entry.getValue().toString());
            }
        }

        class EventListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return eventNames.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View row = inflater.inflate(R.layout.row_event, parent, false);
                TextView eventName = (TextView) row.findViewById(R.id.row_event_name);
                eventName.setText(eventNames.get(position));
                TextView eventLocation = (TextView) row.findViewById(R.id.row_event_location);
                eventLocation.setText(eventLocations.get(position));

                Button deleteButton = (Button) row.findViewById(R.id.delete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = getSharedPreferences("events", MODE_PRIVATE).edit();
                        editor.remove(eventNames.get(position));
                        editor.apply();
                        eventNames.remove(position);
                        eventLocations.remove(position);
                        notifyDataSetChanged();
                    }
                });

                return row;
            }
        }

        EventListAdapter adapter = new EventListAdapter();
        eventListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_event) {
            Intent eventCreationIntent = new Intent(this, EventCreationActivity.class);
            startActivity(eventCreationIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
