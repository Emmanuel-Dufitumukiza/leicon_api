package com.example.demo.dao;

import com.example.demo.models.Admins;
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

         String exists = "SELECT username FROM customers WHERE gateId = " + customer.getGateId();
         System.out.println(customer.getGateId());
        jdbcTemplate.query(exists, new ResultSetExtractor<List<Customers>>() {

            @Override
            public List<Customers> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return null;
                } else {
                    if(customer.getSecretPin() == ""){
                        int result = jdbcTemplate.update(sql, null, customer.getUsername(),
                                customer.getEmail(), null, customer.getDistrict(),
                                customer.getSector(), customer.getTelephone());
                    }

                 if(customer.getSecretPin() != ""){
                     String encodedPassword = passwordEncoder.encode(customer.getSecretPin());

                     int result = jdbcTemplate.update(sql, customer.getGateId(), customer.getUsername(),
                             customer.getEmail(),encodedPassword, customer.getDistrict(),
                             customer.getSector(), customer.getTelephone());

                      jdbcTemplate.update("UPDATE products set status = 'sold' where gateId = ?", new Object[] {customer.getGateId()});

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

        String exists = "SELECT username FROM customers WHERE gateId = " + newInfo.getGateId();
        jdbcTemplate.query(exists, new ResultSetExtractor<Integer>() {

                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return null;
                        } else {
                            String sql = "UPDATE customers set gateId  = ? ," +
                                    " secretPin = ? where id = ?";

                            String encodedPassword = passwordEncoder.encode(newInfo.getSecretPin());
                            jdbcTemplate.update("UPDATE products set status = 'sold' where gateId = ?", new Object[] {newInfo.getGateId()});

                           return jdbcTemplate.update(sql, new Object[] {newInfo.getGateId(), encodedPassword, id});
                        }
                    }
                });
        return 0;
    }

    public List getCustomerPassword(Customers customer){

        List list = new ArrayList();

        String sql = "SELECT * FROM customers WHERE gateId = "+customer.getGateId();

        jdbcTemplate.query(sql, new ResultSetExtractor<List<Customers>>() {
            @Override
            public List<Customers> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                    String hashPass = rs.getString("secretPin");

                    Boolean isValid = passwordEncoder.matches(customer.getSecretPin(), hashPass);

                    if(isValid ==true){
                        String username = rs.getString("username");
                        String sql2 = "SELECT * FROM products where gateId = "+rs.getInt("gateId");

                        jdbcTemplate.query(sql2, new ResultSetExtractor<List<Product>>() {

                            @Override
                            public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                                if(rs.next()){
                                    String password = rs.getString("password");
                                    list.add(password);
                                    list.add(username);

                                    return list;

                                }else{
                                    return list;
                                }
                            }
                        });
                    }else{
                        list.add("Incorrect gateId or secretPin");
                        return list;
                    }
            }else{
                    list.add("Incorrect gateId or secretPin");
                    return list;
                }
                return list;
        }
        });

        return list;
    }

    public List getRemoteCode(Customers product) {
        String sql = "SELECT * FROM products where gateId = "+product.getGateId();

        List list = new ArrayList();
        jdbcTemplate.query(sql, new ResultSetExtractor<List<Product>>() {

            @Override
            public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    list.add(rs.getString("remoteCode"));
                    return list;
                }else{
                    list.add("Incorrect gateId");
                    return list;
                }
            }
        });
        return list;
    }
}