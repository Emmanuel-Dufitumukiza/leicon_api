package com.example.demo.dao;

import com.example.demo.models.Customers;

import java.util.List;
import java.util.UUID;

public interface CustomerDao {

    default List<Customers> insertCustomer(Customers person){
        return insertCustomer(person);
    }

    default List<Customers> getCustomers(){
        return getCustomers();
    }

    default int approveCustomer(int id,Customers newInfo){return  approveCustomer(id,newInfo);}
}
