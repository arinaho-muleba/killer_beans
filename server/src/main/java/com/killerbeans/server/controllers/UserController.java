package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Customer;
import com.killerbeans.server.models.Agent;
import com.killerbeans.server.models.CustomerPhoneNumber;
import com.killerbeans.server.models.dtos.SignUpModel;
import com.killerbeans.server.repositories.CustomerPhoneNumberRepository;
import com.killerbeans.server.repositories.CustomerRepository;
import com.killerbeans.server.services.AgentService;
import com.killerbeans.server.services.CustomerPhoneNumberService;
import com.killerbeans.server.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class UserController {

    private final CustomerService customerService;

    private final CustomerPhoneNumberService customerPhoneNumberService;
    private final AgentService agentService;

    private final CustomerRepository customerRepository;

    @Autowired
    public UserController(CustomerService customerService,CustomerPhoneNumberService customerPhoneNumberService,AgentService agentService,CustomerRepository customerRepository){
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.customerService = customerService;
        this.agentService = agentService;
        this.customerRepository =customerRepository;
    }

    @GetMapping("/")
    public String login( HttpServletRequest request,@AuthenticationPrincipal OAuth2User oauth2User){
        String token = request.getHeader("cookie");
        String alias = (String) oauth2User.getAttribute("login");
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        Long userId = customerService.getCustomerByAlias(alias)
                .map(Customer::getId)
                .orElseGet(() -> agentService.getAgentByAlias(alias)
                        .map(agent -> {
                            isAdmin.set(true);
                            return agent.getId();
                        })
                        .orElse(null));
        if(userId==null){
            Customer customer = new Customer();
            customer.setAlias(alias);
            customerService.saveCustomer(customer);

            userId = customerService.getCustomerByAlias(alias)
                    .map(Customer::getId)
                    .orElse(null);
        }
        return  "Welcome to killer beans.\n Copy Token to CLI, Token : "+token+":"+userId.toString()+":"+isAdmin.toString();
    }
}
