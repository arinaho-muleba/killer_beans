package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.models.dtos.PricedBean;
import com.killerbeans.server.services.BeanService;
import com.killerbeans.server.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/bean")
public class BeanController {

    private final BeanService beanService;
    private  final PriceService priceService;

    @Autowired
    public BeanController(BeanService beanService, PriceService priceService){
        this.beanService = beanService;
        this.priceService = priceService;
    }
    @Autowired


    @GetMapping
    public List<Bean> getAllBeans(){
        return beanService.getAllBeans();
    }

    @GetMapping("/getByTimeToKillRange")
    public List<Bean> getBeansByTimeToKillRange(
            @RequestParam("minTimeToKill") int minTimeToKill,
            @RequestParam("maxTimeToKill") int maxTimeToKill) {
        return beanService.getBeansByTimeToKillRange(minTimeToKill, maxTimeToKill);
    }


}