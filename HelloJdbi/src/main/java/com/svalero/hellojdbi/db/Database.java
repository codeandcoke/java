package com.svalero.hellojdbi.db;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLException;

import static com.svalero.hellojdbi.util.Constants.*;

public class Database {

    public static Jdbi jdbi;
    public static Handle db;

    public static void connect() throws ClassNotFoundException, SQLException {
        jdbi = Jdbi.create(CONNECTION_STRING, USERNAME, PASSWORD);
        jdbi.installPlugin(new SqlObjectPlugin());
        db = jdbi.open();
    }

    public void close() throws SQLException {
        db.close();
    }
}
