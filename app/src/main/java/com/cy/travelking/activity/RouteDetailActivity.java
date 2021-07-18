package com.cy.travelking.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.bumptech.glide.Glide;
import com.cy.travelking.R;
import com.cy.travelking.adapter.RouteDetailAdapter;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.OrderStatus;
import com.cy.travelking.constant.UrlConstant;
import com.cy.travelking.entity.AcceptOrder;
import com.cy.travelking.entity.Result;
import com.cy.travelking.entity.RouteDetail;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.widget.CustomDialog;
import com.cy.travelking.widget.TitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.DONE_ORDER;
import static com.cy.travelking.constant.UrlConstant.GET_PRIVATE_CONSUME;

public class RouteDetailActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tv_action)
    TextView mTvAction;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.tv_title)
    TextView mTitle;


    private RouteDetail routeDetail;
    private RouteDetailAdapter mAdapter;
    private List<RouteDetail.RoadsBean> mData;
    private AcceptOrder order;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_route_detail;
    }

    @Override
    protected void initData() {
        titleBar.setTitle("路线详情");
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(RouteDetailActivity.this).load(path).into(imageView);
            }
        });
        mData = new ArrayList<>();
        mAdapter = new RouteDetailAdapter(mData);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setNestedScrollingEnabled(false);
        mRv.setAdapter(mAdapter);

        order = (AcceptOrder) getIntent().getSerializableExtra("order");
        if(order == null){
            mTvAction.setVisibility(View.GONE);
        }else {
            mTvAction.setVisibility(View.VISIBLE);
            notifyStatus();
        }
    }

    public void notifyStatus(){
        if (OrderStatus.ACCEPTED.equals(order.getOrder_status())) {
            titleBar.setTitle("去接乘客");
            mTvAction.setText("到达约定点");
        }else if(OrderStatus.ON_THE_WAY.equals(order.getOrder_status())){
            titleBar.setTitle("服务中");
            mTvAction.setText("结束行程");
        }
    }

    @Override
    public void getData() {
        if (order != null && order.getScene().equals("DAY_PRIVATE")) {
            mRv.setVisibility(View.INVISIBLE);
            mTvIntro.setVisibility(View.INVISIBLE);
            return;
        }
        showLoading();
        String id ="";
        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
        }else {
            id = order.getPrivate_consume_id();
        }
        OkGo.<Result<RouteDetail>>get(GET_PRIVATE_CONSUME)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("private_consume_id",id)
                .execute(new JsonCallback<Result<RouteDetail>>() {
                             @Override
                             public void onSuccess(Response<Result<RouteDetail>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     routeDetail = (RouteDetail) result.getData();
                                     initDtail();
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<RouteDetail>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

    public void initDtail() {
        if (!TextUtils.isEmpty(routeDetail.getPrivate_consume().getName())) {
            mTitle.setText(routeDetail.getPrivate_consume().getName());
        }
        if (!TextUtils.isEmpty(routeDetail.getPrivate_consume().getImages())) {
            banner.setVisibility(View.VISIBLE);
            //设置图片集合
            List<String> images = new ArrayList<>();
            for (String img :
                    routeDetail.getPrivate_consume().getImages().split(",")) {
//                img = AppUtil.replaceDomin(img);
                images.add(img);
            }
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        } else {
            banner.setVisibility(View.GONE);
        }
        mData.clear();
        mData.addAll(routeDetail.getRoads());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_action})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_action:
                if (OrderStatus.ACCEPTED.equals(order.getOrder_status())) {
                    executeOrder();
                }else if(OrderStatus.ON_THE_WAY.equals(order.getOrder_status())){
                    showDoneDialog();
                }
                break;
        }
    }

    public void executeOrder(){
        showLoading();
        OkGo.<Result<List<AcceptOrder>>>post(UrlConstant.EXECUTE_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .params("order_id", order.getId())
                .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
                                     order.setOrder_status(OrderStatus.ON_THE_WAY);
                                     notifyStatus();
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<List<AcceptOrder>>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

    private CustomDialog dialog;

    private void showDoneDialog() {
        dialog = new CustomDialog(this, R.layout.view_wait_customer);
        dialog.setCancelable(false);
        dialog.show();
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneOrder();
                dialog.cancel();
            }
        });
    }

    public void doneOrder() {
        showLoading();
        OkGo.<Result<List<AcceptOrder>>>post(DONE_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .params("order_id", order.getId())
                .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     toast("订单完成");
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
                                     finish();
                                 } else {
                                     toast("用户还未付款，不能结束该订单！");
                                 }
                             }

                             @Override
                             public void onError(Response<Result<List<AcceptOrder>>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

}
