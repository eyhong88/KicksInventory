package com.kicks.inventory;

import java.time.LocalDate;

public class ShoeSale {

    private int id;
    private String sku;
    private double price;
    private LocalDate saleDate;

    public ShoeSale(String sku, double price, LocalDate saleDate) {
        this.sku = sku;
        this.price = price;
        this.saleDate = saleDate;
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
