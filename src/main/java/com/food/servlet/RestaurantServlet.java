package com.food.servlet;

import com.food.dao.MenuItemDAO;
import com.food.dao.RestaurantDAO;
import com.food.model.MenuItem;
import com.food.model.Restaurant;
import com.food.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/restaurant")
public class RestaurantServlet extends HttpServlet {

    private final RestaurantDAO restaurantDAO = new RestaurantDAO();
    private final MenuItemDAO   menuItemDAO   = new MenuItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list"   -> listRestaurants(req, resp);
            case "menu"   -> showMenu(req, resp);
            case "search" -> searchRestaurants(req, resp);
            default       -> resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
        }
    }

    // ---- List all restaurants ----
    private void listRestaurants(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();
            req.setAttribute("restaurants", restaurants);
            req.getRequestDispatcher("/WEB-INF/views/restaurants.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/restaurants.jsp").forward(req, resp);
        }
    }

    // ---- Show menu for a restaurant ----
    private void showMenu(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            Restaurant       restaurant = restaurantDAO.getRestaurantById(id);
            List<MenuItem>   menuItems  = menuItemDAO.getMenuByRestaurant(id);
            req.setAttribute("restaurant", restaurant);
            req.setAttribute("menuItems",  menuItems);
            req.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/restaurants.jsp").forward(req, resp);
        }
    }

    // ---- Search restaurants ----
    private void searchRestaurants(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";
        try {
            List<Restaurant> restaurants = restaurantDAO.searchRestaurants(keyword.trim());
            req.setAttribute("restaurants", restaurants);
            req.setAttribute("keyword",     keyword);
            req.getRequestDispatcher("/WEB-INF/views/restaurants.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/restaurants.jsp").forward(req, resp);
        }
    }
}
