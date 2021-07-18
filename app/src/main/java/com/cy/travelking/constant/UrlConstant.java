package com.cy.travelking.constant;

/**
 * Created by AAA on 2019/1/9
 */
public class UrlConstant {

    public final static int PAGE_SIZE = 20;
    public final static String BASE_URL = "http://tms.kingtrip.vip";
//    public final static String BASE_URL = "https://apis.kingtrip.vip";
    public final static String ABOUT_DRIVER = "http://www.kingtrip.vip/html/aboutdriver.html";
    public final static String CLAUSE_DRIVER = "http://www.kingtrip.vip/html/clausedriver.html";
//    public final static String BASE_URL = "https://apis.junyaji.com";
//    public final static String BASE_URL = "http://47.105.216.34:8080";
    public final static String CAPTCHA = BASE_URL+"/v5/session/captcha";
    public final static String CAPTCHA_LOGIN = BASE_URL+"/v5/user/captcha_login";
    public final static String USER_UPDATE = BASE_URL+"/v5/user/update";
    public final static String UPLOAD = BASE_URL+"/v5/file/local/upload";
    public final static String GET_DRIVER = BASE_URL+"/v5/travel/driver/get";
    public final static String OPEN_ORDER = BASE_URL+"/v5/travel/order/driver/now";//司机立即叫车(抢单)
    public final static String ACCEPT_ORDER = BASE_URL+"/v5/travel/driver/accept_order";//司机接单
    public final static String  TASK_LIST= BASE_URL+"/v5/travel/order/driver/task_list_test";//司机任务列表
//    public final static String  TASK_LIST= BASE_URL+"/v5/travel/order/driver/task_list";//司机任务列表
    public final static String WAIT_ORDER = BASE_URL+"/v5/travel/order/driver/order_list";//司机待接单列表
    public final static String ACCEPTER_ORDER = BASE_URL+"/v5/travel/order/driver/accepted_list";//司机已接单列表
    public final static String CANCELED_ORDER = BASE_URL+"/v5/travel//driver/driver_cancel_page"; //司机已接的已取消订单
    public final static String HISTORY_ORDER = BASE_URL+"/v5/travel/driver/driver_order_page";//司机历史订单
    public final static String SETTLED_LIST = BASE_URL+"/v5/travel/order/driver/settled_list";//司机已结算订单列表
    public final static String SYNC = BASE_URL+"/v5/travel/driver/sync";//司机历史订单
    public final static String CHANGE_ORDER = BASE_URL+"/v5/travel/driver/change_order";//司机申请改派订单
    public final static String GET_ORDER = BASE_URL+"/v5/travel/order/get"; //获取订单详情
    public final static String EXECUTE_ORDER = BASE_URL+"/v5/travel/driver/execute_order";//司机到达约定地点,开始订单
    public final static String DONE_ORDER = BASE_URL+"/v5/travel/driver/done_order";//司机到达约定地点,开始订单
    public final static String CHECK = BASE_URL+"/v5/console/user/check";//手机号码或邮箱是否注册
    public final static String GET_PRIVATE_CONSUME = BASE_URL+"/v5/travel/private_consume/get";//获取路线详情


}
