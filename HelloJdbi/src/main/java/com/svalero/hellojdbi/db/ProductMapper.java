package com.svalero.hellojdbi.db;

import com.svalero.hellojdbi.domain.Product;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Product(rs.getInt("id"),
                rs.getString("name"),
                rs.getFloat("price"));
    }
}
