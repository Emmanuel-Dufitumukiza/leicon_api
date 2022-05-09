package com.example.demo.dao;

import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Repository("adminsDao")
public class AdminsAccess implements AdminsDao{

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long JWT_TOKEN_VALIDITY = 5*60*60;

        System.out.println(Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512,
                        "leiconadminjwtkeytkns").compact());

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, "leiconadminjwtkeytkns").compact();
    }
    PasswordEncoder passwordEncoder;

    public AdminsAccess(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String[] insertAdmin(Admins admin) {

        String sql2 = "SELECT * FROM admins WHERE email = '"+admin.getEmail()+"'";
        final String[] status = {""};

        jdbcTemplate.query(sql2, new ResultSetExtractor<List<Admins>>() {

                    @Override
                    public List<Admins> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if(rs.next()) {
                            status[0] = "Email already exists";
                            return null;
                        } else {
                            String sql = "INSERT INTO admins (username, email, password)" +
                                    " VALUES(?,?,?)";

                            String encodedPassword = passwordEncoder.encode(admin.getPassword());

                            int result = jdbcTemplate.update(sql,admin.getUsername(), admin.getEmail(), encodedPassword);
                            status[0] = "Admin created successfully!";
                            return null;
                        }
                    }
        });

        return status;
    }

    @Override
    public String[] loginAdmin(Admins admin) {

        String sql2 = "SELECT * FROM admins WHERE email = '"+admin.getEmail()+"'";
        final String[] status = {""};

        jdbcTemplate.query(sql2, new ResultSetExtractor<List<Admins>>() {

            @Override
            public List<Admins> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                    String hashPass = rs.getString("password");
                    int id = rs.getInt("id");
                    String subject = Integer.toString(id);

                   Boolean isValid = passwordEncoder.matches(admin.getPassword(), hashPass);

                   if(isValid ==true){
                       Map<String, Object> claims = new HashMap<>();

                       status[0] = "";
//                       status[1] = doGenerateToken(claims, subject);
//                       System.out.println(doGenerateToken(claims, subject));
//                       doGenerateToken(claims, subject);
                       return null;
                   }
                    status[0] = "Incorrect email or password.";
                    return null;

                } else {
                    status[0] = "Incorrect email or password.";
                    return null;
                }
            }
        });

        return status;
    }

}
