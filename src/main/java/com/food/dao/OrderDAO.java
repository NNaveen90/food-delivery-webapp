package com.food.dao;

import com.food.model.Order;
import com.food.model.OrderItem;
import com.food.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO 
{

    // ---- Place a new order ----
    public int placeOrder(Order order) throws SQLException 
    {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert order
            String orderSql = "INSERT INTO orders (user_id, restaurant_id, total_amount, status, delivery_address) VALUES (?,?,?,?,?)";
            int generatedId = -1;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) 
            {
                ps.setInt(1,    order.getUserId());
                ps.setInt(2,    order.getRestaurantId());
                ps.setDouble(3, order.getTotalAmount());
                ps.setString(4, "PENDING");
                ps.setString(5, order.getDeliveryAddress());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) 
                {
                    if (keys.next()) generatedId = keys.getInt(1);
                }
            }

            // Insert order items
            String itemSql = "INSERT INTO order_items (order_id, menu_item_id, quantity, unit_price) VALUES (?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) 
            {
                for (OrderItem item : order.getItems()) 
                {
                    ps.setInt(1,    generatedId);
                    ps.setInt(2,    item.getMenuItemId());
                    ps.setInt(3,    item.getQuantity());
                    ps.setDouble(4, item.getUnitPrice());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return generatedId;
        }
        catch (SQLException e) 
        {
            if (conn != null) conn.rollback();
            throw e;
        }
        finally 
        {
            DBConnection.closeConnection(conn);
        }
    }

    // ---- Get orders by user ----
    public List<Order> getOrdersByUser(int userId) throws SQLException 
    {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, r.name AS restaurant_name " +
                     "FROM orders o JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE o.user_id=? ORDER BY o.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) list.add(mapOrder(rs));
            }
        }
        return list;
    }

    // ---- Get all orders (Admin) ----
    public List<Order> getAllOrders() throws SQLException 
    {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.name AS user_name, r.name AS restaurant_name " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "ORDER BY o.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                Order order = mapOrder(rs);
                order.setUserName(rs.getString("user_name"));
                list.add(order);
            }
        }
        return list;
    }

    // ---- Get order by ID (with items) ----
    public Order getOrderById(int orderId) throws SQLException 
    {
        Order order = null;
        String sql = "SELECT o.*, u.name AS user_name, r.name AS restaurant_name " +
                     "FROM orders o " +
                     "JOIN users u ON o.user_id = u.id " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "WHERE o.id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) 
            {
                if (rs.next()) 
                {
                    order = mapOrder(rs);
                    order.setUserName(rs.getString("user_name"));
                }
            }
        }
        if (order != null) order.setItems(getOrderItems(orderId));
        return order;
    }

    // ---- Get order items ----
    public List<OrderItem> getOrderItems(int orderId) throws SQLException 
    {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, m.name AS menu_item_name " +
                     "FROM order_items oi JOIN menu_items m ON oi.menu_item_id = m.id " +
                     "WHERE oi.order_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) 
                {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    // ---- Update order status (Admin) ----
    public boolean updateOrderStatus(int orderId, String status) throws SQLException 
    {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, status);
            ps.setInt(2,    orderId);
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Cancel order (Customer) ----
    public boolean cancelOrder(int orderId, int userId) throws SQLException 
    {
        String sql = "UPDATE orders SET status='CANCELLED' WHERE id=? AND user_id=? AND status='PENDING'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setInt(1, orderId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    // ---- Helper ----
    private Order mapOrder(ResultSet rs) throws SQLException 
    {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setUserId(rs.getInt("user_id"));
        o.setRestaurantId(rs.getInt("restaurant_id"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setStatus(rs.getString("status"));
        o.setDeliveryAddress(rs.getString("delivery_address"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        o.setUpdatedAt(rs.getTimestamp("updated_at"));
        try 
        {
        	o.setRestaurantName(rs.getString("restaurant_name"));
        }
        catch (SQLException ignored) {}
        return o;
    }
}
