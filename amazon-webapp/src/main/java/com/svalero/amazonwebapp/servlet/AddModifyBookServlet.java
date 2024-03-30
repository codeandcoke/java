package com.svalero.amazonwebapp.servlet;

import com.svalero.amazonwebapp.dao.BookDao;
import com.svalero.amazonwebapp.dao.Database;
import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.domain.User;
import com.svalero.amazonwebapp.exception.BookAlreadyExistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/add-modify-book")
public class AddModifyBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("accesonopermitido.jsp");
        }

        String title = request.getParameter("title");       // input name="title" del formulario
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String action = request.getParameter("action");
        String bookId = request.getParameter("bookId");
        Book book = new Book(title, author, publisher);

        Database database = new Database();
        BookDao bookDao = new BookDao(database.getConnection());
        try {
            if (action.equals("register")) {
                bookDao.add(book);
                out.println("<div class='alert alert-success' role='alert'>El libro se ha registrado correctamente</div>");
            } else {
                bookDao.modify(Integer.parseInt(bookId), book);
                out.println("<div class='alert alert-success' role='alert'>El libro se ha modificado correctamente</div>");
            }
        } catch (BookAlreadyExistException baee) {
            out.println("<div class='alert alert-danger' role='alert'>El libro ya existe en la base de datos</div>");
            baee.printStackTrace();
        } catch (SQLException sqle) {
            out.println("<div class='alert alert-danger' role='alert'>Se ha producido un error al registrar el libro</div>");
            sqle.printStackTrace();
        }
    }
}