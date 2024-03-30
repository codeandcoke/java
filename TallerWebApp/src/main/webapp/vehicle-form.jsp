<%@include file="includes/header.jsp"%>

<!--<script type="text/javascript">
    $(document).ready(function() {
        $("form").on("submit", function(event) {
            event.preventDefault();
            var formValue = $(this).serialize();
            $.post("add-vehicle", formValue, function(data) {
                $("#result").html(data);
            });
        });
    });
</script>-->

<%
    String action = request.getParameter("action");
    String licensePlate = request.getParameter("licensePlate");
    if (licensePlate == null) licensePlate = "";
    String brand = request.getParameter("brand");
    if (brand == null) brand = "";
    String model = request.getParameter("model");
    if (model == null) model = "";
    String kilometers = request.getParameter("kilometers");
    if (kilometers == null) kilometers = "";
%>

<main>
<div class="container">
    <br/>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="index.jsp">Inicio</a></li>
        <li class="breadcrumb-item active" aria-current="page">Registrar vehículo</li>
      </ol>
    </nav>
    <form class="row g-3" method="post" action="edit-vehicle" enctype="multipart/form-data">
        <div class="col-md-6">
            <label for="matricula" class="form-label">Matricula</label>
            <input type="text" class="form-control" id="matricula" name="licensePlate" value='<%= licensePlate %>'>
        </div>
        <div class="col-md-6">
            <label for="marca" class="form-label">Marca</label>
            <input type="text" class="form-control" id="marca" name="brand" value='<%= brand %>'>
        </div>
        <div class="col-md-6">
            <label for="modelo" class="form-label">Modelo</label>
            <input type="text" class="form-control" id="modelo" name="model" value='<%= model %>'>
        </div>
        <div class="col-md-6">
          <label for="kilometros" class="form-label">Kilómetros</label>
          <input type="text" class="form-control" id="kilometros" name="kilometers" value='<%= kilometers %>'>
        </div>
        <div class="col-md-6">
          <label for="image" class="form-label">Imagen</label>
          <input type="file" class="form-control" id="image" name="image">
        </div>
        <input type="hidden" name="action" value="<%= action %>"/>
        <%
        if (action.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
        %>
            <input type="hidden" name="id" value="<%= id %>"/>
        <%
        }
        %>
        <div class="col-12">
            <input type="submit" value="<%= action %>"/>
        </div>
    </form>
    <br/>
    <div id="result"></div>
</div>
</main>
<%@include file="includes/footer.jsp"%>


