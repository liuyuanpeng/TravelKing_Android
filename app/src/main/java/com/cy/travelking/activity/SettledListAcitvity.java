package com.cy.travelking.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cy.travelking.R;
import com.cy.travelking.adapter.HistoryAdapterAdapter;
import com.cy.travelking.adapter.SettledAdapter;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.entity.HistoryOrder;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.entity.Result;
import com.cy.travelking.entity.SettledDetail;
import com.cy.travelking.entity.SettledTotal;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.SETTLED_LIST;

/**
 * Created by CY on 2019/4/20
 */
public class SettledListAcitvity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    private List<SettledDetail> mList;
    private SettledAdapter mAdapter;
    private long start;
    private long end;
    private int year;
    private int month;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_settled_list;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        titleBar.setTitle("我的收入");
    }

    @OnClick({R.id.tv_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_month:
                Intent intent = new Intent(this, ChooseTimeActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mAdapter = new SettledAdapter(mList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData(start, end);
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        mTvMonth.setText("本月");
        year = AppUtil.getYear();
        month = AppUtil.getMonth();
        start = AppUtil.getBeginDayofMonth(year, month);
        end = AppUtil.getEndDayofMonth(year, month);
        refreshData(start, end);
    }

    private void refreshData(long start, long end) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id());
        map.put("start", start);
        map.put("end", end);
        OkGo.<Result<SettledTotal>>post(SETTLED_LIST)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<Result<SettledTotal>>() {
                             @Override
                             public void onSuccess(Response<Result<SettledTotal>> response) {
                                 Result result = response.body();
                                 mRefreshLayout.finishRefresh();

                                 if (result.isSuccess()) {
                                     SettledTotal settledTotal = (SettledTotal) result.getData();
                                     mList.clear();
                                     mTvMoney.setText("收入 ￥" + settledTotal.getTotal());
                                     mList.addAll(settledTotal.getTravel_order_list());
                                     mAdapter.notifyDataSetChanged();
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<SettledTotal>> response) {
                                 super.onError(response);
                                 toastNetErr();
                                 mRefreshLayout.finishRefresh();
                             }
                         }
                );
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.CHOOSE_SETTLED_TIME:
                Logger.d("CHOOSE_SETTLED_TIME: "+event.getData().toString());
                String[] timeArr = event.getData().toString().split("-");
                year = Integer.parseInt(timeArr[0].trim());
                month = Integer.parseInt(timeArr[1].trim());
                if (year == AppUtil.getYear() && month == AppUtil.getMonth()) {
                    mTvMonth.setText("本月");
                } else {
                    mTvMonth.setText(year + " - " + month);
                }
                start = AppUtil.getBeginDayofMonth(year, month);
                end = AppUtil.getEndDayofMonth(year, month);
                refreshData(start, end);
                break;
        }
    }
}
