package com.cy.travelking.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.constant.OrderStatus;
import com.cy.travelking.entity.AcceptOrder;
import com.cy.travelking.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<AcceptOrder, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

    public HomeAdapter(List<AcceptOrder> data) {
        super(data);
        addItemType(0, R.layout.item_home);
        addItemType(1, R.layout.item_chartered);
    }

    @Override
    protected void convert(BaseViewHolder helper, AcceptOrder item) {
/*              WAIT_APPROVAL_OR_PAY, // 待商家审核(商家发起的订单);待用户付款(用户发起的订单)

                WAIT_ACCEPT, // 待接单(已发布,但未被司机接单,且未进入派单流程的订单)

                AUTO, // 派单中(已发布,但未被司机接单,且触发派单条件的订单) - 每个司机对应有一个auto订单池

                ACCEPTED, // 已接单(司机已接单,乘客未上车)

                ON_THE_WAY, // 行程中(乘客已上车,未到达目的地)

                DONE, // 已抵达目的地

                SETTLED, // 后台财务人员点击已结算

                CANCEL_CONSOLE, // 已取消(后台)

                CANCEL_SHOP, // 已取消(商家执行)

                CANCEL_USER, // 取消用户订单(用户执行)*/

        if (helper.getItemViewType() == 1) {
            helper.setText(R.id.tv_accepted_time, TimeUtil.stampToDate(item.getStart_time()))
                    .setText(R.id.tv_phone, "备用手机号："+item.getContact_mobile())
                    .setText(R.id.tv_chartered_day, "包车天数：" + item.getDays() + '天')
                    .setGone(R.id.tv_chartered_day, item.getScene().equals("DAY_PRIVATE"))
                    .setText(R.id.tv_detail, item.getScene().equals("DAY_PRIVATE")?"包车详情":"线路详情")
                    .setText(R.id.tv_start_time, "出发日期：" + sdf.format(Long.parseLong(item.getStart_time())))
                    .setText(R.id.tv_start_place, item.getStart_place())
                    .setText(R.id.tv_price, "一口价：" + item.getPrice())
                    .setText(R.id.tv_id, "订单号：" + item.getId())
                    .setText(R.id.tv_status, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ? "未开始任务" : (OrderStatus.ON_THE_WAY.equals(item.getOrder_status()) ? "进行中任务" : "已取消"))
                    .setText(R.id.tv_action_order, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ? "申请改派" : "确认送达")
                    .addOnClickListener(R.id.tv_detail)
                    .addOnClickListener(R.id.rl_contact)
                    .addOnClickListener(R.id.rl_accept);
        } else {
            helper.setText(R.id.tv_status, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ? "未开始任务" : (OrderStatus.ON_THE_WAY.equals(item.getOrder_status()) ? "进行中任务" : "已取消"))
                    .setTextColor(R.id.tv_status, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ?
                            ContextCompat.getColor(mContext, R.color.textBlack) : OrderStatus.ON_THE_WAY.equals(item.getOrder_status()) ? ContextCompat.getColor(mContext, R.color.titleGreen) : ContextCompat.getColor(mContext, R.color.gray))
                    .setText(R.id.tv_phone, "备用手机号："+item.getContact_mobile())
                    .setText(R.id.tv_accepted_time, TimeUtil.stampToDate(item.getStart_time()))
                    .setText(R.id.tv_start_addr, item.getStart_place())
                    .setText(R.id.tv_end_addr, item.getTarget_place())
                    .setText(R.id.tv_air_no, "航班号：" + item.getAir_no())
                    .setText(R.id.tv_cat_time, "上车时间：" + TimeUtil.stampToDateForHome(item.getStart_time()))
                    .setText(R.id.tv_price, "一口价：" + item.getPrice())
                    .setText(R.id.tv_remark, "备注：" + item.getRemark())
                    .setText(R.id.tv_id, "订单号：" + item.getId())
                    .setText(R.id.tv_action_order, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ? "申请改派" : "确认送达")
                    .setVisible(R.id.tv_action_order, OrderStatus.ACCEPTED.equals(item.getOrder_status()) || OrderStatus.ON_THE_WAY.equals(item.getOrder_status()))
                    .setVisible(R.id.iv_nav, OrderStatus.ACCEPTED.equals(item.getOrder_status()) || OrderStatus.ON_THE_WAY.equals(item.getOrder_status()))
                    .setVisible(R.id.iv_action, OrderStatus.ACCEPTED.equals(item.getOrder_status()) || OrderStatus.ON_THE_WAY.equals(item.getOrder_status()))
                    .setImageResource(R.id.iv_action, OrderStatus.ACCEPTED.equals(item.getOrder_status()) ? R.mipmap.icon_change_order : R.mipmap.car)
                    .addOnClickListener(R.id.tv_call)
                    .addOnClickListener(R.id.tv_action_order)
                    .addOnClickListener(R.id.iv_nav);
        }
    }
}