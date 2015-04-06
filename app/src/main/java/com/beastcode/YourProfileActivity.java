package com.beastcode;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.View;
import android.widget.TextView;

import com.beastcode.R;


public class YourProfileActivity extends ActionBarActivity {

    // --Commented out by Inspection:String username = "unknown";

    private int id;

    /**
     * onCreate method that takes shows your profile
     * @param savedInstanceState Bundles!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("Username");
        id = extras.getInt("identification");

        ((TextView) findViewById(R.id.profileUsername)).setText(username);

        SQLiteDB db = new SQLiteDB(this);
        User user = db.getUser(id);
        db.close();
//
//        ((TextView) findViewById(R.id.fDetailsName)).setText(user.getUsername());
        ((TextView) findViewById(R.id.ypEmail)).setText(user.getEmail());
        ((TextView) findViewById(R.id.ypRating)).setText("" + user.getRating());
        ((TextView) findViewById(R.id.ypReports)).setText("" + user.getNumReports());

    }

    /**
     * method that starts the sales Map with the locations of the sales on it
     */
    public void saleMap() {
        Intent intent = new Intent(this, MapsActivityReports.class);
        startActivity(intent);
    }


    /**
     * method to go logout and go back to the main menu
     */
    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * request an item button directs here
     */
    public void requestItem() {
        Intent intent = new Intent(this, RequestItemActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }

    /**
     * submit an item report button.
     */
    public void submitReportButton() {
        Intent intent = new Intent(this, ReportingSaleActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_your_profile, menu);
        getMenuInflater().inflate(R.menu.friends, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

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
            case R.id.action_displayRequestedItems:
                displayRequestedItems();
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
    private void displayFriends() {
        Intent intent = new Intent(this, FriendsList.class);
        //username = "dummy";
//        intent.putExtra(username);
        startActivity(intent);
    }

    /**
     * method for the menu so when Add Friends is clicked, it opens up the notFriendsListActivity.
     */
    private void displayNotFriends() {
        // display the list of people who are not your friends so you can add them
        Intent intent = new Intent(this, notFriendsListActivity.class);
        startActivity(intent);
    }
    private void displayRequestedItems() {
        Intent intent = new Intent(this, RequestedItemsList.class);
        startActivity(intent);
    }
}
