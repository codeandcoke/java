<%@ page import="com.svalero.parquenatural.dao.Database" %>
<%@ page import="com.svalero.parquenatural.dao.ActivityDao" %>
<%@ page import="com.svalero.parquenatural.domain.Activity" %>
<%@ page import="com.svalero.parquenatural.util.CurrencyUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="includes/header.jsp"%>

<main>
    <section class="py-5 text-center container">
        <h1>Ver Actividad</h1>
    </section>
        <%
          // TODO Validar si viene el campo id
          int id = Integer.parseInt(request.getParameter("id"));

          Database.connect();
          Activity activity = Database.jdbi.withExtension(ActivityDao.class, dao -> dao.getActivity(id));
        %>
    <div class="container">
        <div class="card text-center">
            <div class="card-header">
                Actividad
            </div>
            <div class="card-body">
                <h5 class="card-title"><%= activity.getName() %></h5>
                <p class="card-text"><%= activity.getDescription() %></p>
                <a href="#" class="btn btn-primary">Apuntarme</a>
            </div>
            <div class="card-footer text-body-secondary">
                <%= CurrencyUtils.format(activity.getPrice()) %>
            </div>
        </div>
    </div>
</main>

<%@include file="includes/footer.jsp"%>
