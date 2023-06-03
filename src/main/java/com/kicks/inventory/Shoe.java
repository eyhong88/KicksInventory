package com.kicks.inventory;

public class Shoe {
    private double size;
    private String brand;
    private String model;
    private double price;
    private double estSalePrice;
    private int quantity;
    private String styleCode;
    private String sku;

    private String colorway;

    public Shoe(double estSalePrice, String colorway, double size, String model, String brand, double price, int quantity, String styleCode, String sku) {
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.estSalePrice = estSalePrice;
        this.quantity = quantity;
        this.styleCode = styleCode;
        this.sku = sku;
        this.size = size;
        this.colorway = colorway;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model){
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getEstSalePrice() {
        return estSalePrice;
    }

    public void setEstSalePrice(double estSalePrice) {
        this.estSalePrice = estSalePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStyleCode() {
        return styleCode;
    }
    public void setStyleCode(String styleCode){
        this.styleCode = styleCode;
    }
    public void setSku(String sku){
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }
    public String getColorway() {
        return colorway;
    }

    public void setColorway(String colorway){
        this.colorway = colorway;
    }

    public double getSize(){
        return size;
    }

    public void setSize(double size){
        this.size = size;
    }
}