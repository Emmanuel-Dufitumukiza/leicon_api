package com.example.demo.dao;

import com.example.demo.models.Admins;

public interface AdminsDao {

    default String[] insertAdmin(Admins admin){
        return insertAdmin(admin);
    }

    default String[] loginAdmin(Admins admin){
        return loginAdmin(admin);
    }
}
