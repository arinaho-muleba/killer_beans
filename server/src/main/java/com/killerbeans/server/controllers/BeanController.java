package com.killerbeans.server.controllers;

import com.killerbeans.server.models.Bean;
import com.killerbeans.server.services.BeanService;
import com.killerbeans.server.services.PriceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
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
    public ResponseEntity<?> getBeansByTimeToKillRange(
            @RequestParam("minTimeToKill") Integer minTimeToKill,
            @RequestParam("maxTimeToKill") Integer maxTimeToKill) {

        if (minTimeToKill == null || maxTimeToKill == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("minTimeToKill and maxTimeToKill must be provided as integers");
        }

        if (minTimeToKill >= maxTimeToKill) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("minTimeToKill should be less than maxTimeToKill");
        }

        List<Bean> beans = beanService.getBeansByTimeToKillRange(minTimeToKill, maxTimeToKill);
        return ResponseEntity.ok(beans);
    }

    @GetMapping("/timeToKill/{minTimeToKill}")
    public ResponseEntity<List<Bean>> getBeansByMinTimeToKill(@PathVariable int minTimeToKill) {
        List<Bean> beans = beanService.getBeansByMinTimeToKill(minTimeToKill);
        if (beans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(beans, HttpStatus.OK);
        }
    }
}
