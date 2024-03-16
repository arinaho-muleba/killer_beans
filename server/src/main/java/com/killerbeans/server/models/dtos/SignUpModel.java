package com.killerbeans.server.models.dtos;

public class SignUpModel {
    private String CustomerPhoneNumber;

    private Long id;

    public SignUpModel(){}
    public void setPhoneNumber(String x){
        this.CustomerPhoneNumber = x;
    }

    public String getCustomerPhoneNumber(){
        return this.CustomerPhoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
