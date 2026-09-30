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
    <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap" rel="stylesheet">
</head>
<body>
    <h1>Webshop</h1>
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <h2>Login</h2>
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="action" value="login">
                <input type="text" name="username"
                       placeholder="Enter username..." required><br>
                <input type="password" name="password"
                       placeholder="Enter password..." required><br><br>
                <button type="submit">Submit</button>
                <a href="${pageContext.request.contextPath}/register.jsp">
                    <h3><i class="fa-solid fa-address-card"></i> Register</h3>
                </a>
            </form>
        </c:when>
        <c:otherwise>
            <p class="messages">
                Welcome <c:out value="${sessionScope.user.username}"/>!
            </p>
            <c:if test="${sessionScope.user.permissionlevel == 'Customer'}">
                <a href="${pageContext.request.contextPath}/controller">Open shop</a>
            </c:if>
        </c:otherwise>
    </c:choose>
    <c:if test="${not empty requestScope.message}">
        <p class="messages"><c:out value="${requestScope.message}"/></p>
    </c:if>
</body>
</html>