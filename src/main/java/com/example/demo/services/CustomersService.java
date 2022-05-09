package com.example.demo.services;
import com.example.demo.dao.AdminsDao;
import com.example.demo.dao.CustomerDao;
import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {

    private final CustomerDao customerDao;

    public CustomersService(@Qualifier("customerDao") CustomerDao customer) {
        this.customerDao = customer;
    }

    public List<Customers> insertCustomer(Customers info) {
        return customerDao.insertCustomer(info);
    }

    public List<Customers> getCustomers(){
        return customerDao.getCustomers();
    }

    public int approveCustomer(int id, Customers newInfo){return customerDao.approveCustomer(id,newInfo);}

}
