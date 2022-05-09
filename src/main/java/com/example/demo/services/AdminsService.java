package com.example.demo.services;

import com.example.demo.dao.AdminsDao;
import com.example.demo.dao.CustomerDao;
import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminsService {
    private final AdminsDao adminsDao;

    public AdminsService(@Qualifier("adminsDao") AdminsDao adminsDao) {
        this.adminsDao = adminsDao;
    }

    public String[] insertAdmin(Admins info) {
        return adminsDao.insertAdmin(info);
    }

    public String[] loginAdmin(Admins info) {
        return adminsDao.loginAdmin(info);
    }

}
