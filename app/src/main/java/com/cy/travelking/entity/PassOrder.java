package com.cy.travelking.entity;

import java.util.List;

public class PassOrder {

    private String time;
    private List<String> ids;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
