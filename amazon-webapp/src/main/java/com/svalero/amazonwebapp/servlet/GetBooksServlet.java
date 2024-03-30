package com.svalero.amazonwebapp.servlet;

import com.svalero.amazonwebapp.dao.BookDao;
import com.svalero.amazonwebapp.dao.Database;
import com.svalero.amazonwebapp.domain.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class GetBooksServlet extends HttpServlet {

    // TODO Mejor hacer como jsp
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<head>\n" +
                "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n" +
                "</head>");
        out.println("<div class='container'>");
        out.println("<h1>Listado de libros</h1>");
        Database database = new Database();
        BookDao bookDao = new BookDao(database.getConnection());
        try {
            out.println("<ul class='list-group'>");
            List<Book> books = bookDao.findAll();
            for (Book book : books) {
                out.println("<li class='list-group-item'><a href='book.jsp?id=" + book.getId() + "'>" + book.getTitle() + "</a></li>");
            }
            out.println("</ul>");
        } catch (SQLException sqle) {
            out.println("<h3>Se ha producido un error al cargar los datos de los libros</h3>");
            sqle.printStackTrace();
        }
        out.println("</div>");
    }
}