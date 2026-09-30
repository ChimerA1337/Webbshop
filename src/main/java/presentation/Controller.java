package presentation;

import application.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Controller", value = "/controller", loadOnStartup = 1)
public class Controller extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("Initialize...\n\n");

        Model.initialize();
        super.init();
    }

    @Override
    public void destroy() {
        System.out.println("Shutdown...\n\n");
        super.destroy();
        Model.shutdown();
    }

    private static Model getModel(HttpSession session) {
        Model model = (Model) session.getAttribute("model");
        if (model == null) {
            model = new Model();
            session.setAttribute("model", model);
        }
        return model;
    }

    public static void addToCart(HttpSession session, Item item) {
        getModel(session).addToCart(item);
    }
    public static void removeFromCart(HttpSession session, Item item) {
        getModel(session).removeFromCart(item);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");
        if ("cart".equals(action) || "shop".equals(action)) {
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/controller");
                return;
            }
            if (user.getPermissionlevel() != PermissionLevel.Customer) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if ("cart".equals(action)) {
                request.setAttribute("items", getModel(session).getCartItems());
                request.setAttribute("message", session.getAttribute("message"));
                session.removeAttribute("message");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
            }
            else {
                response.sendRedirect(request.getContextPath() + "/shop.jsp");
            }
            return;
        }
        if (user != null && user.getPermissionlevel() == PermissionLevel.Customer) {
            response.sendRedirect(request.getContextPath() + "/shop.jsp");
            return;
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if ("addToCart".equals(action)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            int itemId;
            try {
                itemId = Integer.parseInt(request.getParameter("itemid"));
            } catch (NumberFormatException exception) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item ID.");
                return;
            }
            boolean added = getModel(session).addToCart(itemId);
            if (!added) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/controller?action=shop");
            return;
        }
        if ("placeOrder".equals(action)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/controller");
                return;
            }
            if (user.getPermissionlevel() != PermissionLevel.Customer) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            boolean success = getModel(session).placeOrder(user.getUserid());
            if (success) {
                session.setAttribute("message", "Order placed!");
            } else {
                session.setAttribute("message", "Order could not be placed. Check stock and try again.");
            }
            response.sendRedirect(request.getContextPath() + "/controller?action=shop");
            return;
        }

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/controller");
            return;
        }

        if (!"login".equals(action)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action.");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            request.setAttribute("message", "Please enter both username and password.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        User user = Model.loginUser(username, password);
        if (user == null) {
            request.setAttribute("message", "Incorrect username or password.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        request.changeSessionId();
        session.setAttribute("user", user);
        session.setAttribute("model", new Model());
        response.sendRedirect(request.getContextPath() + "/controller");
    }

    public static List<ItemDTO> getAllItems() {
        return Model.getAllItems();
    }

    static public User login(String username, String password) {
        return Model.loginUser(username, password);
    }
    static public User register(String username, String password, PermissionLevel permissionlevel) {
        return Model.register(username, password, permissionlevel);
    }
    static public boolean usernameTaken(String username) {
        return Model.usernameTaken(username);
    }
}
