package com.cy.travelking.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cy.travelking.HardCode;
import com.cy.travelking.R;
import com.cy.travelking.activity.RouteDetailActivity;
import com.cy.travelking.adapter.CharteredAdapter;
import com.cy.travelking.adapter.TaskAdapter;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.entity.Result;
import com.cy.travelking.entity.TaskOrder;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.cy.travelking.constant.UrlConstant.ACCEPT_ORDER;
import static com.cy.travelking.constant.UrlConstant.TASK_LIST;

/**
 * Author: CY
 * Date: 2019/4/22
 */
public class CharteredFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private List<TaskOrder.DataListBean> mList;
    private CharteredAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_chartered;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        mAdapter = new CharteredAdapter(mList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_contact:
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + mList.get(position).getMobile());
                        intent.setData(data);
                        startActivity(intent);
                        break;
                    case R.id.rl_accept:
                        acceptOrder(mList.get(position));
                        break;
                    case R.id.tv_detail:
                        Intent detailIntent = new Intent(mContext, RouteDetailActivity.class);
                        detailIntent.putExtra("id",mList.get(position).getPrivate_consume_id());
                        startActivity(detailIntent);
                        break;
                }
            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });
    }

    public void acceptOrder(TaskOrder.DataListBean bean) {
        showLoading();
        OkGo.<Result<String>>post(ACCEPT_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("order_id", bean.getId())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .execute(new JsonCallback<Result<String>>() {
                             @Override
                             public void onSuccess(Response<Result<String>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     toast("接单成功!");
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<String>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

    @Override
    public void getData() {
        super.getData();
        refreshData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshData();
        }
    }

    /**
     * 司机待接单列表
     */
/*    private void refreshData() {
        OkGo.<Result<List<AcceptOrder>>>post(WAIT_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     List<AcceptOrder> list = (List<AcceptOrder>) result.getData();
                                     mList.clear();
                                     if(list.size()>30){
                                         for (int i=0;i<30;i++){
                                             mList.add(list.get(i));
                                         }
                                     }else {
                                         mList.addAll(list);
                                     }
                                     mAdapter.notifyDataSetChanged();
                                     mRefreshLayout.finishRefresh();
                                 } else {
                                     toast(result.getMessage());
                                     mRefreshLayout.finishRefresh(false);
                                 }
                             }

                             @Override
                             public void onError(Response<Result<List<AcceptOrder>>> response) {
                                 super.onError(response);
                                 toastNetErr();
                                 mRefreshLayout.finishRefresh(false);
                             }
                         }
                );
    }*/

    /**
     * 司机任务列表
     */
    private void refreshData() {
        Map<String, Object> map = new HashMap<>();
//        map.put("driver_user_id",AppUtil.getDriverInfo().getDriver().getUser_id());
        map.put("page", 0);
        map.put("size", 40);
        Map<String, Object> submap = new HashMap<>();
        submap.put( "direction", "DESC");
        submap.put("property", "startTime");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(submap);
        map.put("sort_data_list", list);
        OkGo.<Result<TaskOrder>>post(TASK_LIST + "?driver_user_id=" + AppUtil.getDriverInfo().getDriver().getUser_id())
                .tag(this)
                .headers("token", AppUtil.getToken())
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<Result<TaskOrder>>() {
                             @Override
                             public void onSuccess(Response<Result<TaskOrder>> response) {
//                                 Result result = gson.fromJson(HardCode.CHARTERED,Result.class);
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     TaskOrder taskOrder = gson.fromJson(gson.toJson(result.getData()),TaskOrder.class);
//                                     TaskOrder taskOrder = (TaskOrder) result.getData();
                                     List<TaskOrder.DataListBean> list = taskOrder.getData_list();
                                     mList.clear();
                                     for (TaskOrder.DataListBean bean :
                                             list) {
//                                         ['JIEJI', 'SONGJI', 'ORDER_SCENE', 'DAY_PRIVATE', 'ROAD_PRIVATE']
                                         if ("DAY_PRIVATE".equals(bean.getScene())||"ROAD_PRIVATE".equals(bean.getScene())) {
                                             mList.add(bean);
                                         }
                                     }
//                                     mList.addAll(list);
                                     mAdapter.notifyDataSetChanged();
                                     mRefreshLayout.finishRefresh();
                                 } else {
                                     toast(result.getMessage());
                                     mRefreshLayout.finishRefresh(false);
                                 }
                             }

                             @Override
                             public void onError(Response<Result<TaskOrder>> response) {
                                 super.onError(response);
                                 toastNetErr();
                                 mRefreshLayout.finishRefresh(false);
                             }
                         }
                );
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_ORDER:
                refreshData();
                break;
            case MessageConstant.NOTIFY_TASK:
                refreshData();
                break;
        }
    }
}