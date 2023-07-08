package com.kicks.inventory;

public class Vendor {
    private int id;
    private String vendorName;
    private double vendorFee;

    public Vendor(int id, String vendorName, double vendorFee){
        this.id = id;
        this.vendorName = vendorName;
        this.vendorFee = vendorFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public double getVendorFee() {
        return vendorFee;
    }

    public void setVendorFee(double vendorFee) {
        this.vendorFee = vendorFee;
    }
}
