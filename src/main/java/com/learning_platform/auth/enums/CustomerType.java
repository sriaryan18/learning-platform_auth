package com.learning_platform.auth.enums;


public enum CustomerType {
    PRIVATE("PRIVATE"),
    GROUP("GROUP");

    private final String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}
