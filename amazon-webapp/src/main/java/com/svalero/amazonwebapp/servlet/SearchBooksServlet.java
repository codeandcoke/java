package com.svalero.amazonwebapp.servlet;

import com.svalero.amazonwebapp.dao.BookDao;
import com.svalero.amazonwebapp.dao.Database;
import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.exception.BookAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search-books")
public class SearchBooksServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchText = request.getParameter("searchtext");

        Database database = new Database();
        BookDao bookDao = new BookDao(database.getConnection());
        try {
            ArrayList<Book> books = bookDao.findAll(searchText);
            StringBuilder result = new StringBuilder("<ul class='list-group'>");
            for (Book book : books) {
                result.append("<li class='list-group-item'>").append(book.getTitle()).append("</li>");
            }
            result.append("</ul>");
            out.println(result);
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error durante la búsqueda</div>");
            sqle.printStackTrace();
        }
    }
}