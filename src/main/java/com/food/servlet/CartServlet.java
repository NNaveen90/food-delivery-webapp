package com.food.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getSession(false) == null || req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add"    -> addToCart(req, resp);
            case "remove" -> removeFromCart(req, resp);
            case "clear"  -> clearCart(req, resp);
            default       -> resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
        }
    }

    // ---- Add item to cart ----
    private void addToCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int menuItemId   = Integer.parseInt(req.getParameter("menuItemId"));
        int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
        int quantity     = 1;
        try { quantity = Integer.parseInt(req.getParameter("quantity")); } catch (Exception ignored) {}

        HttpSession session = req.getSession();

        // If cart exists for a different restaurant, clear it first
        Integer existingRestaurant = (Integer) session.getAttribute("cartRestaurantId");
        if (existingRestaurant != null && existingRestaurant != restaurantId) {
            session.removeAttribute("cart");
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        cart.merge(menuItemId, quantity, Integer::sum);
        session.setAttribute("cart",              cart);
        session.setAttribute("cartRestaurantId",  restaurantId);

        resp.sendRedirect(req.getContextPath() + "/restaurant?action=menu&id=" + restaurantId);
    }

    // ---- Remove item from cart ----
    private void removeFromCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int menuItemId   = Integer.parseInt(req.getParameter("menuItemId"));
        int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(menuItemId);
            if (cart.isEmpty()) session.removeAttribute("cartRestaurantId");
        }
        resp.sendRedirect(req.getContextPath() + "/restaurant?action=menu&id=" + restaurantId);
    }

    // ---- Clear cart ----
    private void clearCart(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.getSession().removeAttribute("cart");
        req.getSession().removeAttribute("cartRestaurantId");
        resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
    }
}
