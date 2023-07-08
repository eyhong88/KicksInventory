package com.kicks.inventory.dto;

import java.time.LocalDate;

public class ShoeSale {

    private int id;
    private String sku;
    private double price;
    private LocalDate saleDate;
    private int vendorId;
    private double totalPayout;
    private String brand;

    public ShoeSale(String sku, double price, LocalDate saleDate, int vendorId, double totalPayout) {
        this.sku = sku;
        this.price = price;
        this.saleDate = saleDate;
        this.vendorId = vendorId;
        this.totalPayout = totalPayout;
    }

    public ShoeSale(String sku, String brand, double price, double totalPayout){
        this.sku = sku;
        this.price = price;
        this.brand = brand;
        this.totalPayout = totalPayout;

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

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
}
