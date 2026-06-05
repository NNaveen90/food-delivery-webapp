package com.food.model;

public class MenuItem 
{
    private int    id;
    private int    restaurantId;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;

    public MenuItem() {}

    public MenuItem(int id, int restaurantId, String name, String description, double price, String category, boolean available) 
    {
        this.id           = id;
        this.restaurantId = restaurantId;
        this.name         = name;
        this.description  = description;
        this.price        = price;
        this.category     = category;
        this.available    = available;
    }

    // Getters
    public int     getId()           { return id; }
    public int     getRestaurantId() { return restaurantId; }
    public String  getName()         { return name; }
    public String  getDescription()  { return description; }
    public double  getPrice()        { return price; }
    public String  getCategory()     { return category; }
    public boolean isAvailable()     { return available; }

    // Setters
    public void setId(int id)                     { this.id           = id; }
    public void setRestaurantId(int rid)          { this.restaurantId = rid; }
    public void setName(String name)              { this.name         = name; }
    public void setDescription(String desc)       { this.description  = desc; }
    public void setPrice(double price)            { this.price        = price; }
    public void setCategory(String category)      { this.category     = category; }
    public void setAvailable(boolean available)   { this.available    = available; }
}
