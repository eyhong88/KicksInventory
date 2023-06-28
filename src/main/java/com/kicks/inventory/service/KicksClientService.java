package com.kicks.inventory.service;

import com.kicks.inventory.Shoe;
import com.kicks.inventory.ShoeSale;
import com.kicks.inventory.client.KicksClient;
import com.kicks.inventory.client.KicksClientImpl;
import com.kicks.inventory.dao.ShoesDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class KicksClientService {

    private final KicksClient client = KicksClientImpl.getInstance();
    private static KicksClientService instance;
    private ShoesDAO dao = ShoesDAO.getInstance();
    private Boolean ping = null;

    public static KicksClientService getInstance(){
        if(instance == null){
            instance = new KicksClientService();
        }
        return instance;
    }

    private boolean ping() {
        if(null == ping)
            ping = client.ping();

        return ping;
    }

    public void loadShoes() {
        List<Shoe> result;

        if(ping()) {
            result = client.getShoes();
            dao.getShoes().setAll(result);
        }
        else dao.loadShoes();
    }

    public ObservableList<Shoe> getShoes(){
        return dao.getShoes();
    }

    public void addShoe(Shoe shoe) {

        if(ping()) {
            client.addShoe(shoe);

            // Add the shoe to the list of shoes
            dao.getShoes().add(shoe);
        }
        else dao.addShoe(shoe);
    }

    public void updateShoe(Shoe shoe) {
        if(ping()) {
            client.updateShoe(shoe);
        }
        else dao.updateShoe(shoe);
    }

    public Shoe getShoe(String sku) {
        Shoe result;
        if(ping()) result = client.getShoe(sku);
        else result = dao.getShoe(sku);

        return result;
    }

    public void addShoeSale(ShoeSale sale){
        if(ping()) client.addShoeSale(sale);
        else dao.addShoeSale(sale);
    }
}
