<%@ page import="com.svalero.amazonwebapp.dao.Database" %>
<%@ page import="com.svalero.amazonwebapp.dao.BookDao" %>
<%@ page import="com.svalero.amazonwebapp.domain.Book" %>
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
        <h2>Listado de libros</h2>
        <ul class="list-group">
            <%
                // Acceder a la base de datos y recuperar la información de los libros
                Database database = new Database();
                BookDao bookDao = new BookDao(database.getConnection());
                try {
                    List<Book> books = bookDao.findAll();
                    for (Book book : books) {
            %>
                        <li class="list-group-item">
                            <a target="_blank" href="book.jsp?id=<%= book.getId() %>"><%= book.getTitle() %></a>
                            <%
                                if ((currentUser != null) && (currentUser.getRole().equals("admin"))) {
                            %>
                                    <a href="addbook.jsp?id=<%= book.getId() %>" class="btn btn-outline-warning">Modificar</a>
                                    <a href="delete-book?id=<%= book.getId() %>" class="btn btn-outline-danger">Eliminar</a>
                            <%
                                }
                            %>
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