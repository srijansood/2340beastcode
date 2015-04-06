package com.beastcode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Login extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private List<User> users;
    private SQLiteDB db;
    private ArrayList<String> askInterest;

    /**
     * onCreate that handles the login
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mUsernameView.requestFocus();
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        db = new SQLiteDB(this);
        users = db.getAllUsers();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * method that is called when you want to go back to the welcome screen
     */
    public void gotoWelcomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * AutoComplete
     */
    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && isPasswordValid(password)) {
            mPasswordView.setError("Incorrect Password");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUsernameView.setError("Error username Required");
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(email)) {
            mUsernameView.setError("Error Invalid Username");
            focusView = mUsernameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Error password Required");
            focusView = mPasswordView;
            cancel = true;
        } else if (isPasswordValid(password)) {
            mPasswordView.setError("Error Invalid Password");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            canLogin();
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }

    /**
     * seeing if the username is valid or not
     * @param un username
     * @return if it is a valid length or not
     */
    private boolean isUsernameValid(String un) {
        return un.length() > 0;
    }

    /**
     * seeing if the password is valid or not
     * @param password password
     * @return if it is a valid length or not
     */
    private boolean isPasswordValid(String password) {
        return password.length() <= 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    void showProgress() {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(View.VISIBLE);
                }
            });

            mProgressView.setVisibility(View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(View.GONE);
            mLoginFormView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**
     * Responsible for profile queries
     */
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    /**
     * add emails to Auto Complete
     * @param emailAddressCollection email address
     */
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mUsernameView.setAdapter(adapter);
    }

    /**
     * method that authenticates the login and starts the login process
     */
    private void canLogin() {
        boolean valid = false;
        for (User user : users) {
            String un = user.getUsername();
            String pass = user.getPassword();
            int identification = user.getId();
            if (un.equals(mUsernameView.getText().toString())) {
                // Account exists, return true if the password matches.
                if (pass.equals(mPasswordView.getText().toString())) {
                    User.currentUser = user;
                    if (launchInterest(user)) {
                        Intent intent = new Intent(Login.this, ReportsInterestActivity.class);
                        intent.putExtra("Username", user.getUsername());
                        intent.putExtra("identification", identification);
                        intent.putStringArrayListExtra("InterestList", askInterest);

                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Login.this, YourProfileActivity.class);
                        intent.putExtra("Username", user.getUsername());
                        intent.putExtra("identification", identification);
                        startActivity(intent);
                        finish();
                    }

                    valid = true;
                }
            }
        }
        if (!valid) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setMessage("Invalid Username or Password!");
            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * method to determine if the interest form should be shown before your profile is.
     * @param currentUser the user who's profile has been selected
     */
    boolean launchInterest(User currentUser) {
//        Message.message(this, currentUser.getName() + " is the current user");
        List<ItemRequest> items = db.getRequestsByUser(currentUser);
        List<ItemRequest> reports = db.getAllReportsITEM();
        askInterest = new ArrayList<>();

        String suggest = "";
        for (ItemRequest i: items) {
            for(ItemRequest r: reports) {
                if (i.getName().equals(r.getName()) && r.getMaxPrice() <= i.getMaxPrice()) {
                    askInterest.add(i.getName());
                    suggest += i.getName() + " ";
                }
            }
        }
//        Message.message(this, suggest);
        return !askInterest.isEmpty();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            for (User user : users) {
                String un = user.getUsername();
                String pass = user.getPassword();

                if (un.equals(mUsername)) {
                    // Account exists, return true if the password matches.
                    if (pass.equals(mPassword)) {
                        User.currentUser = user;
                        return true;
                    } else
                        return false;
                }
            }
            // TODO: register the new account here.
            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress();
            if (success) {
                finish();
                Intent intent = new Intent(Login.this, YourProfileActivity.class);
                intent.putExtra("Username", mUsername);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError("Incorrect credentials");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress();
        }
    }
}