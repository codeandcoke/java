package com.svalero.dao;

import com.svalero.domain.Vehicle;
import com.svalero.util.DateUtils;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Vehicle(rs.getInt("id"),
                rs.getString("license_plate"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("kilometers"),
                rs.getString("image"),
                DateUtils.getLocalDate(rs.getDate("registration_date"))
        );
    }
}
