package com.cy.travelking.entity;

import java.util.List;

public class SettledTotal {

    private float total;
    private List<SettledDetail> travel_order_list;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<SettledDetail> getTravel_order_list() {
        return travel_order_list;
    }

    public void setTravel_order_list(List<SettledDetail> travel_order_list) {
        this.travel_order_list = travel_order_list;
    }
}
