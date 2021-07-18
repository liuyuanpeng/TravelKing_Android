package com.cy.travelking.entity;

import java.util.List;

public class RouteDetail {


    /**
     * private_consume : {"create_time":1571208436304,"update_time":1571214007195,"create_user":"50151145122824192","update_user":"50151145122824192","id":"59215641369903104","weight":0,"scene":"DAY_PRIVATE","common_scene":"ORDER","name":"火车站","tag":"火车站,a1,a2,a3","show_days":4,"start_time":1571241600000,"end_time":1575215999999,"price":0.1,"reason":"推荐理由。。。","images":"http://118.190.59.41/travel_file/59221480349630464.jpg","promise":"承诺。。。。","price_include":"费用包含。。。。","price_exclude":"费用不含。。。。","advance_notice":"预订须知。。。。","refund_rule":"退订规则。。。"}
     * roads : [{"name":"厦门火车站","day_road":"行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2","images":"http://118.190.59.41/travel_file/59215590498238464.jpg,http://118.190.59.41/travel_file/59215597625409536.jpg","play_time":"2"},{"name":"火车站","day_road":"行程1","images":"http://118.190.59.41/travel_file/59215537085874176.png,http://118.190.59.41/travel_file/59215540330168320.png","play_time":"1.5"}]
     */

    private PrivateConsumeBean private_consume;
    private List<RoadsBean> roads;

    public PrivateConsumeBean getPrivate_consume() {
        return private_consume;
    }

    public void setPrivate_consume(PrivateConsumeBean private_consume) {
        this.private_consume = private_consume;
    }

    public List<RoadsBean> getRoads() {
        return roads;
    }

    public void setRoads(List<RoadsBean> roads) {
        this.roads = roads;
    }

    public static class PrivateConsumeBean {
        /**
         * create_time : 1571208436304
         * update_time : 1571214007195
         * create_user : 50151145122824192
         * update_user : 50151145122824192
         * id : 59215641369903104
         * weight : 0
         * scene : DAY_PRIVATE
         * common_scene : ORDER
         * name : 火车站
         * tag : 火车站,a1,a2,a3
         * show_days : 4
         * start_time : 1571241600000
         * end_time : 1575215999999
         * price : 0.1
         * reason : 推荐理由。。。
         * images : http://118.190.59.41/travel_file/59221480349630464.jpg
         * promise : 承诺。。。。
         * price_include : 费用包含。。。。
         * price_exclude : 费用不含。。。。
         * advance_notice : 预订须知。。。。
         * refund_rule : 退订规则。。。
         */

        private long create_time;
        private long update_time;
        private String create_user;
        private String update_user;
        private String id;
        private int weight;
        private String scene;
        private String common_scene;
        private String name;
        private String tag;
        private int show_days;
        private long start_time;
        private long end_time;
        private double price;
        private String reason;
        private String images;
        private String promise;
        private String price_include;
        private String price_exclude;
        private String advance_notice;
        private String refund_rule;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getShow_days() {
            return show_days;
        }

        public void setShow_days(int show_days) {
            this.show_days = show_days;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getPromise() {
            return promise;
        }

        public void setPromise(String promise) {
            this.promise = promise;
        }

        public String getPrice_include() {
            return price_include;
        }

        public void setPrice_include(String price_include) {
            this.price_include = price_include;
        }

        public String getPrice_exclude() {
            return price_exclude;
        }

        public void setPrice_exclude(String price_exclude) {
            this.price_exclude = price_exclude;
        }

        public String getAdvance_notice() {
            return advance_notice;
        }

        public void setAdvance_notice(String advance_notice) {
            this.advance_notice = advance_notice;
        }

        public String getRefund_rule() {
            return refund_rule;
        }

        public void setRefund_rule(String refund_rule) {
            this.refund_rule = refund_rule;
        }
    }

    public static class RoadsBean {
        /**
         * 	"create_time": 1571302122398,
         * 			"update_time": 1571302122398,
         * 			"create_user": "50151145122824192",
         * 			"update_user": "50151145122824192",
         * 			"id": "59313878359605248",
         * 			"weight": 0,
         * 			"private_consume_id": "59215641369903104",
         * 			"name": "厦门火车站",
         * 			"start_time": 1571270425000,
         * 			"day_road": "行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2行程2",
         * 			"images": "http://www.kingtrip.vip/travel_file/59313850080559104.jpg,http://www.kingtrip.vip/travel_file/59313853734846464.png",
         * 			"play_time": "2"
         */

        private int weight;
        private String id;
        private String private_consume_id;
        private String name;
        private String day_road;
        private String images;
        private String play_time;
        private String create_user;
        private String update_user;
        private long start_time;
        private long create_time;
        private long update_time;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrivate_consume_id() {
            return private_consume_id;
        }

        public void setPrivate_consume_id(String private_consume_id) {
            this.private_consume_id = private_consume_id;
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

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDay_road() {
            return day_road;
        }

        public void setDay_road(String day_road) {
            this.day_road = day_road;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getPlay_time() {
            return play_time;
        }

        public void setPlay_time(String play_time) {
            this.play_time = play_time;
        }
    }
}
