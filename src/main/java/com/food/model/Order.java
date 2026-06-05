package com.food.model;

import java.sql.Timestamp;
import java.util.List;

public class Order 
{
    private int       id;
    private int       userId;
    private int       restaurantId;
    private double    totalAmount;
    private String    status;
    private String    deliveryAddress;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Convenience fields (joined from other tables)
    private String        userName;
    private String        restaurantName;
    private List<OrderItem> items;

    public Order() {}

    // Getters
    public int       getId()              { return id; }
    public int       getUserId()          { return userId; }
    public int       getRestaurantId()    { return restaurantId; }
    public double    getTotalAmount()     { return totalAmount; }
    public String    getStatus()          { return status; }
    public String    getDeliveryAddress() { return deliveryAddress; }
    public Timestamp getCreatedAt()       { return createdAt; }
    public Timestamp getUpdatedAt()       { return updatedAt; }
    public String    getUserName()        { return userName; }
    public String    getRestaurantName()  { return restaurantName; }
    public List<OrderItem> getItems()     { return items; }

    // Setters
    public void setId(int id)                          { this.id              = id; }
    public void setUserId(int userId)                  { this.userId          = userId; }
    public void setRestaurantId(int rid)               { this.restaurantId    = rid; }
    public void setTotalAmount(double total)           { this.totalAmount     = total; }
    public void setStatus(String status)               { this.status          = status; }
    public void setDeliveryAddress(String addr)        { this.deliveryAddress = addr; }
    public void setCreatedAt(Timestamp ts)             { this.createdAt       = ts; }
    public void setUpdatedAt(Timestamp ts)             { this.updatedAt       = ts; }
    public void setUserName(String userName)           { this.userName        = userName; }
    public void setRestaurantName(String rname)        { this.restaurantName  = rname; }
    public void setItems(List<OrderItem> items)        { this.items           = items; }
}
