package com.ltrix.jk.quiapo;

import java.io.Serializable;

/**
 * Created by jk on 4/3/15.
 */
public class ProviderElement implements Serializable {

    String  costValue;
    String  providerName;
    int quantity;

    public ProviderElement(String providerName,String costValue, int quantity) {
        this.costValue = costValue;
        this.quantity = quantity;
        this.providerName = providerName;
    }


}
