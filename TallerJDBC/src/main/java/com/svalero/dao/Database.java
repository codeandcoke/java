package com.svalero.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicles", "gestaller", "gestaller1234");
            System.out.println("Conectado!");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            System.out.println("Driver no encontrado");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("Fallo de conexión");
        }

        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Desconectado!");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("Fallo de conexión");
        }
    }
}
