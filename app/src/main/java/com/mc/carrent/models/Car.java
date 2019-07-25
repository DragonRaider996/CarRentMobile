package com.mc.carrent.models;

import java.io.Serializable;

//Car Model
public class Car implements Serializable {

    private int id;
    private String carModel;
    private double carRating;
    private double price;
    private double lat;
    private double lng;
    private String url;
    private String carDescription;
    private boolean rated;

    public Car(int id, String carModel, double carRating, double price, double lat, double lng, String url, String carDescription) {
        this.id = id;
        this.carModel = carModel;
        this.carRating = carRating;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.carDescription = carDescription;
        this.url = url;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
