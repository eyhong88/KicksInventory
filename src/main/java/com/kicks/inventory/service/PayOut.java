package com.kicks.inventory.service;

public interface PayOut {
    double calculate(double price, double fee, double tax);
}
