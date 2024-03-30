package com.svalero.books.dao;

import com.svalero.books.domain.Book;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {

    private Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Book book) {
        String sql = "INSERT INTO books (title, author, publisher) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }
    }

    public boolean delete(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            int rows = statement.executeUpdate();

            return rows == 1;
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return false;
    }

    public boolean modify(String title, Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ? WHERE title = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, title);
            int rows = statement.executeUpdate();
            return rows == 1;
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return false;
    }

    public ArrayList<Book> findAll() {
        String sql = "SELECT * FROM books ORDER BY title";
        ArrayList<Book> books = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublisher(resultSet.getString("publisher"));
                books.add(book);
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return books;
    }

    public Book findByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        Book book = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = new Book();
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublisher(resultSet.getString("publisher"));
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return book;
    }
}
