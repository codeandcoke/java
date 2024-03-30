package com.svalero.amazonwebapp.dao;

import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.domain.Order;
import com.svalero.amazonwebapp.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = SHA1(?)";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }

        return Optional.ofNullable(user);
    }

    public Optional<User> findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = fromResultSet(resultSet);
        }

        return Optional.ofNullable(user);
    }

    public User fromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setRole(resultSet.getString("role"));

        return user;
    }
}
