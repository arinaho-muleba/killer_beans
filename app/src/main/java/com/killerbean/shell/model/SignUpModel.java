package com.killerbean.shell.model;

public class SignUpModel {
    private String CustomerPhoneNumber;

    public SignUpModel(){}
    public void setPhoneNumber(String x){
        this.CustomerPhoneNumber = x;
    }

    public String getCustomerPhoneNumber(){
        return this.CustomerPhoneNumber;
    }
}
