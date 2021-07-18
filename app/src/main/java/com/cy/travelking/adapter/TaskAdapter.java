package com.cy.travelking.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.entity.AcceptOrder;
import com.cy.travelking.entity.TaskOrder;
import com.cy.travelking.util.TimeUtil;

import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class TaskAdapter extends BaseQuickAdapter<TaskOrder.DataListBean, BaseViewHolder> {

    public TaskAdapter(List<TaskOrder.DataListBean> data) {
        super(R.layout.item_task, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskOrder.DataListBean item) {
        helper.setText(R.id.tv_accepted_time , TimeUtil.stampToDate(item.getStart_time()))
                .setText(R.id.tv_start_addr, item.getStart_place())
                .setText(R.id.tv_end_addr, item.getTarget_place())
                .setText(R.id.tv_air_no, "航班号：" + item.getAir_no())
                .setText(R.id.tv_cat_time, item.getStart_time()==null?"上车时间：":"上车时间：" + TimeUtil.stampToDateForHome(item.getStart_time()))
                .setText(R.id.tv_price, "一口价：" + item.getPrice())
                .setText(R.id.tv_remark, "备注：" + item.getRemark())
                .setText(R.id.tv_id, "订单号：" + item.getId())
                .addOnClickListener(R.id.rl_contact)
                .addOnClickListener(R.id.rl_accept);
    }
}
