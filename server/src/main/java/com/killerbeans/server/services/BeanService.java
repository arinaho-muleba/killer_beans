package com.killerbeans.server.services;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.repositories.BeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeanService {

    private  final BeanRepository beanRepository;

    @Autowired
    BeanService(BeanRepository beanRepository){
        this.beanRepository = beanRepository;
    }
    public List<Bean> getAllBeans(){
        return beanRepository.findAll();
    }
}
