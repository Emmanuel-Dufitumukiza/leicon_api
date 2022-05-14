package com.example.demo.services;

import com.example.demo.dao.CustomerDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.models.Customers;
import com.example.demo.models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(@Qualifier("productDao") ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> addProduct(Product info) {
        return productDao.addProduct(info);
    }

    public List<Product> getProducts(){
        return productDao.getProducts();
    }
}
