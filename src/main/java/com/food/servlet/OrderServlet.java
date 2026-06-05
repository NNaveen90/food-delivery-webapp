package com.food.servlet;

import com.food.dao.MenuItemDAO;
import com.food.dao.OrderDAO;
import com.food.model.MenuItem;
import com.food.model.Order;
import com.food.model.OrderItem;
import com.food.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private final OrderDAO    orderDAO    = new OrderDAO();
    private final MenuItemDAO menuItemDAO = new MenuItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "myorders";

        switch (action) {
            case "myorders"  -> myOrders(req, resp);
            case "detail"    -> orderDetail(req, resp);
            case "cancel"    -> cancelOrder(req, resp);
            case "checkout"  -> showCheckout(req, resp);
            default          -> resp.sendRedirect(req.getContextPath() + "/order?action=myorders");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        String action = req.getParameter("action");
        if ("place".equals(action)) placeOrder(req, resp);
        else resp.sendRedirect(req.getContextPath() + "/order?action=myorders");
    }

    // ---- My Orders ----
    private void myOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = getLoggedInUser(req);
        try {
            List<Order> orders = orderDAO.getOrdersByUser(user.getId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/myorders.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/myorders.jsp").forward(req, resp);
        }
    }

    // ---- Order Detail ----
    private void orderDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("id"));
        try {
            Order order = orderDAO.getOrderById(orderId);
            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/orderdetail.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/myorders.jsp").forward(req, resp);
        }
    }

    // ---- Show Checkout Page ----
    private void showCheckout(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
            return;
        }
        String restaurantId = req.getParameter("restaurantId");
        req.setAttribute("restaurantId", restaurantId);

        List<OrderItem> cartItems = new ArrayList<>();
        double total = 0;
        try {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                MenuItem   item      = menuItemDAO.getMenuItemById(entry.getKey());
                OrderItem  cartItem  = new OrderItem();
                cartItem.setMenuItemId(item.getId());
                cartItem.setMenuItemName(item.getName());
                cartItem.setUnitPrice(item.getPrice());
                cartItem.setQuantity(entry.getValue());
                cartItems.add(cartItem);
                total += item.getPrice() * entry.getValue();
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        req.setAttribute("cartItems",    cartItems);
        req.setAttribute("totalAmount",  total);
        req.setAttribute("restaurantId", restaurantId);
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    // ---- Place Order ----
    private void placeOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = getLoggedInUser(req);
        HttpSession session = req.getSession();

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
            return;
        }

        String deliveryAddress = req.getParameter("deliveryAddress");
        int    restaurantId    = Integer.parseInt(req.getParameter("restaurantId"));

        try {
            List<OrderItem> items = new ArrayList<>();
            double total = 0;
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                MenuItem  mi   = menuItemDAO.getMenuItemById(entry.getKey());
                OrderItem item = new OrderItem(0, mi.getId(), entry.getValue(), mi.getPrice());
                items.add(item);
                total += mi.getPrice() * entry.getValue();
            }

            Order order = new Order();
            order.setUserId(user.getId());
            order.setRestaurantId(restaurantId);
            order.setTotalAmount(total);
            order.setDeliveryAddress(deliveryAddress);
            order.setItems(items);

            int orderId = orderDAO.placeOrder(order);
            session.removeAttribute("cart");
            resp.sendRedirect(req.getContextPath() + "/order?action=detail&id=" + orderId + "&msg=placed");
        } catch (Exception e) {
            req.setAttribute("error", "Order failed: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
        }
    }

    // ---- Cancel Order ----
    private void cancelOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user    = getLoggedInUser(req);
        int  orderId = Integer.parseInt(req.getParameter("id"));
        try {
            orderDAO.cancelOrder(orderId, user.getId());
        } catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/order?action=myorders");
    }

    // ---- Helpers ----
    private boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    private User getLoggedInUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }
}
