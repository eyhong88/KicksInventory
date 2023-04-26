package org.example;

public class Shoe {
    private double size;
    private String brand;
    private String name;
    private double price;
    private int quantity;
    private String styleCode;
    private String sku;

    private String colorway;

    public Shoe(String colorway, double size, String name, String brand, double price, int quantity, String styleCode, String sku) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.styleCode = styleCode;
        this.sku = sku;
        this.size = size;
        this.colorway = colorway;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
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