package com.beastcode;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.beastcode.R;


public class SettingsActivity extends ActionBarActivity {

    private SQLiteDB sqliteHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sqliteHelp = new SQLiteDB(this);
    }

    /**
     * method to call to see all of the contents of the database table with users
     */
    public void databaseContents() {
        String users = sqliteHelp.getAllUsersString();
        Message.message(this, users);
    }

    /**
     * method to clear the database of everything in every table
     */
    public void clearDatabase() {
        sqliteHelp.clearDatabase();
        Message.message(this, "Database Cleared");
    }

    /**
     * clear the users from the database
     */
    public void clearUsersAndFriends() {
        sqliteHelp.clearUsers();
        Message.message(this, "Users and Friends Cleared");
    }

    /**
     * method that clears the requested items from the database
     */
    public void clearItemsRequested() {
        sqliteHelp.clearItemsRequested();
        Message.message(this, "Items Requested Cleared");
    }

    /**
     * method that clears the reports in the database
     */
    public void clearReports() {
        sqliteHelp.clearReports();
        Message.message(this, "Reports Cleared");
    }

    /**
     * method that shows all the items that have been requested
     */
    public void showItemsRequested() {
        String itemsList = sqliteHelp.getAllItemsString();
        Message.message(this, itemsList);
    }

    /**
     * method that shows friends table in database
     */
    public void showFriends() {
        String friends = sqliteHelp.getAllFriendsString();
        Message.message(this, friends);
    }

    public void showReports() {
        String reports = sqliteHelp.getAllReportsString();
        Message.message(this, reports);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
    public void databaseContents() {
    }

    @SuppressWarnings("EmptyMethod")
    public void clearDatabase() {
    }

    @SuppressWarnings("EmptyMethod")
    public void showItemsRequested() {
    }

    @SuppressWarnings("EmptyMethod")
    public void clearUsersAndFriends() {
    }

    @SuppressWarnings("EmptyMethod")
    public void clearItemsRequested() {
    }

    @SuppressWarnings("EmptyMethod")
    public void showFriends() {
    }

    @SuppressWarnings("EmptyMethod")
    public void clearReports() {
    }

    @SuppressWarnings("EmptyMethod")
    public void showReports() {
    }
}
