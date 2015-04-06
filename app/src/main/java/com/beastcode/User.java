package com.beastcode;

/**
 * Created by Adam on 2/26/2015.
 */
public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private int rating;
    private int numReports;
    private boolean isLocked;
    private boolean isAdmin;

    public static User currentUser;

    public User() {
    }

    /**
     * Constructs the user from the registration screen
     * @param name name
     * @param username username
     * @param password password
     */
    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        rating = 0;
        numReports = 0;
    }

    /**
     * constructs a user from the database
     * @param id
     * @param name
     * @param username
     * @param pw
     * @param email
     * @param rating
     * @param numReports
     * @param isLocked
     * @param isAdmin
     */
    public User(int id, String name, String username, String pw, String email, int rating,
                int numReports, int isLocked, int isAdmin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = pw;
        this.email = email;
        this.numReports = numReports;
        this.rating = rating;
        this.isLocked = isLocked == 1;
        this.isAdmin = isAdmin == 1;
    }

    /**
     * gets the id
     * @return  id
     */
    public int getId() {
        return id;
    }

    /**
     * get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * gets the email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the name
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the username
     * @param un username to be set
     */
    public void setUsername(String un) {
        this.username = un;
    }

    /**
     * sets the password
     * @param pw password to be set
     */
    public void setPassword(String pw) {
        this.password = pw;
    }

    /**
     * sets the email
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * sets the id
     * @param id id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get the rating
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * sets the rating
     * @param rating rating to be set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * gets the number of reports generated
     * @return number of reports generated
     */
    public int getNumReports() {
        return numReports;
    }

    /**
     * sets the number of reports generated
     * @param numReports number of reports generated
     */
    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }

    /**
     * gets the lock status of the user
     * @return lock status
     */
    public boolean getIsLocked() {
        return isLocked;
    }

    /**
     * sets the lock status
     * @param isLocked lock status
     */
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * gets the admin status
     * @return admin status
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * sets the admin status
     * @param isAdmin admin status
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object user) {
        return user instanceof User && ((User)user).getUsername().equals(this.username);
    }
}
