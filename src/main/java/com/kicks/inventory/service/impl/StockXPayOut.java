package com.kicks.inventory.service.impl;

import com.kicks.inventory.service.PayOut;

public class StockXPayOut implements PayOut {
    @Override
    public double calculate(double price, double fee, double tax) {
        return price - (price * fee) - 4;
    }
}
