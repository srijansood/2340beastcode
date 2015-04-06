package com.beastcode;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.beastcode.R;


public class RequestItemActivity extends ActionBarActivity {


    private EditText itemName, maxPrice, location;
    private int id;


    /**
     * onCreate method that gets the different properties of an item and puts them into
     * the database under the table of Items
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_item);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");


        itemName = (EditText)findViewById(R.id.rifItemName);
        maxPrice = (EditText)findViewById(R.id.rifMaxPrice);
        location = (EditText)findViewById(R.id.rifLocation);
//        itemName.requestFocus();

    }

    /**
     * submit button that allows for the item to be put into the database
     */
    public void submitItemRequest() {
        String riName = itemName.getText().toString();
        String riMaxPriceCheck = maxPrice.getText().toString();
        int riMaxPrice = 0;
        if (!riMaxPriceCheck.equals("")) {
            riMaxPrice = Integer.parseInt(riMaxPriceCheck);
        }
        String riLocation = location.getText().toString();


        if (riName.isEmpty()) {
            itemName.setError("Item must have a name");
            itemName.requestFocus();
        } else if(riMaxPriceCheck.isEmpty()) {
            maxPrice.setError("Must specify a Max Price");
            maxPrice.requestFocus();
        } else if (riMaxPrice <= 0) {
            maxPrice.setError("Number must be positive");
            maxPrice.requestFocus();
        } else if(riLocation.isEmpty()) {
            location.setError("Must have a location");
            location.requestFocus();
        } else {
            SQLiteDB db = new SQLiteDB(this);
            User requester = db.getUser(id);
//            Message.message(this, id + " is the id of requester");
            ItemRequest item = new ItemRequest(requester, riName, riMaxPrice, riLocation);
            db.addItemRequest(item);
//            Message.message(this, "Item added to request list");

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
        getMenuInflater().inflate(R.menu.menu_request_item, menu);
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

    @SuppressWarnings("EmptyMethod")
    public void submitItemRequest() {
    }
}
