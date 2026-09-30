package presentation;

import application.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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
