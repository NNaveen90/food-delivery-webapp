package com.food.dao;

import com.food.model.MenuItem;
import com.food.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO 
{

    // ---- Get all menu items for a restaurant ----
    public List<MenuItem> getMenuByRestaurant(int restaurantId) throws SQLException 
    {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE restaurant_id=? AND is_available=1 ORDER BY category, name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, restaurantId);
            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) list.add(mapItem(rs));
            }
        }
        return list;
    }

    // ---- Get menu item by ID ----
    public MenuItem getMenuItemById(int id) throws SQLException 
    {
        String sql = "SELECT * FROM menu_items WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) return mapItem(rs);
            }
        }
        return null;
    }

    // ---- Add menu item (Admin) ----
    public boolean addMenuItem(MenuItem item) throws SQLException 
    {
        String sql = "INSERT INTO menu_items (restaurant_id, name, description, price, category) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1,    item.getRestaurantId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getCategory());
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Update menu item (Admin) ----
    public boolean updateMenuItem(MenuItem item) throws SQLException 
    {
        String sql = "UPDATE menu_items SET name=?, description=?, price=?, category=?, is_available=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getCategory());
            ps.setInt(5,    item.isAvailable() ? 1 : 0);
            ps.setInt(6,    item.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Delete menu item (Admin) ----
    public boolean deleteMenuItem(int id) throws SQLException 
    {
        String sql = "UPDATE menu_items SET is_available=0 WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Helper ----
    private MenuItem mapItem(ResultSet rs) throws SQLException 
    {
        MenuItem m = new MenuItem();
        m.setId(rs.getInt("id"));
        m.setRestaurantId(rs.getInt("restaurant_id"));
        m.setName(rs.getString("name"));
        m.setDescription(rs.getString("description"));
        m.setPrice(rs.getDouble("price"));
        m.setCategory(rs.getString("category"));
        m.setAvailable(rs.getInt("is_available") == 1);
        return m;
    }
}
