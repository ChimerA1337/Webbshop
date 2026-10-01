<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="application.*" %>
<%@ page import="presentation.*" %>
<%@ page import="java.sql.SQLException" %>

<%
    User user = (User) session.getAttribute("user");
    String message = null;

    if(user == null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String permissionLevelString = request.getParameter("permissionlevel");
        PermissionLevel permissionLevel;

        if(password != null && !password.isBlank() && username != null && !username.isBlank()) {
            if(!Controller.usernameTaken(username)) {
                if(permissionLevelString.equals("Employee")) permissionLevel = PermissionLevel.Employee;
                else if(permissionLevelString.equals("Customer")) permissionLevel = PermissionLevel.Customer;
                else permissionLevel = PermissionLevel.None;

                user = Controller.register(username, password, permissionLevel);
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/register.jsp");
                return;
            }
            else message = "Please enter both fields.";
        }
    }
    else message = "Welcome " + user.getUsername() + "!";
%>

<!DOCTYPE html>
<html lang="se">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
</head>
<body>
    <% if(user == null) { %>
        <form method="post" action="register.jsp">
            <h2>Register</h2>
            <input type="text" placeholder="Enter a username..." name="username"><br>
            <input type="text" placeholder="Choose a password..." name="password"><br>
            <div id="permissionLevelContainer">
                <input type="radio" name="permissionlevel" id="employee" value="Employee">
                <label for="employee">Employee</label><br>
                <input type="radio" name="permissionlevel" id="customer" value="Customer" checked>
                <label for="customer">Customer</label><br>
            </div>
            <button type="submit">Submit</button>
        </form>
    <% } else {%>
        <a href="index.jsp">
            <h2 type="link"><i class="fa-solid fa-address-card" class="link"></i>Go to login page</h2>
        </a>
    <% } %>

    <% if(message != null) { %>
        <p class="messages"><%= message %></p>
    <% } %>
</body>
</html>