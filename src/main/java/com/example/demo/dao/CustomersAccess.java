package com.example.demo.dao;

import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
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

@Repository("customerDao")
public class CustomersAccess implements  CustomerDao {

    PasswordEncoder passwordEncoder;

    public CustomersAccess(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Customers> insertCustomer(Customers customer) {
        String sql = "INSERT INTO customers (gateId, username, email, secretPin, district, sector, telephone)" +
                " VALUES(?,?,?,?,?,?,?)";

        int min = 1111;
        int max = 9999;
        Random random = new Random();
        int value = random.nextInt(max + min) + min;

        int min2 = 100;
        int max2 = 999;
        Random random2 = new Random();
        int value2 = random2.nextInt(max2 + min2) + min2;

        String num = value + "" + value2;

         String exists = "SELECT username FROM customers WHERE gateId = " + num;
        jdbcTemplate.query(exists, new ResultSetExtractor<List<Customers>>() {

            @Override
            public List<Customers> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    insertCustomer(customer);
                    return null;
                } else {
                    if(customer.getSecretPin() == ""){
                        int result = jdbcTemplate.update(sql, null, customer.getUsername(),
                                customer.getEmail(), null, customer.getDistrict(),
                                customer.getSector(), customer.getTelephone());
                    }

                 if(customer.getSecretPin() != ""){
                     String encodedPassword = passwordEncoder.encode(customer.getSecretPin());

                     int result = jdbcTemplate.update(sql, num, customer.getUsername(),
                             customer.getEmail(),encodedPassword, customer.getDistrict(),
                             customer.getSector(), customer.getTelephone());
                 }

                    return null;
                }
            }
        });

        return null;
    }

    public List<Customers> getCustomers() {
        String exists = "SELECT * FROM customers order by date desc";
        List<Customers> list = new ArrayList<Customers>();

        return jdbcTemplate.query(exists, new ResultSetExtractor<List<Customers>>() {

            @Override
            public List<Customers> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    list.add(new Customers(
                            rs.getString("username"),
                            rs.getString("telephone"),
                            rs.getString("secretPin"),
                            rs.getString("email"),
                            rs.getString("district"),
                            rs.getString("sector"),
                            rs.getString("gateId"),
                            rs.getString("date"),
                            rs.getInt("id"))
                            );
                }

                return list;
            }
        });
    }

    public int approveCustomer(int id,Customers newInfo){
        int min = 1111;
        int max = 9999;
        Random random = new Random();
        int value = random.nextInt(max + min) + min;

        int min2 = 100;
        int max2 = 999;
        Random random2 = new Random();
        int value2 = random2.nextInt(max2 + min2) + min2;

        String num = value + "" + value2;

        String exists = "SELECT username FROM customers WHERE gateId = " + num;
        jdbcTemplate.query(exists, new ResultSetExtractor<Integer>() {

                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            approveCustomer(id, newInfo);
                            return null;
                        } else {
                            String sql = "UPDATE customers set gateId  = ? ," +
                                    " secretPin = ? where id = ?";

                            String encodedPassword = passwordEncoder.encode(newInfo.getSecretPin());

                           return jdbcTemplate.update(sql, new Object[] {num, encodedPassword, id});
                        }
                    }
                });
        return 0;
    }
}