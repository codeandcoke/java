<%@ page import="com.svalero.dao.Database" %>
<%@ page import="com.svalero.dao.VehicleDAO" %>
<%@ page import="com.svalero.domain.Vehicle" %>

<%@include file="includes/header.jsp"%>
<main>
<%
    int vehicleId = Integer.parseInt(request.getParameter("id"));
    Class.forName("com.mysql.cj.jdbc.Driver");
    Database.connect();
    Vehicle vehicle = Database.jdbi.withExtension(VehicleDAO.class, dao -> dao.getVehicle(vehicleId));
%>
<div class="container">
    <div class="card mb-3">
      <img src="..." class="card-img-top" alt="...">
      <div class="card-body">
        <h5 class="card-title"><%= vehicle.getBrand() %> <%= vehicle.getModel() %></h5>
        <p class="card-text">Vehiculo bla bla bla bla</p>
        <p class="card-text"><small class="text-muted"><%= vehicle.getKilometers() %></small></p>
      </div>
    </div>
</div>
</main>
<%@include file="includes/footer.jsp"%>