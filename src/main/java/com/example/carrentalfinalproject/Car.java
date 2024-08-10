package com.example.carrentalfinalproject;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    public int id;
    public String brand;
    public String model;
    public int year;
    public String color;
    public int price;
    public int img;
    public String desc;



    public Car(int id, String brand, String model, int year, String color, int price,int img,String desc) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.price = price;
        this.img = img;
        this.desc = desc;

    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public int getimg() {
        return img;
    }

    public String getDesc(){return desc;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                year == car.year &&
                price == car.price &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(color, car.color)&&
                Objects.equals(desc,car.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, year, color, price,desc);
    }


}
