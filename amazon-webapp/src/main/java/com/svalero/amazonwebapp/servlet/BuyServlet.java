package com.svalero.amazonwebapp.servlet;

import com.svalero.amazonwebapp.dao.BookDao;
import com.svalero.amazonwebapp.dao.Database;
import com.svalero.amazonwebapp.dao.OrderDao;
import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.domain.Order;
import com.svalero.amazonwebapp.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("accesonopermitido.jsp");
        }

        String bookId = request.getParameter("id");
        Database database = new Database();
        BookDao bookDao = new BookDao(database.getConnection());
        try {
            Optional<Book> book = bookDao.findById(Integer.parseInt(bookId));
            if (!book.isPresent()) {
                // TODO Error
            }

            Order order = new Order();

            OrderDao orderDao = new OrderDao(database.getConnection());
            orderDao.addOrder(currentUser, List.of(book.get()));
            out.println("<div class='alert alert-success' role='alert'>Pedido realizado correctamente</div>");

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}