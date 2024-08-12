package com.example.carrentalfinalproject;

import java.io.Serializable;

public class Store implements Serializable {
    private String name;
    private String address;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }


}
