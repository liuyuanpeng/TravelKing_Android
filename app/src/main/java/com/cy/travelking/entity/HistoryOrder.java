package com.cy.travelking.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Author: CY
 * Date: 2019/5/22
 */
public class HistoryOrder implements Serializable {

    /**
     * data_list : [{"accepted_time":0,"air_no":"string","auto_dispatch_time":0,"cancel_time":0,"car_config_id":"string","common_scene":"NOW","contact":"string","create_time":0,"create_user":"string","done_time":0,"driver_user_id":"string","execute_time":0,"id":"string","mobile":"string","order_source":"SHOP","order_status":"WAIT_APPROVAL_OR_PAY","price":0,"remark":"string","scene":"JIEJI","settled_time":0,"shop_id":"string","start_latitude":0,"start_longitude":0,"start_place":"string","start_time":0,"target_latitude":0,"target_longitude":0,"target_place":"string","update_time":0,"update_user":"string","user_id":"string","username":"string","warning_status":"NORMAL","wechat_order_id":"string"}]
     * page : 0
     * size : 0
     * total_elements : 0
     * total_pages : 0
     */

    private int page;
    private int size;
    private int total_elements;
    private int total_pages;
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

    public int getTotal_elements() {
        return total_elements;
    }

    public void setTotal_elements(int total_elements) {
        this.total_elements = total_elements;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean implements Serializable, MultiItemEntity {
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
         * wechat_order_id : string
         */

        private String accepted_time;
        private String air_no;
        private String auto_dispatch_time;
        private String cancel_time;
        private String car_config_id;
        private String common_scene;
        private String contact;
        private String create_time;
        private String create_user;
        private String done_time;
        private String driver_user_id;
        private String execute_time;
        private String id;
        private String mobile;
        private String order_source;
        private String order_status;
        private String price;
        private String remark;
        private String scene;
        private String settled_time;
        private String shop_id;
        private String start_latitude;
        private String start_longitude;
        private String start_place;
        private String start_time;
        private String target_latitude;
        private String target_longitude;
        private String target_place;
        private String update_time;
        private String update_user;
        private String user_id;
        private String username;
        private String warning_status;
        private String wechat_order_id;

        public String getAccepted_time() {
            return accepted_time;
        }

        public void setAccepted_time(String accepted_time) {
            this.accepted_time = accepted_time;
        }

        public String getAir_no() {
            return air_no;
        }

        public void setAir_no(String air_no) {
            this.air_no = air_no;
        }

        public String getAuto_dispatch_time() {
            return auto_dispatch_time;
        }

        public void setAuto_dispatch_time(String auto_dispatch_time) {
            this.auto_dispatch_time = auto_dispatch_time;
        }

        public String getCancel_time() {
            return cancel_time;
        }

        public void setCancel_time(String cancel_time) {
            this.cancel_time = cancel_time;
        }

        public String getCar_config_id() {
            return car_config_id;
        }

        public void setCar_config_id(String car_config_id) {
            this.car_config_id = car_config_id;
        }

        public String getCommon_scene() {
            return common_scene;
        }

        public void setCommon_scene(String common_scene) {
            this.common_scene = common_scene;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getDone_time() {
            return done_time;
        }

        public void setDone_time(String done_time) {
            this.done_time = done_time;
        }

        public String getDriver_user_id() {
            return driver_user_id;
        }

        public void setDriver_user_id(String driver_user_id) {
            this.driver_user_id = driver_user_id;
        }

        public String getExecute_time() {
            return execute_time;
        }

        public void setExecute_time(String execute_time) {
            this.execute_time = execute_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }

        public String getSettled_time() {
            return settled_time;
        }

        public void setSettled_time(String settled_time) {
            this.settled_time = settled_time;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getStart_latitude() {
            return start_latitude;
        }

        public void setStart_latitude(String start_latitude) {
            this.start_latitude = start_latitude;
        }

        public String getStart_longitude() {
            return start_longitude;
        }

        public void setStart_longitude(String start_longitude) {
            this.start_longitude = start_longitude;
        }

        public String getStart_place() {
            return start_place;
        }

        public void setStart_place(String start_place) {
            this.start_place = start_place;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getTarget_latitude() {
            return target_latitude;
        }

        public void setTarget_latitude(String target_latitude) {
            this.target_latitude = target_latitude;
        }

        public String getTarget_longitude() {
            return target_longitude;
        }

        public void setTarget_longitude(String target_longitude) {
            this.target_longitude = target_longitude;
        }

        public String getTarget_place() {
            return target_place;
        }

        public void setTarget_place(String target_place) {
            this.target_place = target_place;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getWarning_status() {
            return warning_status;
        }

        public void setWarning_status(String warning_status) {
            this.warning_status = warning_status;
        }

        public String getWechat_order_id() {
            return wechat_order_id;
        }

        public void setWechat_order_id(String wechat_order_id) {
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
}
