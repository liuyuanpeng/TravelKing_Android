package com.cy.travelking.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cy.travelking.R;
import com.cy.travelking.adapter.HistoryAdapterAdapter;
import com.cy.travelking.entity.HistoryOrder;
import com.cy.travelking.entity.Result;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.cy.travelking.constant.UrlConstant.HISTORY_ORDER;
import static com.cy.travelking.constant.UrlConstant.PAGE_SIZE;

/**
 * Created by CY on 2019/4/20
 */
public class HistoryOrderAcitvity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private List<HistoryOrder.DataListBean> mList;
    private HistoryAdapterAdapter mAdapter;
    private int page = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_history_order;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        titleBar.setTitle("历史订单");
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mAdapter = new HistoryAdapterAdapter(mList);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                refreshData();
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        refreshData();
    }

    private void refreshData() {
        Map<String,Object> map = new HashMap<>();
        map.put("driver_user_id",AppUtil.getDriverInfo().getDriver().getUser_id());
        map.put("page",page);
        map.put("size",PAGE_SIZE);
        OkGo.<Result<HistoryOrder>>post(HISTORY_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<Result<HistoryOrder>>() {
                             @Override
                             public void onSuccess(Response<Result<HistoryOrder>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     HistoryOrder historyOrder = (HistoryOrder) result.getData();
                                     if (page == 0) {
                                         mList.clear();
                                         mRefreshLayout.finishRefresh();
                                         if (historyOrder.getPage() < historyOrder.getTotal_elements()) {
                                             page++;
                                             mRefreshLayout.setNoMoreData(false);
                                         } else {
                                             mRefreshLayout.finishLoadMoreWithNoMoreData();
                                         }
                                     } else {
                                         if (historyOrder.getPage() < historyOrder.getTotal_elements()) {
                                             page++;
                                             mRefreshLayout.finishLoadMore();
                                         } else {
                                             mRefreshLayout.finishLoadMoreWithNoMoreData();
                                         }
                                     }
                                     mList.addAll(historyOrder.getData_list());
                                     mAdapter.notifyDataSetChanged();
                                 } else {
                                     toast(result.getMessage());
                                     mRefreshLayout.finishRefresh(false);
                                     mRefreshLayout.finishLoadMore(false);
                                 }
                             }

                             @Override
                             public void onError(Response<Result<HistoryOrder>> response) {
                                 super.onError(response);
                                 toastNetErr();
                                 mRefreshLayout.finishRefresh(false);
                                 mRefreshLayout.finishLoadMore(false);
                             }
                         }
                );
    }
}
