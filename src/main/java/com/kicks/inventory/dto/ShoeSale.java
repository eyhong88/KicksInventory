package com.kicks.inventory.dto;

import java.time.LocalDate;

public class ShoeSale {

    private int id;
    private String sku;
    private double price;
    private String saleDate;
    private int vendorId;
    private double totalPayout;
    private String brand;
    private String model;
    private String colorway;
    private int size;
    private double salePrice;
    private String styleCode;

    public ShoeSale(){}

    public ShoeSale(String sku, double salePrice, String saleDate, int vendorId, double totalPayout) {
        this.sku = sku;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.vendorId = vendorId;
        this.totalPayout = totalPayout;
    }

    public ShoeSale(int id, String sku, double salePrice, double totalPayout){
        this.id = id;
        this.sku = sku;
        this.salePrice = salePrice;
        this.totalPayout = totalPayout;

    }

    public ShoeSale(int id, String saleDate, int vendorId, String brand, String model, String colorway, int size, double price, String styleCode, String sku, double salePrice, double totalPayout){
        this.brand = brand;
        this.model = model;
        this.colorway = colorway;
        this.size = size;
        this.price = price;
        this.styleCode = styleCode;
        this.sku = sku;
        this.salePrice = salePrice;
        this.totalPayout = totalPayout;
        this.id = id;
        this.saleDate = saleDate;
        this.vendorId = vendorId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColorway() {
        return colorway;
    }

    public void setColorway(String colorway) {
        this.colorway = colorway;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
