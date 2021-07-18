package com.cy.travelking.entity;

public class Driver {

    private long create_time;
    private long update_time;
    private String create_user;
    private int evaluate;
    private int total_evaluate;
    private int total_evaluate_count;
    private String id;
    private double latitude;
    private double longitude;
    private String status;
    private String update_user;
    private String user_id;


    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public int getTotal_evaluate() {
        return total_evaluate;
    }

    public void setTotal_evaluate(int total_evaluate) {
        this.total_evaluate = total_evaluate;
    }

    public int getTotal_evaluate_count() {
        return total_evaluate_count;
    }

    public void setTotal_evaluate_count(int total_evaluate_count) {
        this.total_evaluate_count = total_evaluate_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
