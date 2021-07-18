package com.cy.travelking.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.entity.TaskOrder;
import com.cy.travelking.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class CharteredAdapter extends BaseQuickAdapter<TaskOrder.DataListBean, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

    public CharteredAdapter(List<TaskOrder.DataListBean> data) {
        super(R.layout.item_chartered, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskOrder.DataListBean item) {
        helper.setText(R.id.tv_accepted_time, TimeUtil.stampToDate(item.getStart_time()))
                .setGone(R.id.tv_phone, true)
                .setText(R.id.tv_chartered_day, "包车天数：" + item.getDays() + '天')
                .setGone(R.id.tv_chartered_day, item.getScene().equals("DAY_PRIVATE"))
                .setVisible(R.id.tv_detail, !item.getScene().equals("DAY_PRIVATE"))
                .setText(R.id.tv_start_time, "出发日期：" + sdf.format(Long.parseLong(item.getStart_time())))
                .setText(R.id.tv_start_place, item.getStart_place())
                .setText(R.id.tv_price, "一口价：" + item.getPrice())
                .setText(R.id.tv_status, "DAY_PRIVATE".equals(item.getScene()) ? "按天包车" : "线路包车")
                .setText(R.id.tv_id, "订单号：" + item.getId())
                .addOnClickListener(R.id.tv_detail)
                .addOnClickListener(R.id.rl_contact)
                .addOnClickListener(R.id.rl_accept);
    }
}
