package com.beastcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beastcode.R;


public class MainActivity extends ActionBarActivity {

//    private ArrayList<Integer> list = new ArrayList<>();
//    private Random rand = new Random();
//    private String[] myStringArray = new String[25];
//    private int count = 0;

    SQLiteDB sqliteHelp;

    /**
     * onCreate method that makes sqliteHelp for database.
     * @param savedInstanceState bundles!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelp = new SQLiteDB(this);

        // UNCOMMENT FOR INITIAL LIST
//        String[]  myStringArray = {"A","B","C","A","B","C","A","B","C","A","B","Z"};
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myStringArray);
//        ListView myList=(ListView) findViewById(R.id.listView);
//        myList.setAdapter(myAdapter);

//        String[]  myStringArray = {""};
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myStringArray);
//        ListView myList=(ListView) findViewById(R.id.listView);
//        myList.setAdapter(myAdapter);
//        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // When clicked, show a toast with the TextView text or do whatever you need.
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//                profileActivity(view);
//            }
//        });

    }

    /**
     * method to display a test toast notification
     */
    public void testMessage() {
        Message.message(this, "Test Toast");
    }

    /**
     * method for calling the register screen
     * @param view view
     */
    public void registerScreen(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }

    /**
     * method for calling the login Screen
     * @param view view
     */
    public void loginScreen(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();

    }


//
//    public void update(View view) {
//        sqliteHelp.updateName("TEST", "TESTPASS");
//    }
//
//    public void deleteRow(View view) {
//        int count = sqliteHelp.deleteRow("qq");
//        Message.message(this, ""+count);
//    }
//





//    public void addRandomNumber(View view) {
//        list.add(rand.nextInt(25 - 0) + 1);
//
//        // UNCOMMENT TO MAKE ALERT OF ARRAY
////        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////        builder.setMessage(list.toString());
////        builder.setCancelable(true);
////        AlertDialog dialog = builder.create();
////        dialog.show();
//
//        ArrayAdapter<Integer> myAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1, list);
//        ListView myList=(ListView) findViewById(R.id.listView);
//        myList.setAdapter(myAdapter);
//
//    }



//    public void textInput(View view) {
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        myStringArray[count] = message;
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage(myStringArray[count]);
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        count++;
//    }


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
    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
