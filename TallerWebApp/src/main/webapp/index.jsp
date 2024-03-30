<%@ page import="com.svalero.dao.Database" %>
<%@ page import="com.svalero.dao.VehicleDAO" %>
<%@ page import="com.svalero.domain.Vehicle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@include file="includes/header.jsp"%>

<main>

  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <div class="col-lg-6 col-md-8 mx-auto">
        <h1 class="fw-light">Aplicación para gestión del taller</h1>
        <p class="lead text-muted">Gestiona tu taller</p>
        <p>
          <a href="vehicle-form.jsp?action=register" class="btn btn-info my-2">Registrar nuevo vehículo</a>
          <a href="register-order.jsp" class="btn btn-info my-2">Registrar una orden de taller</a>
        </p>
      </div>
    </div>
  </section>

  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <%
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database.connect();
            List<Vehicle> vehicleList = Database.jdbi.withExtension(VehicleDAO.class, VehicleDAO::getVehicles);
            for (Vehicle vehicle : vehicleList) {
        %>
        <div class="col">
          <div class="card shadow-sm">
            <img src="../taller_data/<%= vehicle.getImage() %>" class="bd-placeholder-img card-img-top"/>
            <div class="card-body">
              <p class="card-text"><%= vehicle.getBrand() %> <%= vehicle.getModel() %></p>
              <small class="text-muted">Registro: <%= vehicle.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) %></small>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <a href="view-details.jsp?id=<%= vehicle.getId() %>" class="btn btn-sm btn-outline-primary">Ver detalles</a>
                  <a href="vehicle-form.jsp?id=<%= vehicle.getId() %>&action=edit&licensePlate=<%= vehicle.getLicensePlate() %>&brand=<%= vehicle.getBrand() %>&model=<%= vehicle.getModel() %>&kilometers=<%= vehicle.getKilometers() %>"
                    class="btn btn-sm btn-outline-secondary">Editar vehiculo</a>
                  <a href="remove-vehicle?id=<%= vehicle.getId() %>" class="btn btn-sm btn-outline-warning">Eliminar</a>
                </div>
                <small class="text-muted"><%= vehicle.getKilometers() %> kms</small>
              </div>
            </div>
          </div>
        </div>
        <%
            }
        %>
      </div>
    </div>
  </div>

</main>

<%@include file="includes/footer.jsp"%>
