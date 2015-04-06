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


public class notFriendsListActivity extends ActionBarActivity {


    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private final List nameList = new ArrayList();
    private List<User> userList;
    private ArrayAdapter mAdapter;


    /**
     * onCreate method which makes a list depending on who is your friend already
     * if they are not your friend already then they are added to the list for you to add
     * to your friends list if you want to.
     * @param savedInstanceState Bundles!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_friends_list);

        SQLiteDB db = new SQLiteDB(this);
        userList = db.getAllUsers();
        List friendList = db.getAllFriends(User.currentUser);
        List<User> list = db.getAllUsers();
        db.close();

        for (User user : list) {
            if (!user.equals(User.currentUser) && !friendList.contains(user)) {
                //noinspection unchecked
                nameList.add(user.getName());
            } else
                userList.remove(user); //removes the irrelevant user
        }
        //noinspection unchecked
        mAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                nameList);
        ListView myList = (ListView) findViewById(R.id.notFriendsList);
        myList.setAdapter(mAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text or do whatever you need.
                Toast.makeText(getApplicationContext(), ((TextView) view).getText() + " is now your friend", Toast.LENGTH_LONG).show();
                //adds the user to the database as a friend of the current user
                addToFriendList(position);

            }
        });

    }

    /**
     * method called that adds the friend to your friends list when you click on the person in the
     * list
     * @param position the position of the list they were in.
     */
    void addToFriendList(int position) {
        SQLiteDB db = new SQLiteDB(this);
        db.addFriend(User.currentUser, userList.get(position));
        db.close();
        nameList.remove(position);
        userList.remove(position);
        //refreshes the adapter
        mAdapter.notifyDataSetChanged();
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
        @SuppressWarnings("UnusedAssignment") int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

//        return super.onOptionsItemSelected(item);

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
    void displayFriends() {
        Intent intent = new Intent(this, FriendsList.class);
        //username = "dummy";
//        intent.putExtra(username);
        startActivity(intent);
        finish();
    }

    /**
     * method for the menu so when Add Friends is clicked, it opens up the notFriendsListActivity.
     */
    void displayNotFriends() {
        // display the list of people who are not your friends so you can add them
        Intent intent = new Intent(this, notFriendsListActivity.class);
        startActivity(intent);
        finish();
    }

}
