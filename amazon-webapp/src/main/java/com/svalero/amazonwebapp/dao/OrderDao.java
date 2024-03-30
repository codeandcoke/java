package com.svalero.amazonwebapp.dao;

import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.domain.Order;
import com.svalero.amazonwebapp.domain.User;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void addOrder(User user, List<Book> books) throws SQLException {
        String orderSql = "INSERT INTO orders (code, user_id, date) VALUES (?, ?, ?)";

        connection.setAutoCommit(false);

        PreparedStatement orderStatement = connection.prepareStatement(orderSql,
                PreparedStatement.RETURN_GENERATED_KEYS);
        orderStatement.setString(1, UUID.randomUUID().toString());
        orderStatement.setInt(2, user.getId());
        orderStatement.setDate(3, new Date(System.currentTimeMillis()));
        orderStatement.executeUpdate();

        // Obtener el orderId generado en la sentencia anterior (el último AUTO_INCREMENT generado)
        ResultSet orderKeys = orderStatement.getGeneratedKeys();
        orderKeys.next();
        int orderId = orderKeys.getInt(1);
        orderStatement.close();

        for (Book book : books) {
            String bookSql = "INSERT INTO order_book (order_id, book_id) VALUES (?, ?)";

            PreparedStatement bookStatement = connection.prepareStatement(bookSql);
            bookStatement.setInt(1, orderId);
            bookStatement.setInt(2, book.getId());
            bookStatement.executeUpdate();
        }

        connection.commit();
        connection.setAutoCommit(true);
    }

    public List<Order> findAll() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY date";
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Order order = fromResultSet(resultSet);
            orders.add(order);
        }

        return orders;
    }

    public List<Order> findAll(User user) throws SQLException {
        String sql = "SELECT * FROM orders o, users u WHERE o.user_id = u.id AND u.id = ? ORDER BY date";
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, user.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Order order = fromResultSet(resultSet);
            orders.add(order);
        }

        return orders;
    }

    public Optional<Order> findById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            order = fromResultSet(resultSet);
        }

        return Optional.ofNullable(order);
    }


    public void payOrder() {

    }

    private Order fromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setCode(resultSet.getString("code"));
        order.setDate(new java.util.Date(resultSet.getDate("date").getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        order.setPaid(resultSet.getBoolean("paid"));
        UserDao userDao = new UserDao(connection);
        String userId = resultSet.getString("user_id");
        User user = userDao.findById(Integer.parseInt(userId)).get();   // FIXME Comprobar que no falle
        order.setUser(user);
        // TODO Traerse también los libros relacionados
        return order;
    }

    // TODO Terminar de hacer el resto de métodos de este DAO: modifyUser, deleteUser, getUsers, . . . .
}
