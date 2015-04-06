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

import com.beastcode.R;

import java.util.List;


public class RequestedItemsList extends ActionBarActivity {

//    private List<String> itemsRequester;
    /**
     * onCreate method that takes in the list of items and puts them into a list view
     * then allows you to click on the item in the list.
     * @param savedInstanceState default
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_items_list);
        SQLiteDB db = new SQLiteDB(this);
//        List<ItemRequest> items = db.getAllItemsRequestedITEM();
        List<String> itemList = db.getAllItemsRequested();

//        for (ItemRequest i: items) {
//            itemsRequester.add(i.getRequester().getName());
//        }

        db.close();

        ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                itemList);

        ListView myList = (ListView) findViewById(R.id.itemsRequestedlistView);
        myList.setAdapter(adapter);

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
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                //adds the user to the database as a friend of the current user
                requestedItemDetail(position);

            }
        });
    }

    /**
     * method that opens up the details of the item in the list that you selected
     * @param position position in the list of the item that you touched
     */
    void requestedItemDetail(int position) {
//        Message.message(this, "" + position);
        Intent i = new Intent(getApplicationContext(), ItemRequestDetails.class);
        i.putExtra("Position", position);
//        i.putExtra("Letters", "hello");
        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_requested_items_list, menu);
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
