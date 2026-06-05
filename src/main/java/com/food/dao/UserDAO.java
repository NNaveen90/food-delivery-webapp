package com.food.dao;

import com.food.model.User;
import com.food.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO 
{

    // ---- Register a new user ----
    public boolean registerUser(User user) throws SQLException 
    {
        String sql = "INSERT INTO users (name, email, password, phone, address, role) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, "CUSTOMER");
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Login (email + password) ----
    public User login(String email, String password) throws SQLException 
    {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) return mapUser(rs);
            }
        }
        return null;
    }

    // ---- Get user by ID ----
    public User getUserById(int id) throws SQLException 
    {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) return mapUser(rs);
            }
        }
        return null;
    }

    // ---- Get all users (Admin) ----
    public List<User> getAllUsers() throws SQLException 
    {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(sql)) 
        {
            while (rs.next()) list.add(mapUser(rs));
        }
        return list;
    }

    // ---- Update user profile ----
    public boolean updateUser(User user) throws SQLException 
    {
        String sql = "UPDATE users SET name=?, phone=?, address=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getAddress());
            ps.setInt(4,    user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Check if email already exists ----
    public boolean emailExists(String email) throws SQLException 
    {
        String sql = "SELECT id FROM users WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) 
            {
                return rs.next();
            }
        }
    }

    // ---- Helper ----
    private User mapUser(ResultSet rs) throws SQLException 
    {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getString("phone"));
        u.setAddress(rs.getString("address"));
        u.setRole(rs.getString("role"));
        return u;
    }
}
