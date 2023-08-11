package com.kicks.inventory.factory;

import com.kicks.inventory.dto.Vendor;
import com.kicks.inventory.service.PayOut;
import com.kicks.inventory.service.impl.EbayPayOut;
import com.kicks.inventory.service.impl.StockXPayOut;

public class PayOutFactory {
    public static PayOut getInstance(Vendor vendor){
        if(vendor.getId() == 1)
            return new EbayPayOut();
        else return new StockXPayOut();
    }
}
