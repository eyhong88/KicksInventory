package com.kicks.inventory.client;

import com.kicks.inventory.Shoe;
import com.kicks.inventory.ShoeSale;

import java.util.List;

public interface KicksClient {
    boolean ping();
    List<Shoe> getShoes();
    void addShoe(Shoe shoe);
    void updateShoe(Shoe shoe);
    Shoe getShoe(String sku);
    void addShoeSale(ShoeSale sale);
}
