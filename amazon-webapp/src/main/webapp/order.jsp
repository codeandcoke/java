<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.amazonwebapp.dao.Database" %>
<%@ page import="com.svalero.amazonwebapp.dao.OrderDao" %>
<%@ page import="com.svalero.amazonwebapp.domain.Order" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <%
        String orderId = request.getParameter("id");
        Database db = new Database();
        OrderDao orderDao = new OrderDao(db.getConnection());
        Order order = null;
        try {
            Optional<Order> optionalOrder = orderDao.findById(Integer.parseInt(orderId));
            order = optionalOrder.get();

    %>
    <div class="container">
        <div class="card text-center">
          <div class="card-header">
            Detalles del Pedido
          </div>
          <div class="card-body">
            <h5 class="card-title"><%= order.getCode() %></h5>
            <p class="card-text">Realizado por <strong><%= order.getUser().getName() %></strong></p>
            <a href="buy?id=<%= order.getId() %>" class="btn btn-primary">Ver artículos</a>
          </div>
          <div class="card-footer text-muted">
            Pagado: <strong><%= order.isPaid() %></strong>
          </div>
        </div>
    </div>
    <%
        } catch (SQLException sqle) {
    %>
        <div class='alert alert-danger' role='alert'>Se ha producido al cargar los datos del pedido</div>
    <%
        }
    %>
</body>
</html>
