package com.beastcode;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beastcode.R;

import java.util.ArrayList;
import java.util.List;


public class ReportsInterestActivity extends ActionBarActivity {

    private int id;
    private String username;
    private ArrayAdapter mAdapter;

    /**
     * onCreate method that goes and gets the items that match your requested items
     * and are less than the max price you have and display them in a list.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_interest);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("Username");
        id = extras.getInt("identification");
        SQLiteDB db = new SQLiteDB(this);
        User currentUser = db.getUser(id);
        List<ItemRequest> requestedItems = db.getRequestsByUser(currentUser);
        List<ItemRequest> reports = db.getAllReportsITEM();
        ArrayList<String> askInsert = new ArrayList<>();

        for (ItemRequest i: requestedItems) {
            for(ItemRequest r: reports) {
                if (i.getName().equals(r.getName()) && r.getMaxPrice() <= i.getMaxPrice()) {
                    askInsert.add(r.getName() + " - Price: $" + r.getMaxPrice() + " - Location: " + r.getLocation());
                }
            }
        }

        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                askInsert);

        ListView myList = (ListView) findViewById(R.id.interestListView);
        myList.setAdapter(mAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * private inner class that handles if you click on a list item.
             * @param parent parent
             * @param view view
             * @param position position in the list of the item that you touched
             * @param id id...
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text or do whatever you need.
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                //adds the user to the database as a friend of the current user
                String username = ((TextView) view).getText().toString();


            }
        });
    }

    /**
     * continueToProfile method which is just for the button so you can move onto your
     * profile if you don't see anything that you like
     * @param view view
     */
    public void continueToProfile(View view) {
        Intent intent = new Intent(this, YourProfileActivity.class);
        intent.putExtra("Username", username);
        intent.putExtra("identification", id);
        startActivity(intent);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports_interest, menu);
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
