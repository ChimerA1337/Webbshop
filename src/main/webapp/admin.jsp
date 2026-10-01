<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://kit.fontawesome.com/dd581d5599.js" crossorigin="anonymous"></script>
</head>
<body>
    <h1>Administrator Menu</h1>
    <h2>Users<h2>
    <table id="adminUserTable">
        <tr class="adminUserTableHeader">
            <th>User ID: </th>
            <th>Username: </th>
            <th>Permission Level: </th>
        </tr>
        <c:forEach var="user" items="${requestScope.users}">
            <tr class="adminUserTableElement">
            <div class="darkGreenContainer">
                <td><c:out value="${user.userid}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.permissionlevel}"/></td>
            </div>
            </tr>
        </c:forEach>
    </table>
</body>
</html>