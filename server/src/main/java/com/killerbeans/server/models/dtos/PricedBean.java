package com.killerbeans.server.models.dtos;

import com.killerbeans.server.models.Bean;
import java.math.BigDecimal;

public class PricedBean extends Bean {
    private BigDecimal price;

    public  PricedBean(Bean bean, BigDecimal price){
        this.price = price;
        this.setId(bean.getId());
        this.setName(bean.getName());
        this.setQuantity(bean.getQuantity());
        this.setTimeToKill(bean.getTimeToKill());
    }

    @Override
    public String toString() {
        return "PricedBean{hello:'hi'}";
    }
}
