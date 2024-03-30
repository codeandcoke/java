<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.amazonwebapp.dao.Database" %>
<%@ page import="com.svalero.amazonwebapp.dao.OrderDao" %>
<%@ page import="com.svalero.amazonwebapp.domain.Order" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="com.svalero.amazonwebapp.domain.User" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h2>Listado de pedidos: <%= currentUser %></h2>
        <ul class="list-group">
            <%
                // Acceder a la base de datos y recuperar la información de los pedidos
                Database database = new Database();
                OrderDao orderDao = new OrderDao(database.getConnection());
                try {
                    List<Order> orders = orderDao.findAll(currentUser);
                    for (Order order : orders) {
            %>
                        <li class="list-group-item">
                            <a target="_blank" href="order.jsp?id=<%= order.getId() %>"><%= order %></a>
                        </li>
            <%
                    }
               } catch (SQLException sqle) {
            %>
                    <div class="alert alert-danger" role="alert">
                      Error conectando con la base de datos
                    </div>
            <%
               }
            %>
        </ul>
    </div>
</body>
</html>