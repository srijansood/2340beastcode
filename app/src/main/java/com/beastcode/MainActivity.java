package com.beastcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.beastcode.R;


public class MainActivity extends ActionBarActivity {


    /**
     * onCreate method that makes sqliteHelp for database.
     * @param savedInstanceState bundles!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * method to display a test toast notification
     */
    public void testMessage() {
        Message.message(this, "Test Toast");
    }

    /**
     * method for calling the register screen
     */
    public void registerScreen() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    /**
     * method for calling the login Screen
     */
    public void loginScreen() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            settings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that calls the settings tab
     */
    private void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

// --Commented out by Inspection START (4/6/15, 6:49 PM):
//    @SuppressWarnings("EmptyMethod")
//    public void loginScreen() {
//    }
// --Commented out by Inspection STOP (4/6/15, 6:49 PM)

// --Commented out by Inspection START (4/6/15, 6:49 PM):
//    @SuppressWarnings("EmptyMethod")
//    public void registerScreen() {
//    }
// --Commented out by Inspection STOP (4/6/15, 6:49 PM)
}