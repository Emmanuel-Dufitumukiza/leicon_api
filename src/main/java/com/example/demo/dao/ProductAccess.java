package com.example.demo.dao;

import com.example.demo.models.Customers;
import com.example.demo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository("productDao")
public class ProductAccess implements ProductDao {
    PasswordEncoder passwordEncoder;
    public ProductAccess(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> addProduct(Product product) {
        String sql = "INSERT INTO products (gateId, remoteCode, password)" +
                " VALUES(?,?,?)";

        int min = 1111;
        int max = 9999;
        Random random = new Random();
        int value = random.nextInt(max + min) + min;

        int min2 = 100;
        int max2 = 999;
        Random random2 = new Random();
        int value2 = random2.nextInt(max2 + min2) + min2;

        String num = value + "" + value2;

        String exists = "SELECT gateId FROM products WHERE gateId = " + num;

        jdbcTemplate.query(exists, new ResultSetExtractor<List<Product>>() {

            @Override
            public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    addProduct(product);
                    return null;
                } else {
                    String encodedPassword = product.getPassword();

                    int result = jdbcTemplate.update(sql, num, product.getRemoteCode(),encodedPassword);

                    return null;
                }
            }
        });
        return null;
    }

    public List<Product> getProducts() {
        String exists = "SELECT * FROM products order by date desc";
        List<Product> list = new ArrayList<Product>();

        return jdbcTemplate.query(exists, new ResultSetExtractor<List<Product>>() {

            @Override
            public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getString("remoteCode"),
                            rs.getString("password"),
                            rs.getInt("gateId"),
                            rs.getString("status"),
                            rs.getString("date"))
                    );
                }

                return list;
            }
        });
    }
}
