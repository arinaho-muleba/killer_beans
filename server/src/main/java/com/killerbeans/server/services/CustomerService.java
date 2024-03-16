package com.killerbeans.server.services;

import com.killerbeans.server.models.Customer;
import com.killerbeans.server.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByAlias(String alias){
        return customerRepository.findByAlias(alias);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
