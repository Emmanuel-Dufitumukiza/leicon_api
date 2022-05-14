package com.example.demo.api;

import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
import com.example.demo.models.Product;
import com.example.demo.services.AdminsService;
import com.example.demo.services.CustomersService;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
//https://leicon-rw.vercel.app
@RequestMapping("api/v1/customers")
@RestController
public class CustomersController {
    private final CustomersService customerService;
    private final AdminsService adminsService;

    private final ProductService productService;

    @Autowired
    public CustomersController(CustomersService customerService, AdminsService adminsService, ProductService productService) {
        this.customerService = customerService;
        this.adminsService = adminsService;
        this.productService = productService;
    }

    @PostMapping("/register")
    public void saveCustomer(@RequestBody Customers customer){
        customerService.insertCustomer(customer);
    }

    @GetMapping
    public List<Customers> getCusomers(){return customerService.getCustomers();}

    @PatchMapping(path = "{id}")
    public int approveCustomer(@PathVariable("id") int id,@RequestBody Customers newInfo){
        return customerService.approveCustomer(id, newInfo);
    }

    @PostMapping("/admins")
    public String[] insertAdmin(@RequestBody Admins admin){
       return adminsService.insertAdmin(admin);
    }

    @PostMapping("/admins/login")
    public String[] loginAdmin(@RequestBody Admins admin){
        return adminsService.loginAdmin(admin);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @PostMapping("/getPassword")
    public List getCustomerPassword(@RequestBody Customers customer){
        return customerService.getCustomerPassword(customer);
    }

    @PostMapping("/getRemoteCode")
    public List getRemoteCode(@RequestBody Customers customer){
        return customerService.getRemoteCode(customer);
    }

    @GetMapping("/allProducts")
    public List<Product> getProducts(){return productService.getProducts();}

}
