package com.beastcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beastcode.R;


public class Register extends ActionBarActivity {

    private Button button;
    private EditText nameText;
    private EditText unText;
    private EditText passText;
    private EditText emailText;
    private EditText pass2Text;


    /**
     * onCreate that handles registration of a new user
     * @param savedInstanceState Bundles!
     */
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        @SuppressWarnings("UnusedAssignment") @SuppressLint("CutPasteId") EditText username = (EditText) findViewById(R.id.rUsername);
        @SuppressWarnings("UnusedAssignment") @SuppressLint("CutPasteId") EditText password = (EditText) findViewById(R.id.rPassword);
        @SuppressWarnings("UnusedAssignment") SQLiteDB sqliteHelp = new SQLiteDB(this);

//        button = (Button)findViewById(R.id.button);
        nameText = (EditText)findViewById(R.id.rName);
        unText = (EditText)findViewById(R.id.rUsername);
        passText = (EditText)findViewById(R.id.rPassword);
        pass2Text = (EditText)findViewById(R.id.rPassword2);
        emailText = (EditText)findViewById(R.id.rEmail);
        nameText.requestFocus();


    }

    /**
     * method that handles adding the user to the SQLite database
     */
    public void addUser() {
//        String user = username.getText().toString();
//        String pass = password.getText().toString();
//
//        long id = sqliteHelp.addUser(user);
//        if (id < 0) {
//            Message.message(this, "");
//        } else {
//            Message.message(this, "Successfully added a row");
//        }
        SQLiteDB db = new SQLiteDB(this);
        String name = nameText.getText().toString();
        String un = unText.getText().toString();
        String pass = passText.getText().toString();
        String pass2 = pass2Text.getText().toString();
        String email = emailText.getText().toString();
        EditText mUsernameView = (EditText) findViewById(R.id.rUsername);
        EditText mPasswordView = (EditText) findViewById(R.id.rPassword2);
        if (un.equals("") || pass.equals("") || email.equals("") || name.equals("") || pass2.equals("")) {
            mUsernameView.setError("No fields can be empty");
            mUsernameView.requestFocus();
        } else if (db.checkUser(un)) {
            mUsernameView.setError("Username already exists");
            mUsernameView.requestFocus();
        } else if (!pass2.equals(pass)) {
            mPasswordView.setError("Passwords do not match");
        } else {
            db.addUser(new User(name, un, email, pass));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Message.message(this, "Successfully Registered");
            finish();
        }
        db.close();


    }

    /**
     * method that takes you to the login screen!
     */
    public void loginScreen() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    /**
     * method that cancels and brings  you back to the main screen
     */
    public void cancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void addUser(View view) {
    }

    public void cancel(View view) {
    }
}
