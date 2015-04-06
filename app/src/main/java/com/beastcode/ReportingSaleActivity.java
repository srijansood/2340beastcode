package com.beastcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.beastcode.R;


public class ReportingSaleActivity extends ActionBarActivity {

    private EditText itemName, price;
    private int id;
    private User requester;
    SharedPreferences prefs = null;

    /**
     * onCreate Overrride that is for reporting a sale
     * @param savedInstanceState bundle!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_sale);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");


        itemName = (EditText)findViewById(R.id.rsName);
        price = (EditText)findViewById(R.id.rsPrice);
//        location = (EditText)findViewById(R.id.rsLocation);
        prefs = this.getSharedPreferences("LatLng",MODE_PRIVATE);

    }

    /**
     * method that adds a pin when a place on the map is clicked on
     * @param view view
     */
    public void addPinMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /**
     * submit button that allows for the item to be put into the database
     * @param view view
     */
    public void submitReport(View view) {
        String rsName = itemName.getText().toString();
        String rsPriceCheck = price.getText().toString();
        int rsPrice = 0;
        if (!rsPriceCheck.equals("")) {
            rsPrice = Integer.parseInt(rsPriceCheck);
        }
//        String riLocation = location.getText().toString();



        if (rsName.isEmpty()) {
            itemName.setError("Item must have a name");
            itemName.requestFocus();
        } else if(rsPriceCheck.isEmpty()) {
            price.setError("Must specify a price");
            price.requestFocus();
        } else if (rsPrice <= 0) {
            price.setError("Number must be positive");
            price.requestFocus();
        } else if (prefs.getInt("isClicked", 0) == 0) {
            price.setError("Must specify a location");
        } else {
            SQLiteDB db = new SQLiteDB(this);
            requester = db.getUser(id);

            String lat = prefs.getString("Lat", "");
            String lng = prefs.getString("Lng", "");
//            Message.message(this, lat + " " + lng);
            String location = (lat + " " + lng);
            prefs.edit().clear().commit();

            ItemRequest item = new ItemRequest(requester, rsName, rsPrice, location);
            db.addItemReport(item);
            Message.message(this, "Report Created");

            Intent intent = new Intent(this, YourProfileActivity.class);
            intent.putExtra("Username", requester.getUsername());
            intent.putExtra("identification", id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporting_sale, menu);
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
