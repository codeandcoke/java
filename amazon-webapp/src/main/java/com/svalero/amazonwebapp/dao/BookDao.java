package com.svalero.amazonwebapp.dao;

import com.svalero.amazonwebapp.domain.Book;
import com.svalero.amazonwebapp.exception.BookAlreadyExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class BookDao {

    private Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Book book) throws SQLException, BookAlreadyExistException {
        if (existBook(book.getTitle()))
            throw new BookAlreadyExistException();

        String sql = "INSERT INTO books (title, author, publisher) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getPublisher());
        statement.executeUpdate();
    }

    public boolean delete(String title) throws SQLException {
        String sql = "DELETE FROM books WHERE title = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        int rows = statement.executeUpdate();

        return rows == 1;
    }

    public boolean modify(String title, Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ? WHERE title = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getPublisher());
        statement.setString(4, title);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public boolean modify(int bookId, Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getPublisher());
        statement.setInt(4, bookId);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public ArrayList<Book> findAll() throws SQLException {
        String sql = "SELECT * FROM books ORDER BY title";
        ArrayList<Book> books = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Book book = fromResultSet(resultSet);
            books.add(book);
        }

        return books;
    }

    public ArrayList<Book> findAll(String searchText) throws SQLException {
        String sql = "SELECT * FROM books WHERE INSTR(title, ?) != 0 OR INSTR(author, ?) != 0 ORDER BY title";
        ArrayList<Book> books = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Book book = fromResultSet(resultSet);
            books.add(book);
        }

        return books;
    }

    public Optional<Book> findByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = ?";
        Book book = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, title);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            book = fromResultSet(resultSet);
        }

        return Optional.ofNullable(book);
    }

    public Optional<Book> findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        Book book = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            book = fromResultSet(resultSet);
        }

        return Optional.ofNullable(book);
    }

    private Book fromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(resultSet.getString("publisher"));
        return book;
    }

    private boolean existBook(String title) throws SQLException {
        Optional<Book> book = findByTitle(title);
        return book.isPresent();
    }
}
