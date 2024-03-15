package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Customer;
import com.killerbeans.server.models.CustomerPhoneNumber;
import com.killerbeans.server.models.dtos.SignUpModel;
import com.killerbeans.server.repositories.CustomerRepository;
import com.killerbeans.server.services.AgentService;
import com.killerbeans.server.services.CustomerPhoneNumberService;
import com.killerbeans.server.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/signup")
public class SignUpController {

    private final CustomerRepository customerRepository;
    private final CustomerPhoneNumberService customerPhoneNumberService;

    @Autowired
    public SignUpController( CustomerPhoneNumberService customerPhoneNumberService,CustomerRepository customerRepository){
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.customerRepository =customerRepository;
    }
    @PostMapping("/")
    public String signup(@RequestBody SignUpModel signUpModel){
        String customerPhoneNumber = signUpModel.getCustomerPhoneNumber();
        Long userId = signUpModel.getId();
        Customer customer=customerRepository.getById(userId);
        CustomerPhoneNumber phoneNumber = new CustomerPhoneNumber(customer,customerPhoneNumber);
        // phoneNumber.setPhoneNumber(customerPhoneNumber);

        // phoneNumber.setCustomer(customer);

        customerPhoneNumberService.saveCustomerPhoneNumber(phoneNumber);
        return "Sign-up complete";
    }
}
