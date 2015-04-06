package com.beastcode;

public class ItemRequest {

    private int requestId;
    private User requester;
    private String name;
    private int maxPrice;
    private String location;

    /**
     * when you want to request an Item, these are an item's properties
     */
    public ItemRequest() {
    }

    /**
     * Item request method
     * @param user user who is requesting
     * @param name name
     * @param price price
     * @param location location
     */
    public ItemRequest(User user, String name, int price, String location) {
        this.requester = user;
        this.name = name;
        this.maxPrice = price;
        this.location = location;
    }

    /**
     * getter for the id
     * @return int of the id
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * setter method for the id
     * @param id of the person asking
     */
    public void setRequestId(int id) {
        this.requestId = id;
    }

    /**
     * getter for the requestor
     * @return user who is requesting
     */
    public User getRequester() {
        return requester;
    }

    /**
     * setter for the user who is requesting
     * @param user user who is requesting
     */
    public void setRequester(User user) {
        this.requester = user;
    }

    /**
     * getter for the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name
     * @param name that you want to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for the maxprice you want to pay
     * @return max price to pay
     */
    public int getMaxPrice() {
        return maxPrice;
    }

    /**
     * setter for the max price willing to pay
     * @param price max price you will pay
     */
    public void setMaxPrice(int price) {
        this.maxPrice = price;
    }

    /**
     * getter for the location of the item
     * @return location of the item.
     */
    public String getLocation() {
        return location;
    }

    /** setter for the location
     * @param loc location
     */
    public void setLocation(String loc) {
        this.location = loc;
    }

}