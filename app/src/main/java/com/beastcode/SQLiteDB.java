package com.beastcode;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "liquidsn0w";

    // Contacts table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FRIENDS = "friends";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_REPORTS = "reports";

    // common column names
    private static final String KEY_ID = "id";

    // users Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_UN = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PW = "password";
    private static final String KEY_NUM_REP = "numReports";
    private static final String KEY_RATE = "rating";
    private static final String KEY_ADMIN = "isAdmin";
    private static final String KEY_LOCK = "isLocked";

    // friends table column names
    private static final String KEY_BASE = "base";
    private static final String KEY_FRIEND = "friend";

    // items table column names price, name, user, location
    private static final String KEY_UID = "userId";
    private static final String KEY_ITEM_NAME = "itemName";
    private static final String KEY_MAX_PRICE = "maxPrice";
    private static final String KEY_LOCATION = "location";

    private static final String KEY_PRICE = "price of item";


    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates databases with two tables
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_UN + " TEXT," + KEY_PW + " TEXT," + KEY_EMAIL + " TEXT,"
                + KEY_RATE + " INTEGER," + KEY_NUM_REP + " INTEGER," + KEY_ADMIN + " BOOLEAN,"
                + KEY_LOCK + " BOOLEAN" + ")";
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BASE + " INTEGER,"
                + KEY_FRIEND + " INTEGER" + ")";
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " INTEGER,"
                + KEY_ITEM_NAME + " TEXT," + KEY_MAX_PRICE + " INTEGER," + KEY_LOCATION
                + " TEXT" + ")";
        String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_REPORTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " INTEGER,"
                + KEY_ITEM_NAME + " TEXT," + KEY_MAX_PRICE + " INTEGER," + KEY_LOCATION
                + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_REPORTS_TABLE);
    }

    /**
     * upgrades database
     * @param db db to be upgraded
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    /**
     * Add user to the database
     * @param user user to be added
     */
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // user Name
        values.put(KEY_UN, user.getUsername()); // user username
        values.put(KEY_PW, user.getPassword()); // user pw
        values.put(KEY_EMAIL, user.getEmail()); // user email
        values.put(KEY_RATE, 0);
        values.put(KEY_NUM_REP, 0);
        values.put(KEY_ADMIN, 0);
        values.put(KEY_LOCK, 0);

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }
//-------------------------------------------------------------------------------------------------
    /**
     * returns user from id
     * @param id user ID
     * @return user
     */
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID,
                        KEY_NAME, KEY_UN, KEY_PW, KEY_EMAIL, KEY_RATE, KEY_NUM_REP,
                        KEY_ADMIN, KEY_LOCK}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));

        cursor.close();
        db.close();

        return user;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * return a list of all users
     * @return list of all users
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setUsername(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setRating(Integer.parseInt(cursor.getString(5)));
                user.setNumReports(Integer.parseInt(cursor.getString(6)));
                user.setIsLocked(Integer.parseInt(cursor.getString(7)) == 1);
                user.setIsAdmin(Integer.parseInt(cursor.getString(8))==1);

                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        // return user list
        return userList;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * gets all the info from user table
     * @return outputs all the data as a string for Toast!
     */
    public String getAllUsersString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_NAME, KEY_UN, KEY_EMAIL, KEY_PW};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String name = cursor.getString(1);
            String username = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);

            buffer.append(cid).append(") ").append(name).append(" ").append(username).append(" ").append(email).append(" ").append(password).append("\n");
        }
        return buffer.toString();
    }
//-------------------------------------------------------------------------------------------------
    /**
     * get all the items and their details in a list
     * @return String
     */
    public String getAllItemsString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_UID, KEY_ITEM_NAME, KEY_MAX_PRICE, KEY_LOCATION};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_ITEMS, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int requesterID = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String maxPrice = cursor.getString(2);
            String location = cursor.getString(3);

            buffer.append(requesterID).append(" ").append(itemName).append(" ").append(maxPrice).append(" ").append(location).append("\n");
        }
        return buffer.toString();
    }
//-------------------------------------------------------------------------------------------------
    /**
     * get all the items and their details in a list
     * @return String
     */
    public String getAllReportsString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_UID, KEY_ITEM_NAME, KEY_MAX_PRICE, KEY_LOCATION};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_REPORTS, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int requesterID = cursor.getInt(0);
            String itemName = cursor.getString(1);
            String maxPrice = cursor.getString(2);
            String location = cursor.getString(3);

            buffer.append(requesterID).append(" ").append(itemName).append(" ").append(maxPrice).append(" ").append(location).append("\n");
        }
        return buffer.toString();
    }
//-------------------------------------------------------------------------------------------------
    /**
     * get all the friends
     * @return String of friends
     */
    public String getAllFriendsString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID, KEY_BASE, KEY_FRIEND};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_FRIENDS, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int key = cursor.getInt(0);
            String base = cursor.getString(1);
            String friend = cursor.getString(2);

            buffer.append(key).append(" ").append(base).append(" ").append(friend).append("\n");
        }
        return buffer.toString();
    }

//-------------------------------------------------------------------------------------------------

    /**
     * method that gets the name of the items and puts them into a list
     * @return list of strings of the names of the items
     */
    public List<String> getAllItemsRequested() {
        List<String> itemList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_UID, KEY_ITEM_NAME, KEY_MAX_PRICE, KEY_LOCATION};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_ITEMS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
//            int requesterID = cursor.getInt(0);
            String itemName = cursor.getString(1);
//            String maxPrice = cursor.getString(2);
//            String location = cursor.getString(3);
            itemList.add(itemName);

        }
        return itemList;
    }
//-------------------------------------------------------------------------------------------------

    /**
     * method that gets the items and returns them in a list of item request.
     * @return list of items in the itemrequest format
     */
    public List<ItemRequest> getAllItemsRequestedITEM() {
        List<ItemRequest> itemList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemRequest item = new ItemRequest();
                item.setRequestId(cursor.getInt(0));
                item.setRequester(getUser(cursor.getInt(1)));
                item.setName(cursor.getString(2));
                item.setMaxPrice(Integer.parseInt(cursor.getString(3)));
                item.setLocation(cursor.getString(4));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        // return user list
        return itemList;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * method to clear the Database!
     */
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, null, null);
        db.delete(TABLE_ITEMS, null, null);
        db.delete(TABLE_USERS, null, null);
        db.delete(TABLE_REPORTS, null, null);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * method that clears the users and friends tables
     */
    public void clearUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.delete(TABLE_FRIENDS, null, null);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * method that deletes the items requested from the database
     */
    public void clearItemsRequested() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, null, null);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * method that deletes the reports from the database
     */
    public void clearReports() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORTS, null, null);
    }
//-------------------------------------------------------------------------------------------------
    /**
     * updates a given user in the database
     * @param user user to be updated
     * @return number of rows affected
     */
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // Contact Name
        values.put(KEY_UN, user.getUsername()); // Contact username
        values.put(KEY_PW, user.getPassword()); // Contact pw
        values.put(KEY_EMAIL, user.getEmail()); // Contact email
        values.put(KEY_RATE, user.getRating());
        values.put(KEY_NUM_REP, user.getNumReports());
        values.put(KEY_ADMIN, user.getIsAdmin());
        values.put(KEY_LOCK, user.getIsLocked());

        // updating row
        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }
//-------------------------------------------------------------------------------------------------
    /**
     * deleted a single user
     * @param user user to be deleted
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }
//-------------------------------------------------------------------------------------------------

    /**
     * gets count of users
     * @return number of users
     */
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
//-------------------------------------------------------------------------------------------------
    /**
     * checks the username is in the db
     * @param username username to be checked
     * @return true if its in the system
     */
    public boolean checkUser(String username) {
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_UN + " = \'"
                + username + "\'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
//-------------------------------------------------------------------------------------------------
    //Begin Friend table methods

    /**
     * adds a friend to the friend table
     * @param base user with a friend
     * @param friend new friend of the user
     */
    void addFriend(User base, User friend) {
        addFriendOther(friend, base);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BASE, base.getId()); // user Name
        values.put(KEY_FRIEND, friend.getId()); // user username

        // Inserting Row
        db.insert(TABLE_FRIENDS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * method to add the other person as your friend too
     * @param base user with a friend
     * @param friend new friend of the user
     */
    void addFriendOther(User base, User friend) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BASE, base.getId()); // user Name
        values.put(KEY_FRIEND, friend.getId()); // user username

        // Inserting Row
        db.insert(TABLE_FRIENDS, null, values);
        db.close(); // Closing database connection
    }
//-------------------------------------------------------------------------------------------------
    /**
     * returns all friends of a user
     * @param user user to get friends of
     * @return list of friends of user
     */
    public List<User> getAllFriends(User user) {

        int id = user.getId();

        List<User> friendList = new ArrayList<>();
        // select friends of a user from table
        String friendQuery = "SELECT  * FROM " + TABLE_FRIENDS + " WHERE "
                + KEY_BASE + " = \'" + id + "\'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(friendQuery, null);

        // looping through all rows of friends
        if (cursor.moveToFirst()) {
            do {
                //get each friend from the user table
                String friendId = (cursor.getString(2));
                String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
                        + KEY_ID + " = " + friendId;

                SQLiteDatabase userDb = this.getWritableDatabase();
                Cursor userCursor = userDb.rawQuery(selectQuery, null);

                //add friend to list
                if (userCursor.moveToFirst()) {
                    User friend = new User();

                    friend.setId(Integer.parseInt(userCursor.getString(0)));
                    friend.setName(userCursor.getString(1));
                    friend.setUsername(userCursor.getString(2));
                    friend.setPassword(userCursor.getString(3));
                    friend.setEmail(userCursor.getString(4));
                    friend.setRating(Integer.parseInt(userCursor.getString(5)));
                    friend.setNumReports(Integer.parseInt(userCursor.getString(6)));
                    friend.setIsLocked(Integer.parseInt(userCursor.getString(7)) == 1);
                    friend.setIsAdmin(Integer.parseInt(userCursor.getString(8))==1);

                    // Adding friend to list
                    friendList.add(friend);
                }
                userCursor.close();
                userDb.close();

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return friend list
        return friendList;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * method that gets the ids of your friends and puts them in a list of Integers
     * @param user user who you want to get their friend's ids
     * @return list of Integers of the ids
     */
    public List<Integer> getAllFriendsiDs(User user) {

        int id = user.getId();

        List<Integer> friendList = new ArrayList<>();
        // select friends of a user from table
        String friendQuery = "SELECT  * FROM " + TABLE_FRIENDS + " WHERE "
                + KEY_BASE + " = \'" + id + "\'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(friendQuery, null);

        // looping through all rows of friends
        if (cursor.moveToFirst()) {
            do {
                //get each friend from the user table
                String friendId = (cursor.getString(2));
                String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE "
                        + KEY_ID + " = " + friendId;

                SQLiteDatabase userDb = this.getWritableDatabase();
                Cursor userCursor = userDb.rawQuery(selectQuery, null);

                //add friend to list
                if (userCursor.moveToFirst()) {
                    User friend = new User();

                    friend.setId(Integer.parseInt(userCursor.getString(0)));
                    friend.setName(userCursor.getString(1));
                    friend.setUsername(userCursor.getString(2));
                    friend.setPassword(userCursor.getString(3));
                    friend.setEmail(userCursor.getString(4));
                    friend.setRating(Integer.parseInt(userCursor.getString(5)));
                    friend.setNumReports(Integer.parseInt(userCursor.getString(6)));
                    friend.setIsLocked(Integer.parseInt(userCursor.getString(7)) == 1);
                    friend.setIsAdmin(Integer.parseInt(userCursor.getString(8))==1);

                    // Adding friend to list
                    friendList.add(friend.getId());
                }
                userCursor.close();
                userDb.close();

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return friend list
        return friendList;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * deleted a row from the friend database
     * @param user user to be deleted
     * @param friend friend to be deleted
     */
    public void deleteFriend(User user, User friend) {
        deleteFriendOther(friend, user);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, KEY_BASE + " = ? AND " + KEY_FRIEND + " = ?",
                new String[] { String.valueOf(user.getId()), String.valueOf(friend.getId()) });
        db.close();
    }

    /**
     * deleted a row from the friend database for the other friend
     * @param user user to be deleted
     * @param friend friend to be deleted
     */
    void deleteFriendOther(User user, User friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, KEY_BASE + " = ? AND " + KEY_FRIEND + " = ?",
                new String[] { String.valueOf(user.getId()), String.valueOf(friend.getId()) });
        db.close();
    }
//-------------------------------------------------------------------------------------------------
    //begin item request table methods

    /**
     * Adds an item request to the database
     * @param item the item request to be added
     */
    void addItemRequest(ItemRequest item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, item.getRequester().getId()); // user Name
        values.put(KEY_ITEM_NAME, item.getName()); // user username
        values.put(KEY_MAX_PRICE, item.getMaxPrice());
        values.put(KEY_LOCATION, item.getLocation());

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }
//-------------------------------------------------------------------------------------------------
    /**
     * returns an item request given the id
     * @param id the id of the item request
     * @return the item request
     */
    public ItemRequest getItemRequest(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEMS, new String[] { KEY_ID,
                        KEY_UID, KEY_ITEM_NAME, KEY_MAX_PRICE, KEY_LOCATION}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        ItemRequest item = new ItemRequest(getUser(Integer.parseInt(cursor.getString(1))),
                cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4));

        item.setRequestId(Integer.parseInt(cursor.getString(0)));

        cursor.close();
        db.close();

        return item;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * returns a list of item requests
     * @param user the user that posted the item requests
     * @return a list of item requests
     */
    public List<ItemRequest> getRequestsByUser(User user) {

        int id = user.getId();

        List<ItemRequest> itemList = new ArrayList<>();
        // select friends of a user from table
        String itemQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE "
                + KEY_UID + " = \'" + id + "\'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        // looping through all rows of friends
        if (cursor.moveToFirst()) {
            do {
                ItemRequest ir = new ItemRequest();

                ir.setRequestId(Integer.parseInt(cursor.getString(0)));
                ir.setRequester(user);
                ir.setName(cursor.getString(2));
                ir.setMaxPrice(Integer.parseInt(cursor.getString(3)));
                ir.setLocation(cursor.getString(4));

                itemList.add(ir);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return friend list
        return itemList;
    }
//-------------------------------------------------------------------------------------------------
    /**
     * Deletes the item request from the database
     * @param id the id of the request
     */
    public void deleteRequest(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }
//-------------------------------------------------------------------------------------------------

    /**
     * Adds an item report to the database
     * @param item the item request to be added
     */
    void addItemReport(ItemRequest item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, item.getRequester().getId()); // user Name
        values.put(KEY_ITEM_NAME, item.getName()); // item name
        values.put(KEY_MAX_PRICE, item.getMaxPrice());
        values.put(KEY_LOCATION, item.getLocation());

        // Inserting Row
        db.insert(TABLE_REPORTS, null, values);
        db.close(); // Closing database connection
    }
//-------------------------------------------------------------------------------------------------

    /**
     * method that gets the name of the items and puts them into a list
     * @return list of strings of the names of the items
     */
    public List<String> getAllReports() {
        List<String> itemList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_UID, KEY_ITEM_NAME, KEY_MAX_PRICE, KEY_LOCATION};
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_REPORTS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
//            int requesterID = cursor.getInt(0);
            String itemName = cursor.getString(1);
//            String maxPrice = cursor.getString(2);
//            String location = cursor.getString(3);
            itemList.add(itemName);

        }
        return itemList;
    }
//-------------------------------------------------------------------------------------------------

    /**
     * method that gets the items and returns them in a list of item request.
     * @return list of items in the itemrequest format
     */
    public List<ItemRequest> getAllReportsITEM() {
        List<ItemRequest> itemList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORTS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemRequest item = new ItemRequest();
                item.setRequestId(Integer.parseInt(cursor.getString(0)));
                item.setRequester(getUser(1));
                item.setName(cursor.getString(2));
                item.setMaxPrice(Integer.parseInt(cursor.getString(3)));
                item.setLocation(cursor.getString(4));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        // return user list
        return itemList;
    }
}



