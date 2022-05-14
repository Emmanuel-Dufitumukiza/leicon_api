package com.example.demo.dao;

import com.example.demo.models.Customers;
import com.example.demo.models.Product;

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

    default List getCustomerPassword(Customers customer){
        return getCustomerPassword(customer);
    }

    default List getRemoteCode(Customers customer){
        return getRemoteCode(customer);
    }
}
