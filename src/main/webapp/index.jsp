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
        System.out.println("username:" + username + ", password:" + password);

        if(password != null && !password.isBlank() && username != null && !username.isBlank()) {
            User tempUser = Controller.login(username, password);
            if(tempUser != null) {
                user = tempUser;
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            else message = "This username is taken.";
        }
        else message = "please enter both a username and a password.";
    }
    else message = "Welcome " + user.getUsername() + "!";
%>

<!DOCTYPE html>
<html lang="se">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
</head>
<body>
    <h1>Webshop</h1>
    <h2>Login</h2>
    <% if(user == null) { %>
        <form method="post" action="index.jsp">
            <input type="text" placeholder="Enter username..." name="username"><br>
            <input type="text" placeholder="Enter password..." name="password"><br><br>
            <button type="submit">Submit</button>

            <a href="register.jsp">
                <h3 type="link"><i class="fa-solid fa-address-card"></i>Register</h3>
            </a>
        </form>
    <% } %>

    <% if(message != null) { %>
        <p class="messages"><%= message %></p>
    <% } %>
</body>
</html>