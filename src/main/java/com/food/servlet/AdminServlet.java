package com.food.servlet;

import com.food.dao.*;
import com.food.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet 
{

    private final UserDAO       userDAO       = new UserDAO();
    private final RestaurantDAO restaurantDAO = new RestaurantDAO();
    private final MenuItemDAO   menuItemDAO   = new MenuItemDAO();
    private final OrderDAO      orderDAO      = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {

        if (!isAdmin(req)) 
        {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        String path = req.getPathInfo();
        if (path == null) path = "/dashboard";

        switch (path) {
            case "/dashboard"          -> dashboard(req, resp);
            case "/users"              -> listUsers(req, resp);
            case "/restaurants"        -> listRestaurants(req, resp);
            case "/addRestaurant"      -> req.getRequestDispatcher("/WEB-INF/views/admin/addRestaurant.jsp").forward(req, resp);
            case "/editRestaurant"     -> editRestaurant(req, resp);
            case "/deleteRestaurant"   -> deleteRestaurant(req, resp);
            case "/menu"               -> listMenu(req, resp);
            case "/addMenuItem"        -> showAddMenuItem(req, resp);
            case "/editMenuItem"       -> editMenuItem(req, resp);
            case "/deleteMenuItem"     -> deleteMenuItem(req, resp);
            case "/orders"             -> listOrders(req, resp);
            case "/orderDetail"        -> adminOrderDetail(req, resp);
            default                    -> dashboard(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {

        if (!isAdmin(req)) 
        {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }

        String path = req.getPathInfo();
        if (path == null) path = "/dashboard";

        switch (path) 
        {
            case "/saveRestaurant"  -> saveRestaurant(req, resp);
            case "/saveMenuItem"    -> saveMenuItem(req, resp);
            case "/updateStatus"    -> updateOrderStatus(req, resp);
            default                 -> dashboard(req, resp);
        }
    }

    // ---- Dashboard ----
    private void dashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        try 
        {
            req.setAttribute("userCount",       userDAO.getAllUsers().size());
            req.setAttribute("restaurantCount", restaurantDAO.getAllRestaurants().size());
            req.setAttribute("orderCount",      orderDAO.getAllOrders().size());
            req.setAttribute("recentOrders",    orderDAO.getAllOrders().stream().limit(5).toList());
        } 
        catch (Exception e) 
        {
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }

    // ---- Users ----
    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException 
    {
        try 
        {
            req.setAttribute("users", userDAO.getAllUsers());
        }
        catch (Exception e) 
        { 
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(req, resp);
    }

    // ---- Restaurants ----
    private void listRestaurants(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        try 
        {
            req.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
        }
        catch (Exception e) 
        {
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/restaurants.jsp").forward(req, resp);
    }

    private void editRestaurant(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int id = Integer.parseInt(req.getParameter("id"));
        try 
        {
            req.setAttribute("restaurant", restaurantDAO.getRestaurantById(id));
        }
        catch (Exception e) 
        { 
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/editRestaurant.jsp").forward(req, resp);
    }

    private void saveRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        String idParam = req.getParameter("id");
        try 
        {
            Restaurant r = new Restaurant();
            r.setName(req.getParameter("name"));
            r.setAddress(req.getParameter("address"));
            r.setPhone(req.getParameter("phone"));
            r.setCuisine(req.getParameter("cuisine"));
            r.setRating(Double.parseDouble(req.getParameter("rating")));
            r.setActive(true);
            if (idParam != null && !idParam.isEmpty()) 
            {
                r.setId(Integer.parseInt(idParam));
                restaurantDAO.updateRestaurant(r);
            }
            else 
            {
                restaurantDAO.addRestaurant(r);
            }
        }
        catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/admin/restaurants");
    }

    private void deleteRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        int id = Integer.parseInt(req.getParameter("id"));
        try 
        { 
        	restaurantDAO.deleteRestaurant(id); 
        }
        catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/admin/restaurants");
    }

    // ---- Menu Items ----
    private void listMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
        try 
        {
            req.setAttribute("menuItems",   menuItemDAO.getMenuByRestaurant(restaurantId));
            req.setAttribute("restaurant",  restaurantDAO.getRestaurantById(restaurantId));
        } 
        catch (Exception e) 
        { 
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/menu.jsp").forward(req, resp);
    }

    private void showAddMenuItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        req.setAttribute("restaurantId", req.getParameter("restaurantId"));
        req.getRequestDispatcher("/WEB-INF/views/admin/addMenuItem.jsp").forward(req, resp);
    }

    private void editMenuItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int id = Integer.parseInt(req.getParameter("id"));
        try 
        {
            req.setAttribute("menuItem", menuItemDAO.getMenuItemById(id));
        } 
        catch (Exception e) 
        {
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/editMenuItem.jsp").forward(req, resp);
    }

    private void saveMenuItem(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        String idParam       = req.getParameter("id");
        int    restaurantId  = Integer.parseInt(req.getParameter("restaurantId"));
        try {
            MenuItem m = new MenuItem();
            m.setRestaurantId(restaurantId);
            m.setName(req.getParameter("name"));
            m.setDescription(req.getParameter("description"));
            m.setPrice(Double.parseDouble(req.getParameter("price")));
            m.setCategory(req.getParameter("category"));
            m.setAvailable(true);
            if (idParam != null && !idParam.isEmpty()) 
            {
                m.setId(Integer.parseInt(idParam));
                menuItemDAO.updateMenuItem(m);
            }
            else 
            {
                menuItemDAO.addMenuItem(m);
            }
        } catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/admin/menu?restaurantId=" + restaurantId);
    }

    private void deleteMenuItem(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        int id           = Integer.parseInt(req.getParameter("id"));
        int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
        try 
        {
        	menuItemDAO.deleteMenuItem(id); 
        }
        catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/admin/menu?restaurantId=" + restaurantId);
    }

    // ---- Orders ----
    private void listOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        try 
        {
            req.setAttribute("orders", orderDAO.getAllOrders());
        } 
        catch (Exception e) 
        {
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(req, resp);
    }

    private void adminOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        int orderId = Integer.parseInt(req.getParameter("id"));
        try 
        {
            req.setAttribute("order", orderDAO.getOrderById(orderId));
        } 
        catch (Exception e) 
        {
        	req.setAttribute("error", e.getMessage()); 
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/orderDetail.jsp").forward(req, resp);
    }

    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException 
    {
        int    orderId = Integer.parseInt(req.getParameter("orderId"));
        String status  = req.getParameter("status");
        try 
        {
        	orderDAO.updateOrderStatus(orderId, status); 
        } 
        catch (Exception ignored) {}
        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }

    // ---- Helper ----
    private boolean isAdmin(HttpServletRequest req) 
    {
        HttpSession session = req.getSession(false);
        return session != null && "ADMIN".equals(session.getAttribute("role"));
    }
}
