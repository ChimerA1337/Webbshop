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

        if(password != null && username != null) {
            User tempUser = Controller.login(username, password);
            if(tempUser != null) {
                user = tempUser;
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            else message = "Please enter a valid username and/or password.";
        }
    }
    else message = "Welcome " + user.getUsername() + "!";
%>

<!DOCTYPE html>
<html lang="se">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Webbshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
</head>
<body>
    <h1>Webbshop</h1>
    <h2>Login</h2>
    <% if(user == null) { %>
        <div>
        <form method="post" action="index.jsp" id="loginForm">
            <input type="text" placeholder="Enter username..." name="username"><br>
            <input type="text" placeholder="Enter password..." name="password"><br>
            <button type="submit">Submit</button>

        </form>
        <div>
    <% } %>

    <% if(message != null) { %>
        <p id="loginMessage"><%= message %></p>
    <% } %>
</body>
</html>