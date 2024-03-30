package com.svalero.books;

import com.svalero.books.dao.BookDao;
import com.svalero.books.dao.Database;
import com.svalero.books.domain.Book;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;

    public Menu() {
        keyboard = new Scanner(System.in);
    }

    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() {
        connect();

        String choice = null;

        do {
            System.out.println("Catálogo de libros");
            System.out.println("1. Añadir un libro");
            System.out.println("2. Buscar un libro");
            System.out.println("3. Eliminar un libro");
            System.out.println("4. Modificar un libro");
            System.out.println("5. Ver todo el catálogo");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            choice = keyboard.nextLine();

            switch(choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    searchBook();
                    break;
                case "3":
                    deleteBook();
                    break;
                case "4":
                    modifyBook();
                    break;
                case "5":
                    showCatalog();
                    break;
            }
        } while (!choice.equals("6"));
    }

    public void addBook() {
        System.out.print("Título: ");
        String title = keyboard.nextLine();
        System.out.print("Autor: ");
        String author = keyboard.nextLine();
        System.out.print("Editorial: ");
        String publisher = keyboard.nextLine();
        Book book = new Book(title.trim(), author.trim(), publisher.trim());

        BookDao bookDao = new BookDao(connection);
        bookDao.add(book);
        System.out.println("El libro se ha registrado correctamente");
    }

    public void searchBook() {
        System.out.print("Búsqueda por titulo: ");
        String title = keyboard.nextLine();

        BookDao bookDao = new BookDao(connection);
        Book book = bookDao.findByTitle(title);
        if (book == null) {
            System.out.println("Ese libro no existe");
            return;
        }

        System.out.println(book.getTitle());
        System.out.println(book.getAuthor());
        System.out.println(book.getPublisher());
    }

    public void deleteBook() {
        System.out.print("Titulo del libro a eliminar: ");
        String title = keyboard.nextLine();
        BookDao bookDao = new BookDao(connection);
        boolean deleted = bookDao.delete(title);
        if (deleted)
            System.out.println("El libro se ha borrado correctamente");
        else
            System.out.println("El libro no se ha podido borrar. No existe");
    }

    public void modifyBook() {
        System.out.print("Titulo del libro a modificar: ");
        String title = keyboard.nextLine();
        // TODO Buscar el libro antes de pedir los nuevos datos
        System.out.print("Nuevo Título: ");
        String newTitle = keyboard.nextLine();
        System.out.print("Nuevo Autor: ");
        String newAuthor = keyboard.nextLine();
        System.out.print("Nueva Editorial: ");
        String newPublisher = keyboard.nextLine();
        Book newBook = new Book(newTitle.trim(), newAuthor.trim(), newPublisher.trim());

        BookDao bookDao = new BookDao(connection);
        boolean modified = bookDao.modify(title, newBook);
        if (modified)
            System.out.println("El libro se ha modificado correctamente");
        else
            System.out.println("El libro no se ha podido modificar. No existe");
    }

    public void showCatalog() {
        BookDao bookDao = new BookDao(connection);
        // TODO Propagar la excepción al menú de usuario
        ArrayList<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }
}
