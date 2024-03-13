package com.killerbeans.server.services;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.models.Price;
import com.killerbeans.server.repositories.BeanRepository;
import com.killerbeans.server.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeanService {

    private  final BeanRepository beanRepository;
    private final PriceRepository priceRepository;

    @Autowired
    BeanService(BeanRepository beanRepository, PriceRepository priceRepository){
        this.beanRepository = beanRepository;
        this.priceRepository = priceRepository;
    }
    public List<Bean> getAllBeans(){
        List<Bean> beans = beanRepository.findAll();
        for (Bean bean : beans) {
            Optional<Price> currentPrice = priceRepository.findCurrentPriceByBeanId(bean.getId());
            currentPrice.ifPresent(bean::setCurrentPrice);
        }
        return beans;
    }
    public List<Bean> getBeansByTimeToKillRange(int minTimeToKill, int maxTimeToKill) {
        return beanRepository.findByTimeToKillBetween(minTimeToKill, maxTimeToKill);
    }
}
