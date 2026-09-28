<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="application.Item" %>

<!DOCTYPE html>
<html>
<head>
    <title>Webbshop</title>
</head>
<body>
    <h1>Webbshop</h1>
    <%
        String userid = request.getParameter("uname");
        String pwd = request.getParameter("pass");
        if (pwd != null && userid != null) {
            session.setAttribute("userid", userid);
    %>Logged in as <%= userid %>

    <% } else { %>
        <form method="post" action="login.jsp">
            <table border="1">
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
    <% } %>
</body>
</html>