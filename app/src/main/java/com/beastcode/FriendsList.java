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

import com.beastcode.R;

import java.util.ArrayList;
import java.util.List;


public class FriendsList extends ActionBarActivity {

    private List nameList = new ArrayList();
    private List<User> userList;
    private List<Integer> userIDList;

    /**
     * method for on Create that gets the list of your friends from the database and gives the
     * position of the one that you clicked on to the bundle to be used in the next screen
     * @param savedInstanceState the bundle that allows you to pass in the position
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        SQLiteDB db = new SQLiteDB(this);
        List<User> friendList = db.getAllFriends(User.currentUser);
        db.close();

        List nameList = new ArrayList();

        for (User user : friendList) {
            nameList.add(user.getName());
        }

        ArrayAdapter mAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                nameList);

        ListView myList = (ListView) findViewById(R.id.friendsList);
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
                friendDetails(position);

            }
        });

    }

    /**
     * friendDetails method that goes initiates the FriendDetails Activity and passes to it, the
     * position of the item that you clicked on
     * @param position position tells us the position of the item clicked
     */
    public void friendDetails(int position) {
        Intent i = new Intent(getApplicationContext(), FriendDetails.class);
        i.putExtra("Position", position);
        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friends, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_friends:
                displayFriends();
                return true;
            case R.id.action_Addfriends:
                displayNotFriends();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * method for the menu so when Friends is clicked it opens up the FriendsList activity
     */
    public void displayFriends() {
        Intent intent = new Intent(this, FriendsList.class);
        startActivity(intent);
        finish();
    }

    /**
     * method for the menu so when Add Friends is clicked, it opens up the notFriendsListActivity.
     */
    public void displayNotFriends() {
        // display the list of people who are not your friends so you can add them
        Intent intent = new Intent(this, notFriendsListActivity.class);
        startActivity(intent);
        finish();
    }
}