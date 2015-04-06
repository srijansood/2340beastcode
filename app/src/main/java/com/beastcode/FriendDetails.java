package com.beastcode;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beastcode.R;

import java.util.List;


public class FriendDetails extends ActionBarActivity {

    public static User user;

    /**
     * On create that takes in the position that you clicked from the friends list to be used
     * in finding the user that you clicked on;
     * @param savedInstanceState the bundle that allows you to pass in the position
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        Bundle extras = getIntent().getExtras();
        int idPrev = extras.getInt("Position");

        SQLiteDB db1 = new SQLiteDB(this);
        List<User> friendList = db1.getAllFriends(User.currentUser);
        User userID = friendList.get(idPrev);
        int id = userID.getId();
        db1.close();

//        String letters = extras.getString("Letters");
//        Message.message(this, "the id on the detail page " + id);
        SQLiteDB db = new SQLiteDB(this);
        user = db.getUser(id);
        db.close();
//
        ((TextView) findViewById(R.id.fDetailsName)).setText(user.getUsername());
        ((TextView) findViewById(R.id.fDetailsEmail)).setText(user.getEmail());
        ((TextView) findViewById(R.id.fDetailsRating)).setText("" + user.getRating());
        ((TextView) findViewById(R.id.fDetailsReports)).setText("" + user.getNumReports());
    }

    /**
     * method that is called when the remove friend button is called
     * @param view the view
     */
    public void removeFriend(View view) {
        SQLiteDB db = new SQLiteDB(this);
        //removes the friend from the database
        db.deleteFriend(User.currentUser, FriendDetails.user);
        db.close();
        Message.message(this, "Friend removed");
        back();
    }

    /**
     * method to go back to the friend list //not yet implemented
     */
    public void back() {
        Intent intent = new Intent(this, FriendsList.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
    public void displayFriends() {
        Intent intent = new Intent(this, FriendsList.class);
        //username = "dummy";
//        intent.putExtra(username);
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
