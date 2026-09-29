package presentation;

import application.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

    static public User login(String username, String password) {
        return Model.loginUser(username, password);
    }
}
