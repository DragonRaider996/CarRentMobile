package com.mc.carrent;

public class Car {

    private String carModel;
    private double carRating;
    private double price;
    private double lat;
    private double lng;

    public Car(String carModel, double carRating, double price, double lat, double lng) {
        this.carModel = carModel;
        this.carRating = carRating;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getCarRating() {
        return carRating;
    }

    public void setCarRating(double carRating) {
        this.carRating = carRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
