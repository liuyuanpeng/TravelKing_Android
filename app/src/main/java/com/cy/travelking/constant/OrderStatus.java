package com.cy.travelking.constant;

public class OrderStatus {

    public final static String WAIT_APPROVAL_OR_PAY = "WAIT_APPROVAL_OR_PAY";// 待商家审核(商家发起的订单);待用户付款(用户发起的订单)
    public final static String WAIT_ACCEPT = "WAIT_ACCEPT";// 待接单(已发布,但未被司机接单,且未进入派单流程的订单)
    public final static String AUTO = "AUTO";// 派单中(已发布,但未被司机接单,且触发派单条件的订单) - 每个司机对应有一个auto订单池
    public final static String ACCEPTED = "ACCEPTED";// 已接单(司机已接单,乘客未上车)
    public final static String ON_THE_WAY = "ON_THE_WAY";// 行程中(乘客已上车,未到达目的地)
    public final static String DONE = "DONE";// 已抵达目的地
    public final static String SETTLED = "SETTLED";// 后台财务人员点击已结算
    public final static String CANCEL_CONSOLE = "CANCEL_CONSOLE";// 已取消(后台)
    public final static String CANCEL_SHOP = "CANCEL_SHOP";// 已取消(商家执行)
    public final static String CANCEL_USER = "CANCEL_USER";// 取消用户订单(用户执行)

}
