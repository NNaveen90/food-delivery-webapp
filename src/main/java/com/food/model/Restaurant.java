package com.food.model;

public class Restaurant 
{
    private int    id;
    private String name;
    private String address;
    private String phone;
    private String cuisine;
    private double rating;
    private boolean active;

    public Restaurant() {}

    public Restaurant(int id, String name, String address, String phone,
                      String cuisine, double rating, boolean active) 
    {
        this.id      = id;
        this.name    = name;
        this.address = address;
        this.phone   = phone;
        this.cuisine = cuisine;
        this.rating  = rating;
        this.active  = active;
    }

    // Getters
    public int     getId()      { return id; }
    public String  getName()    { return name; }
    public String  getAddress() { return address; }
    public String  getPhone()   { return phone; }
    public String  getCuisine() { return cuisine; }
    public double  getRating()  { return rating; }
    public boolean isActive()   { return active; }

    // Setters
    public void setId(int id)              { this.id      = id; }
    public void setName(String name)       { this.name    = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone)     { this.phone   = phone; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }
    public void setRating(double rating)   { this.rating  = rating; }
    public void setActive(boolean active)  { this.active  = active; }
}
