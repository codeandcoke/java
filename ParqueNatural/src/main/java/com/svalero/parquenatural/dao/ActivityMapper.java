package com.svalero.parquenatural.dao;

import com.svalero.parquenatural.domain.Activity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityMapper implements RowMapper<Activity> {

    @Override
    public Activity map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Activity(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("datetime"),
                rs.getFloat("price"),
                rs.getString("picture"));
    }
}
