package com.food.model;

public class OrderItem 
{
    private int    id;
    private int    orderId;
    private int    menuItemId;
    private int    quantity;
    private double unitPrice;

    // Convenience
    private String menuItemName;

    public OrderItem() {}

    public OrderItem(int orderId, int menuItemId, int quantity, double unitPrice) 
    {
        this.orderId    = orderId;
        this.menuItemId = menuItemId;
        this.quantity   = quantity;
        this.unitPrice  = unitPrice;
    }

    // Getters
    public int    getId()           { return id; }
    public int    getOrderId()      { return orderId; }
    public int    getMenuItemId()   { return menuItemId; }
    public int    getQuantity()     { return quantity; }
    public double getUnitPrice()    { return unitPrice; }
    public String getMenuItemName() { return menuItemName; }
    public double getSubtotal()     { return quantity * unitPrice; }

    // Setters
    public void setId(int id)                    { this.id           = id; }
    public void setOrderId(int orderId)          { this.orderId      = orderId; }
    public void setMenuItemId(int mid)           { this.menuItemId   = mid; }
    public void setQuantity(int quantity)        { this.quantity     = quantity; }
    public void setUnitPrice(double unitPrice)   { this.unitPrice    = unitPrice; }
    public void setMenuItemName(String name)     { this.menuItemName = name; }
}
