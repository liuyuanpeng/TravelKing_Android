package com.cy.travelking.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Author: CY
 * Date: 2019/5/22
 */
public class AcceptOrder implements Serializable, MultiItemEntity {

    /**
     * id : 45739675841724416
     * create_time : 1558356754091
     * update_time : 1558488378951
     * create_user : 44891180405620736
     * update_user : 44902172332130304
     * scene : JIEJI
     * common_scene : NOW
     * order_source : USER
     * order_status : ACCEPTED
     * warning_status : URGENT
     * username : 陈训
     * mobile : 15860746397
     * contact : 陈训
     * start_time : 1558367477000
     * start_place : 厦门高崎国际机场
     * start_longitude : 118.143639
     * start_latitude : 24.545038
     * target_place : 厦门北站
     * target_longitude : 118.080553
     * target_latitude : 24.642666
     * air_no : 厦航MF8117
     * remark : 备注信息
     * car_config_id : 45738567037616128
     * price : 25.22
     * shop_id : null
     * user_id : 44891180405620736
     * driver_user_id : 44902172332130304
     * auto_dispatch_time : 1558423283169
     * accepted_time : 1558488378908
     * execute_time : null
     * done_time : null
     * cancel_time : null
     * settled_time : null
     * wechat_order_id : null
     */

    private String id;
    private String create_time;
    private String update_time;
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
    private String contact_mobile;
    private String start_time;
    private String start_place;
    private Integer days;
    private double start_longitude;
    private double start_latitude;
    private String target_place;
    private double target_longitude;
    private double target_latitude;
    private String air_no;
    private String remark;
    private String car_config_id;
    private String price;
    private Object shop_id;
    private String user_id;
    private String driver_user_id;
    private String auto_dispatch_time;
    private String accepted_time;
    private String execute_time;
    private String private_consume_id;
    private Object done_time;
    private Object cancel_time;
    private Object settled_time;
    private Object wechat_order_id;

    public String getPrivate_consume_id() {
        return private_consume_id;
    }

    public void setPrivate_consume_id(String private_consume_id) {
        this.private_consume_id = private_consume_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDays() { return days; }

    public void setDays(Integer days) { this.days = days; }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
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

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getShop_id() {
        return shop_id;
    }

    public void setShop_id(Object shop_id) {
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

    public String getAuto_dispatch_time() {
        return auto_dispatch_time;
    }

    public void setAuto_dispatch_time(String auto_dispatch_time) {
        this.auto_dispatch_time = auto_dispatch_time;
    }

    public String getAccepted_time() {
        return accepted_time;
    }

    public void setAccepted_time(String accepted_time) {
        this.accepted_time = accepted_time;
    }

    public String getExecute_time() {
        return execute_time;
    }

    public void setExecute_time(String execute_time) {
        this.execute_time = execute_time;
    }

    public Object getDone_time() {
        return done_time;
    }

    public void setDone_time(Object done_time) {
        this.done_time = done_time;
    }

    public Object getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Object cancel_time) {
        this.cancel_time = cancel_time;
    }

    public Object getSettled_time() {
        return settled_time;
    }

    public void setSettled_time(Object settled_time) {
        this.settled_time = settled_time;
    }

    public Object getWechat_order_id() {
        return wechat_order_id;
    }

    public void setWechat_order_id(Object wechat_order_id) {
        this.wechat_order_id = wechat_order_id;
    }

    @Override
    public int getItemType() {
        if ("DAY_PRIVATE".equals(scene) || "ROAD_PRIVATE".equals(scene)) {
            return 1;
        } else {
            return 0;
        }
    }
}
