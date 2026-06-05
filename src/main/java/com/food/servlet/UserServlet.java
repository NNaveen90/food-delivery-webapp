package com.food.servlet;

import com.food.dao.UserDAO;
import com.food.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "register" -> req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            case "login"    -> req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            case "logout"   -> {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
            case "profile"  -> {
                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute("user") == null) {
                    resp.sendRedirect(req.getContextPath() + "/user?action=login");
                    return;
                }
                req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
            }
            default -> resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "register" -> handleRegister(req, resp);
            case "login"    -> handleLogin(req, resp);
            case "update"   -> handleUpdate(req, resp);
            default         -> resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ---- Register ----
    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name     = req.getParameter("name").trim();
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();
        String phone    = req.getParameter("phone").trim();
        String address  = req.getParameter("address").trim();

        try {
            if (userDAO.emailExists(email)) {
                req.setAttribute("error", "Email already registered.");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
                return;
            }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            userDAO.registerUser(user);
            resp.sendRedirect(req.getContextPath() + "/user?action=login&msg=registered");
        } catch (Exception e) {
            req.setAttribute("error", "Registration failed: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }

    // ---- Login ----
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email    = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        try {
            User user = userDAO.login(email, password);
            if (user == null) {
                req.setAttribute("error", "Invalid email or password.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            if ("ADMIN".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/restaurant?action=list");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Login failed: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    // ---- Update Profile ----
    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
            return;
        }
        User loggedIn = (User) session.getAttribute("user");
        loggedIn.setName(req.getParameter("name").trim());
        loggedIn.setPhone(req.getParameter("phone").trim());
        loggedIn.setAddress(req.getParameter("address").trim());

        try {
            userDAO.updateUser(loggedIn);
            session.setAttribute("user", loggedIn);
            resp.sendRedirect(req.getContextPath() + "/user?action=profile&msg=updated");
        } catch (Exception e) {
            req.setAttribute("error", "Update failed: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
        }
    }
}
