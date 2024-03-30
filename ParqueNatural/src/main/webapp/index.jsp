<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.svalero.parquenatural.domain.Activity" %>
<%@ page import="com.svalero.parquenatural.dao.ActivityDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.svalero.parquenatural.dao.Database" %>
<%@ page import="com.svalero.parquenatural.util.CurrencyUtils" %>

<%@include file="includes/header.jsp"%>

<main>
  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <div class="col-lg-6 col-md-8 mx-auto">
        <h1 class="fw-light">Parque Natural de Ordesa</h1>
        <p class="lead text-body-secondary">Gestor de actividades</p>
        <p>
          <%
            if (role.equals("admin")) {
          %>
          <a href="edit-activity.jsp" class="btn btn-primary my-2">Registrar nueva actividad</a>
          <%
            }
          %>
          <a href="#" class="btn btn-secondary my-2">Ver calendario</a>
        </p>
      </div>
    </div>
  </section>

  <div class="album py-5 bg-body-tertiary">
    <div class="container">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <%
          Database.connect();
          List<Activity> activities = Database.jdbi.withExtension(ActivityDao.class, dao -> dao.getAllActivities());
          for (Activity activity : activities) {
        %>
        <div class="col">
          <div class="card shadow-sm">
            <img src="../parque_pictures/<%= activity.getPicture() %>"/>
            <div class="card-body">
              <p class="card-text"><strong><%= activity.getName() %></strong></p>
              <p class="card-text"><%= activity.getDescription() %></p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <a href="view-activity.jsp?id=<%= activity.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Ver</a>
                  <%
                    if (role.equals("admin")) {
                  %>
                  <a href="edit-activity.jsp?id=<%= activity.getId() %>" type="button" class="btn btn-sm btn-outline-primary">Editar</a>
                  <a href="remove-activity?id=<%= activity.getId() %>" type="button" class="btn btn-sm btn-outline-danger">Eliminar</a>
                  <%
                    }
                  %>
                </div>
                <small class="text-body-secondary"><%= CurrencyUtils.format(activity.getPrice()) %></small>
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