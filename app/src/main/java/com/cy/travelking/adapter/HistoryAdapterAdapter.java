package com.cy.travelking.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.entity.HistoryOrder;
import com.cy.travelking.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class HistoryAdapterAdapter extends BaseMultiItemQuickAdapter<HistoryOrder.DataListBean, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");

    public HistoryAdapterAdapter(List<HistoryOrder.DataListBean> data) {
        super(data);
        addItemType(0, R.layout.item_history_order);
        addItemType(1, R.layout.item_history_chartered);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOrder.DataListBean item) {
        if (helper.getItemViewType() == 1) {
            helper.setText(R.id.tv_start_time, "出发日期：" + sdf.format(Long.parseLong(item.getStart_time())))
                    .setText(R.id.tv_start_place, item.getStart_place())
                    .setText(R.id.tv_id, "订单号：" + item.getId())
                    .setText(R.id.tv_price, "一口价：" + item.getPrice());
        } else {
            helper.setText(R.id.tv_start, item.getStart_place())
                    .setText(R.id.tv_end, item.getTarget_place())
                    .setText(R.id.tv_air_no, "航班号：" + item.getAir_no())
                    .setText(R.id.tv_cat_time, item.getStart_time() == null ? "上车时间：" : "上车时间：" + TimeUtil.stampToDateForHome(item.getStart_time()))
                    .setText(R.id.tv_id, "订单号：" + item.getId())
                    .setText(R.id.tv_price, "一口价：" + item.getPrice());
        }

    }
}
