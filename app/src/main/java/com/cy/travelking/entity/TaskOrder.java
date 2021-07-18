package com.cy.travelking.entity;

import java.io.Serializable;
import java.util.List;

public class TaskOrder implements Serializable {

    /**
     * page : 0
     * size : 20
     * total_pages : 1
     * total_elements : 6
     * data_list : [{"id":"48259732105854976","create_time":1560760067026,"update_time":1560760679838,"create_user":"47244988445097984","update_user":"47244988445097984","scene":"JIEJI","common_scene":"ORDER","order_source":"SHOP","order_status":"AUTO","order_status_int":null,"warning_status":"URGENT","username":"18559643214","mobile":"18559643214","contact":"","contact_mobile":"","start_time":1560767541788,"start_place":"福建省厦门市思明区梧村街道金榜南三路金榜公园","start_longitude":118.112011,"start_latitude":24.465027,"target_place":"福建省厦门市海沧区海沧街道石峰岩路","target_longitude":118.020001,"target_latitude":24.495336,"air_no":"","remark":"","car_config_id":"47241746690605056","kilo":null,"time":null,"price":1.37,"refund_fee":null,"shop_id":"48258729240428544","shop_name":"shopofmine","user_id":"47244988445097984","user_mobile":"18559643214","open_id":null,"user_wxname":null,"driver_user_id":null,"driver_user_name":null,"before_use_dispatch_hour":24,"auto_dispatch_time":1560760079807,"force_accept":null,"accepted_time":null,"execute_time":null,"done_time":null,"evaluate":null,"cancel_time":null,"settled_time":null,"wechat_order_id":null,"wechat_timestamp":null,"wechat_nonce_str":null,"wechat_pay_sign":null}]
     */

    private int page;
    private int size;
    private int total_pages;
    private int total_elements;
    private List<DataListBean> data_list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_elements() {
        return total_elements;
    }

    public void setTotal_elements(int total_elements) {
        this.total_elements = total_elements;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * id : 48259732105854976
         * create_time : 1560760067026
         * update_time : 1560760679838
         * create_user : 47244988445097984
         * update_user : 47244988445097984
         * scene : JIEJI
         * common_scene : ORDER
         * order_source : SHOP
         * order_status : AUTO
         * order_status_int : null
         * warning_status : URGENT
         * username : 18559643214
         * mobile : 18559643214
         * contact :
         * contact_mobile :
         * start_time : 1560767541788
         * start_place : 福建省厦门市思明区梧村街道金榜南三路金榜公园
         * start_longitude : 118.112011
         * start_latitude : 24.465027
         * target_place : 福建省厦门市海沧区海沧街道石峰岩路
         * target_longitude : 118.020001
         * target_latitude : 24.495336
         * air_no :
         * remark :
         * car_config_id : 47241746690605056
         * kilo : null
         * time : null
         * price : 1.37
         * refund_fee : null
         * shop_id : 48258729240428544
         * shop_name : shopofmine
         * user_id : 47244988445097984
         * user_mobile : 18559643214
         * open_id : null
         * user_wxname : null
         * driver_user_id : null
         * driver_user_name : null
         * before_use_dispatch_hour : 24.0
         * auto_dispatch_time : 1560760079807
         * force_accept : null
         * accepted_time : null
         * execute_time : null
         * done_time : null
         * evaluate : null
         * cancel_time : null
         * settled_time : null
         * wechat_order_id : null
         * wechat_timestamp : null
         * wechat_nonce_str : null
         * wechat_pay_sign : null
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
        private String order_status_int;
        private String warning_status;
        private String username;
        private String mobile;
        private String private_consume_id;
        private String contact;
        private String contact_mobile;
        private String start_time;
        private String start_place;
        private String start_longitude;
        private String start_latitude;
        private String target_place;
        private String target_longitude;
        private String target_latitude;
        private String air_no;
        private String remark;
        private String car_config_id;
        private String kilo;
        private String time;
        private String price;
        private String refund_fee;
        private String shop_id;
        private String shop_name;
        private String user_id;
        private String user_mobile;
        private String open_id;
        private String user_wxname;
        private String driver_user_id;
        private String driver_user_name;
        private String before_use_dispatch_hour;
        private String auto_dispatch_time;
        private String force_accept;
        private String accepted_time;
        private String execute_time;
        private String done_time;
        private String evaluate;
        private String cancel_time;
        private String settled_time;
        private String wechat_order_id;
        private String wechat_timestamp;
        private String wechat_nonce_str;
        private String wechat_pay_sign;
        private Integer days;

        public Integer getDays() { return days; }

        public void setDays(Integer days) {
            this.days = days;
        }

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

        public String getOrder_status_int() {
            return order_status_int;
        }

        public void setOrder_status_int(String order_status_int) {
            this.order_status_int = order_status_int;
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

        public String getStart_longitude() {
            return start_longitude;
        }

        public void setStart_longitude(String start_longitude) {
            this.start_longitude = start_longitude;
        }

        public String getStart_latitude() {
            return start_latitude;
        }

        public void setStart_latitude(String start_latitude) {
            this.start_latitude = start_latitude;
        }

        public String getTarget_place() {
            return target_place;
        }

        public void setTarget_place(String target_place) {
            this.target_place = target_place;
        }

        public String getTarget_longitude() {
            return target_longitude;
        }

        public void setTarget_longitude(String target_longitude) {
            this.target_longitude = target_longitude;
        }

        public String getTarget_latitude() {
            return target_latitude;
        }

        public void setTarget_latitude(String target_latitude) {
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

        public String getKilo() {
            return kilo;
        }

        public void setKilo(String kilo) {
            this.kilo = kilo;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRefund_fee() {
            return refund_fee;
        }

        public void setRefund_fee(String refund_fee) {
            this.refund_fee = refund_fee;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getUser_wxname() {
            return user_wxname;
        }

        public void setUser_wxname(String user_wxname) {
            this.user_wxname = user_wxname;
        }

        public String getDriver_user_id() {
            return driver_user_id;
        }

        public void setDriver_user_id(String driver_user_id) {
            this.driver_user_id = driver_user_id;
        }

        public String getDriver_user_name() {
            return driver_user_name;
        }

        public void setDriver_user_name(String driver_user_name) {
            this.driver_user_name = driver_user_name;
        }

        public String getBefore_use_dispatch_hour() {
            return before_use_dispatch_hour;
        }

        public void setBefore_use_dispatch_hour(String before_use_dispatch_hour) {
            this.before_use_dispatch_hour = before_use_dispatch_hour;
        }

        public String getAuto_dispatch_time() {
            return auto_dispatch_time;
        }

        public void setAuto_dispatch_time(String auto_dispatch_time) {
            this.auto_dispatch_time = auto_dispatch_time;
        }

        public String getForce_accept() {
            return force_accept;
        }

        public void setForce_accept(String force_accept) {
            this.force_accept = force_accept;
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

        public String getDone_time() {
            return done_time;
        }

        public void setDone_time(String done_time) {
            this.done_time = done_time;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }

        public String getCancel_time() {
            return cancel_time;
        }

        public void setCancel_time(String cancel_time) {
            this.cancel_time = cancel_time;
        }

        public String getSettled_time() {
            return settled_time;
        }

        public void setSettled_time(String settled_time) {
            this.settled_time = settled_time;
        }

        public String getWechat_order_id() {
            return wechat_order_id;
        }

        public void setWechat_order_id(String wechat_order_id) {
            this.wechat_order_id = wechat_order_id;
        }

        public String getWechat_timestamp() {
            return wechat_timestamp;
        }

        public void setWechat_timestamp(String wechat_timestamp) {
            this.wechat_timestamp = wechat_timestamp;
        }

        public String getWechat_nonce_str() {
            return wechat_nonce_str;
        }

        public void setWechat_nonce_str(String wechat_nonce_str) {
            this.wechat_nonce_str = wechat_nonce_str;
        }

        public String getWechat_pay_sign() {
            return wechat_pay_sign;
        }

        public void setWechat_pay_sign(String wechat_pay_sign) {
            this.wechat_pay_sign = wechat_pay_sign;
        }
    }
}
