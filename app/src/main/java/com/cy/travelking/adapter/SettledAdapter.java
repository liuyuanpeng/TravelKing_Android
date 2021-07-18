package com.cy.travelking.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.entity.HistoryOrder;
import com.cy.travelking.entity.SettledDetail;
import com.cy.travelking.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class SettledAdapter extends BaseQuickAdapter<SettledDetail, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SettledAdapter(List<SettledDetail> data) {
        super(R.layout.item_settled, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettledDetail item) {
        helper.setText(R.id.tv_name,item.getUsername())
                .setText(R.id.tv_price,"+"+item.getPrice())
                .setText(R.id.tv_mobile, item.getMobile())
                .setText(R.id.tv_time, sdf.format(item.getCreate_time()));
    }
}
