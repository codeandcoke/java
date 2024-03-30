<%@ page import="com.svalero.amazonwebapp.dao.Database" %>
<%@ page import="com.svalero.amazonwebapp.dao.BookDao" %>
<%@ page import="com.svalero.amazonwebapp.domain.Book" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <%
        String bookId = request.getParameter("id");
        Database db = new Database();
        BookDao bookDao = new BookDao(db.getConnection());
        Book book = null;
        try {
            Optional<Book> optionalBook = bookDao.findById(Integer.parseInt(bookId));
            book = optionalBook.get();

    %>
    <div class="container">
        <div class="card text-center">
          <div class="card-header">
            Detalles del libro
          </div>
          <div class="card-body">
            <h5 class="card-title"><%= book.getTitle() %></h5>
            <p class="card-text">Escrito por <strong><%= book.getAuthor() %></strong></p>
            <a href="buy?id=<%= book.getId() %>" class="btn btn-primary">Comprar</a>
          </div>
          <div class="card-footer text-muted">
            Editado por <strong><%= book.getPublisher() %></strong>
          </div>
        </div>
    </div>
    <%
        } catch (SQLException sqle) {
    %>
        <div class='alert alert-danger' role='alert'>Se ha producido al cargar los datos del libro</div>
    <%
        }
    %>
</body>
</html>
