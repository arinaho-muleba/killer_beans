package com.killerbean.shell.model;

import java.math.BigDecimal;
import java.util.Date;


public class Price {
    private Long id;

    private BigDecimal price;

    private Date startDate;

    private Date endDate;

    private Integer beanId;
    public Price() {
    }
    public Price(BigDecimal price, Date startDate, Date endDate, Integer beanId) {
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.beanId = beanId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getBeanId() {
        return beanId;
    }

    public void setBeanId(Integer beanId) {
        this.beanId = beanId;
    }
}
