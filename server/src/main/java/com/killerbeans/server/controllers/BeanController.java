package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.services.BeanService;
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

    @Autowired
    public BeanController(BeanService beanService){
        this.beanService = beanService;
    }

    @GetMapping
    public List<Bean> getAllBeans(){
        return  beanService.getAllBeans();
    }

    @GetMapping("/getByTimeToKillRange")
    public List<Bean> getBeansByTimeToKillRange(
            @RequestParam("minTimeToKill") int minTimeToKill,
            @RequestParam("maxTimeToKill") int maxTimeToKill) {
        return beanService.getBeansByTimeToKillRange(minTimeToKill, maxTimeToKill);
    }


}
