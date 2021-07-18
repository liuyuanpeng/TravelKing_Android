package com.cy.travelking.entity;

import java.io.Serializable;

/**
 * Author: CY
 * Date: 2019/5/19
 */
public class OpenOrder implements Serializable {

    /**
     * accepted_time : 0
     * air_no : string
     * auto_dispatch_time : 0
     * cancel_time : 0
     * car_config_id : string
     * common_scene : NOW
     * contact : string
     * create_time : 0
     * create_user : string
     * done_time : 0
     * driver_user_id : string
     * execute_time : 0
     * id : string
     * mobile : string
     * order_source : SHOP
     * order_status : WAIT_APPROVAL_OR_PAY
     * price : 0
     * remark : string
     * scene : JIEJI
     * settled_time : 0
     * shop_id : string
     * start_latitude : 0
     * start_longitude : 0
     * start_place : string
     * start_time : 0
     * target_latitude : 0
     * target_longitude : 0
     * target_place : string
     * update_time : 0
     * update_user : string
     * user_id : string
     * username : string
     * warning_status : NORMAL
     */

    private String id;
    private long create_time;
    private long update_time;
    private String create_user;
    private String update_user;
    private String scene;
    private String common_scene;
    private String order_source;
    private String order_status;
    private String warning_status;
    private String username;
    private String mobile;
    private String contact;
    private long start_time;
    private String start_place;
    private double start_longitude;
    private double start_latitude;
    private String target_place;
    private double target_longitude;
    private double target_latitude;
    private String air_no;
    private String remark;
    private String car_config_id;
    private float price;
    private String shop_id;
    private String user_id;
    private String driver_user_id;
    private long auto_dispatch_time;
    private long accepted_time;
    private long cancel_time;
    private long done_time;
    private long execute_time;
    private long settled_time;
    private float kilo;

    public float getKilo() {
        return kilo;
    }

    public void setKilo(float kilo) {
        this.kilo = kilo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getCommon_scene() {
        return common_scene;
    }

    public void setCommon_scene(String common_scene) {
        this.common_scene = common_scene;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getWarning_status() {
        return warning_status;
    }

    public void setWarning_status(String warning_status) {
        this.warning_status = warning_status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getStart_place() {
        return start_place;
    }

    public void setStart_place(String start_place) {
        this.start_place = start_place;
    }

    public double getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getTarget_place() {
        return target_place;
    }

    public void setTarget_place(String target_place) {
        this.target_place = target_place;
    }

    public double getTarget_longitude() {
        return target_longitude;
    }

    public void setTarget_longitude(double target_longitude) {
        this.target_longitude = target_longitude;
    }

    public double getTarget_latitude() {
        return target_latitude;
    }

    public void setTarget_latitude(double target_latitude) {
        this.target_latitude = target_latitude;
    }

    public String getAir_no() {
        return air_no;
    }

    public void setAir_no(String air_no) {
        this.air_no = air_no;
    }

    public String getRemark() {
        if(remark != null) {
            return remark;
        }
        return "";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCar_config_id() {
        return car_config_id;
    }

    public void setCar_config_id(String car_config_id) {
        this.car_config_id = car_config_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDriver_user_id() {
        return driver_user_id;
    }

    public void setDriver_user_id(String driver_user_id) {
        this.driver_user_id = driver_user_id;
    }

    public long getAuto_dispatch_time() {
        return auto_dispatch_time;
    }

    public void setAuto_dispatch_time(long auto_dispatch_time) {
        this.auto_dispatch_time = auto_dispatch_time;
    }

    public long getAccepted_time() {
        return accepted_time;
    }

    public void setAccepted_time(long accepted_time) {
        this.accepted_time = accepted_time;
    }

    public long getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(long cancel_time) {
        this.cancel_time = cancel_time;
    }

    public long getDone_time() {
        return done_time;
    }

    public void setDone_time(long done_time) {
        this.done_time = done_time;
    }

    public long getExecute_time() {
        return execute_time;
    }

    public void setExecute_time(long execute_time) {
        this.execute_time = execute_time;
    }

    public long getSettled_time() {
        return settled_time;
    }

    public void setSettled_time(long settled_time) {
        this.settled_time = settled_time;
    }
}
