package com.example.demo.api;

import com.example.demo.models.Admins;
import com.example.demo.models.Customers;
import com.example.demo.services.AdminsService;
import com.example.demo.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://leicon-rw.vercel.app")

@RequestMapping("api/v1/customers")
@RestController
public class CustomersController {
    private final CustomersService customerService;
    private final AdminsService adminsService;

    @Autowired
    public CustomersController(CustomersService customerService, AdminsService adminsService) {
        this.customerService = customerService;
        this.adminsService = adminsService;
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

}
