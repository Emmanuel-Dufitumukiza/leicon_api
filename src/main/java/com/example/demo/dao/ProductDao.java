package com.example.demo.dao;

import com.example.demo.models.Customers;
import com.example.demo.models.Product;

import java.util.List;

public interface ProductDao {
    default List<Product> addProduct(Product product){
        return addProduct(product);
    }
    default List<Product> getProducts(){
        return getProducts();
    }
}
