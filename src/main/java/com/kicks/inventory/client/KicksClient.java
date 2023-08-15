package com.kicks.inventory.client;

import com.kicks.inventory.dto.*;

import java.util.List;

public interface KicksClient {
    boolean ping();
    List<Shoe> getShoes();
    void addShoe(Shoe shoe);
    void updateShoe(Shoe shoe);
    Shoe getShoe(String sku);
    void addShoeSale(ShoeSale sale);
    List<ShoeSale> getShoeSales();
    List<Vendor> getVendors();
}
