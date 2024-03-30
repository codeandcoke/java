package com.svalero.parquenatural.dao;

import com.svalero.parquenatural.domain.User;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.sql.Date;
import java.util.List;

public interface UserDao {

    @SqlQuery("SELECT * FROM users")
    @UseRowMapper(UserMapper.class)
    List<User> getAllUsers();

    @SqlQuery("SELECT * FROM users WHERE id = ?")
    @UseRowMapper(UserMapper.class)
    User getUser(int id);

    @SqlQuery("SELECT * FROM users WHERE username = ? AND password = SHA1(?)")
    @UseRowMapper(UserMapper.class)
    User getUser(String username, String password);

    @SqlUpdate("INSERT INTO users (name, username, password, role) VALUES (?, ?, SHA1(?), ?)")
    int addUser(String name, String username, String password, String role);

    @SqlUpdate("UPDATE users SET name = ?, username = ?, role = ? WHERE id = ?")
    int updateUser(String name, String username, String role, int id);

    @SqlUpdate("DELETE FROM users WHERE id = ?")
    int removeUser(int id);
}
