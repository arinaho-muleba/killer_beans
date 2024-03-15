package com.killerbeans.server.services;

import com.killerbeans.server.models.CustomerPhoneNumber;
import com.killerbeans.server.models.OrderLine;
import com.killerbeans.server.repositories.CustomerPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPhoneNumberService {

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;

    @Autowired
    public CustomerPhoneNumberService(CustomerPhoneNumberRepository customerPhoneNumberRepository){this.customerPhoneNumberRepository=customerPhoneNumberRepository;}
    public CustomerPhoneNumber saveCustomerPhoneNumber(CustomerPhoneNumber customerPhoneNumber) {
        return customerPhoneNumberRepository.save(customerPhoneNumber);
    }
}
