package com.food.dao;

import com.food.model.Restaurant;
import com.food.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO 
{

    // ---- Get all active restaurants ----
    public List<Restaurant> getAllRestaurants() throws SQLException 
    {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE is_active=1 ORDER BY name";
        try (Connection conn = DBConnection.getConnection();
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(sql)) 
        {
            while (rs.next()) list.add(mapRestaurant(rs));
        }
        return list;
    }

    // ---- Get restaurant by ID ----
    public Restaurant getRestaurantById(int id) throws SQLException 
    {
        String sql = "SELECT * FROM restaurants WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) return mapRestaurant(rs);
            }
        }
        return null;
    }

    // ---- Search restaurants by name or cuisine ----
    public List<Restaurant> searchRestaurants(String keyword) throws SQLException 
    {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE is_active=1 AND (name LIKE ? OR cuisine LIKE ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) list.add(mapRestaurant(rs));
            }
        }
        return list;
    }

    // ---- Add restaurant (Admin) ----
    public boolean addRestaurant(Restaurant r) throws SQLException 
    {
        String sql = "INSERT INTO restaurants (name, address, phone, cuisine, rating) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getPhone());
            ps.setString(4, r.getCuisine());
            ps.setDouble(5, r.getRating());
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Update restaurant (Admin) ----
    public boolean updateRestaurant(Restaurant r) throws SQLException 
    {
        String sql = "UPDATE restaurants SET name=?, address=?, phone=?, cuisine=?, rating=?, is_active=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getPhone());
            ps.setString(4, r.getCuisine());
            ps.setDouble(5, r.getRating());
            ps.setInt(6,    r.isActive() ? 1 : 0);
            ps.setInt(7,    r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Delete restaurant (Admin) ----
    public boolean deleteRestaurant(int id) throws SQLException 
    {
        String sql = "UPDATE restaurants SET is_active=0 WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Helper ----
    private Restaurant mapRestaurant(ResultSet rs) throws SQLException 
    {
        Restaurant r = new Restaurant();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setAddress(rs.getString("address"));
        r.setPhone(rs.getString("phone"));
        r.setCuisine(rs.getString("cuisine"));
        r.setRating(rs.getDouble("rating"));
        r.setActive(rs.getInt("is_active") == 1);
        return r;
    }
}
