<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>

<%@ page import="com.svalero.amazonwebapp.dao.Database" %>
<%@ page import="com.svalero.amazonwebapp.dao.BookDao" %>
<%@ page import="com.svalero.amazonwebapp.domain.Book" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.svalero.amazonwebapp.domain.User" %>

<%
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("accesonopermitido.jsp");
    }

    String textButton = "";
    String bookId = request.getParameter("id");
    Book book = null;
    if (bookId != null) {
        textButton = "Modificar";
        Database db = new Database();
        BookDao bookDao = new BookDao(db.getConnection());
        try {
            Optional<Book> optionalBook = bookDao.findById(Integer.parseInt(bookId));
            book = optionalBook.get();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    } else {
        textButton = "Registrar";
    }
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <script type="text/javascript">
        $(document).ready(function() {
            $("form").on("submit", function(event) {
                event.preventDefault();
                var formValue = $(this).serialize();
                $.post("add-modify-book", formValue, function(data) {
                    $("#result").html(data);
                });
            });
        });
    </script>
    <div class="container">
        <h2>Registro de una nueva pelicula</h2>
        <form>
            <div class="mb-2">
                <label for="title" class="form-label">Título</label>
                <input name="title" type="text" class="form-control w-25" id="title" value="<% if (book != null) out.print(book.getTitle()); %>">
            </div>
            <div class="mb-3">
                <label for="author" class="form-label">Autor</label>
                <input name="author" type="text" class="form-control w-25" id="author" value="<% if (book != null) out.print(book.getAuthor()); %>">
            </div>
            <div class="mb-2">
                <label for="publisher" class="form-label">Editorial</label>
                <input name="publisher" type="text" class="form-control w-25" id="publisher" value="<% if (book != null) out.print(book.getPublisher()); %>">
            </div>
            <input type="hidden" name="action" value="<% if (book != null) out.print("modify"); else out.print("register"); %>">
            <input type="hidden" name="bookId" value="<% if (book != null) out.print(book.getId()); %>">
            <button type="submit" class="btn btn-primary"><%= textButton %></button>
        </form>
        <div id="result"></div>
    </div>
</body>
</html>
