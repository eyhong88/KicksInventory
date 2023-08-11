package com.kicks.inventory.service.impl;

import com.kicks.inventory.service.PayOut;

public class EbayPayOut implements PayOut {
    @Override
    public double calculate(double price, double fee, double tax) {
        double taxedPrice = price + tax;
        return price - (taxedPrice * fee);
    }
}
