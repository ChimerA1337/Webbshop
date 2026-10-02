<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Webshop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Administrator Menu</h1>

    <c:if test="${not empty requestScope.message}">
        <p><c:out value="${requestScope.message}"/></p>
    </c:if>

    <h2>Users</h2>
    <table id="adminUserTable">
        <tr class="adminUserTableHeader">
            <th>User ID</th>
            <th>Username</th>
            <th>Permission Level</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="user" items="${requestScope.users}">
            <tr class="adminUserTableElement">
                <td><c:out value="${user.userid}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.permissionlevel}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="action" value="deleteUser">
                        <input type="hidden" name="userid" value="${user.userid}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Products</h2>
    <table id="adminItemTable">
        <tr>
            <th>Item ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td><c:out value="${item.itemid}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.price}"/></td>
                <td><c:out value="${item.amount}"/></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="action" value="deleteItem">
                        <input type="hidden" name="itemid" value="${item.itemid}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Add product</h2>
    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="action" value="addItem">

        <label for="name">Name</label>
        <input id="name" type="text" name="name" required>
        <br>

        <label for="price">Price</label>
        <input id="price" type="number" name="price" min="0" step="0.01" required>
        <br>

        <label for="description">Description</label>
        <textarea id="description" name="description"></textarea>
        <br>

        <label for="amount">Stock</label>
        <input id="amount" type="number" name="amount" min="0" step="1" required>
        <br>

        <button type="submit">Add product</button>
    </form>

    <form method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="action" value="logout">
        <button type="submit">Log out</button>
    </form>
</body>
</html>