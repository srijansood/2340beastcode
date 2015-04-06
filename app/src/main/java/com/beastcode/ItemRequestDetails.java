package com.beastcode;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.List;


public class ItemRequestDetails extends ActionBarActivity {

    /**
     * onCreate method that accesses the database, gets the list of items and then gets the
     * different attributes of those items and puts them in text boxes to be displayed.
     * @param savedInstanceState the bundle that allows you to pass in the position
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_request_details);
        Bundle extras = getIntent().getExtras();
        int pos = extras.getInt("Position");
        SQLiteDB db = new SQLiteDB(this);
        List<ItemRequest> items = db.getAllItemsRequestedITEM();
        ItemRequest item = items.get(pos);
        User requester = item.getRequester();
        String prodName = item.getName();
        int maxPrice = item.getMaxPrice();
        String location = item.getLocation();

        db.close();

        ((TextView) findViewById(R.id.irdRequester)).setText(requester.getName());
        ((TextView) findViewById(R.id.irdName)).setText(prodName);
        ((TextView) findViewById(R.id.irdPrice)).setText("$" + maxPrice);
        ((TextView) findViewById(R.id.irdLocation)).setText(location);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_request_details, menu);
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
