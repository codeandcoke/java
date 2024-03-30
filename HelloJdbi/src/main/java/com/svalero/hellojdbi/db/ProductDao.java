package com.svalero.hellojdbi.db;

import com.svalero.hellojdbi.domain.Product;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface ProductDao {

    @SqlQuery("SELECT * FROM products")
    @UseRowMapper(ProductMapper.class)
    List<Product> getAllProducts();

    @SqlUpdate("INSERT INTO products (name, price) VALUES (?, ?)")
    int addProduct(String name, float price);
}
