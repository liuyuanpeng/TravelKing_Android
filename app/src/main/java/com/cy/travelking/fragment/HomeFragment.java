package com.cy.travelking.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DistanceItem;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cy.travelking.R;
import com.cy.travelking.activity.NaviActivity;
import com.cy.travelking.activity.RouteDetailActivity;
import com.cy.travelking.adapter.HomeAdapter;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.OrderStatus;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.entity.AcceptOrder;
import com.cy.travelking.entity.HistoryOrder;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.entity.OpenOrder;
import com.cy.travelking.entity.PassOrder;
import com.cy.travelking.entity.Result;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.util.SpUtil;
import com.cy.travelking.widget.CountDownTextView;
import com.cy.travelking.widget.CustomDialog;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.ACCEPTER_ORDER;
import static com.cy.travelking.constant.UrlConstant.CANCELED_ORDER;
import static com.cy.travelking.constant.UrlConstant.ACCEPT_ORDER;
import static com.cy.travelking.constant.UrlConstant.CHANGE_ORDER;
import static com.cy.travelking.constant.UrlConstant.DONE_ORDER;
import static com.cy.travelking.constant.UrlConstant.HISTORY_ORDER;
import static com.cy.travelking.constant.UrlConstant.OPEN_ORDER;
import static com.cy.travelking.constant.UrlConstant.PAGE_SIZE;

/**
 * Author: CY
 * Date: 2019/4/22
 */
public class HomeFragment extends BaseFragment implements DistanceSearch.OnDistanceSearchListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private List<AcceptOrder> mList;
    private HomeAdapter mAdapter;
    private CustomDialog dialog;
    private TextView tvOrderId;
    private TextView tvDistance;
    private TextView tvTarget;
    private TextView tvStart;
    private TextView tvTime;
    private CountDownTextView mCountDownTextView;
    private boolean isOpen = false;//是否开启抢单

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Handler handler = new Handler();
    private long loopGetOrderTime = 3 * 1000;
//    private long loopGetOrderTime = 60 * 1000;
    private DistanceSearch distanceSearch;

    private OpenOrder nowOrder;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        distanceSearch = new DistanceSearch(mContext);
        distanceSearch.setDistanceSearchListener(this);
        tv_order.setVisibility(View.INVISIBLE);
        mList = new ArrayList<>();
        mAdapter = new HomeAdapter(mList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_nav) {
                    toNavi(mList.get(position));
                } else if (view.getId() == R.id.tv_call || view.getId() == R.id.rl_contact) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + mList.get(position).getMobile());
                    intent.setData(data);
                    startActivity(intent);
                } else if (view.getId() == R.id.tv_action_order || view.getId() == R.id.rl_accept) {
                    if (OrderStatus.ACCEPTED.equals(mList.get(position).getOrder_status())) {
                        dialog = new CustomDialog(mContext, R.layout.view_confirm);
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
                                changeOrder(mList.get(position));
                                dialog.cancel();
                            }
                        });

                    } else {
                        showDoneDialog(mList.get(position));
                    }
                }else if(view.getId() == R.id.tv_detail){
                    Intent detailIntent = new Intent(mContext, RouteDetailActivity.class);
                    detailIntent.putExtra("order", mList.get(position));
                    startActivity(detailIntent);
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

    public void changeOrder(AcceptOrder acceptOrder) {
        showLoading();
        OkGo.<Result<List<AcceptOrder>>>post(CHANGE_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .params("order_id", acceptOrder.getId())
                .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     toast("申请成功");
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
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

    private CustomDialog doneDialog;

    private void showDoneDialog(AcceptOrder acceptOrder) {
        doneDialog = new CustomDialog(mContext, R.layout.view_wait_customer);
        doneDialog.setCancelable(false);
        doneDialog.show();
        doneDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneDialog.cancel();
            }
        });
        doneDialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneOrder(acceptOrder);
                doneDialog.cancel();
            }
        });
    }


    public void doneOrder(AcceptOrder acceptOrder) {
        showLoading();
        OkGo.<Result<List<AcceptOrder>>>post(DONE_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .params("order_id", acceptOrder.getId())
                .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
                                 dismissLoading();
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     toast("订单完成");
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
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

    public void toNavi(AcceptOrder order) {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "为了更好的体验，请求打开定位权限", "禁止", "打开权限");
        PermissionsUtil.requestPermission(mContext, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                if (AppUtil.isGpsOPen()) {
                    Intent intent = new Intent(mContext, NaviActivity.class);
                    intent.putExtra("order", order);
                    startActivity(intent);
                } else {
                    new MaterialDialog.Builder(mContext)
                            .content("请打开GPS")
                            .cancelable(false)
                            .positiveText("确定")
                            .show();
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                toast("用户拒绝了定位权限");
            }
        }, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, true, tip);
    }

    @Override
    public void getData() {
        super.getData();
        refreshData();
    }

    @OnClick({R.id.tv_order})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_order:
                if (isOpen) {
                    tv_order.setText("开启抢单");
                    handler.removeCallbacksAndMessages(null);
                    isOpen = !isOpen;
                } else {
                    if (!AppUtil.isGpsOPen()) {
                        new MaterialDialog.Builder(mContext)
                                .content("请打开GPS")
                                .cancelable(false)
                                .positiveText("确定")
                                .show();
                    } else {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String day = sdf.format(System.currentTimeMillis());
                        PassOrder passOrder = new PassOrder();
                        passOrder.setTime(day);
                        passOrder.setIds(new ArrayList<>());
                        SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));

                        openOrder();
                        tv_order.setText("关闭抢单");
                        isOpen = !isOpen;
                    }
                }
                break;
        }
    }

    public Runnable getOrderRun = new Runnable() {
        @Override
        public void run() {
            openOrder();
        }
    };

    /**
     * 司机已接单列表
     */
    private void refreshData() {
        OkGo.<Result<List<AcceptOrder>>>post(ACCEPTER_ORDER)
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
                                     mList.addAll(list);
                                     mAdapter.notifyDataSetChanged();
                                     mRefreshLayout.finishRefresh();
//                                     int[] emptyArray = new int[0];
//                                     OkGo.<Result<List<AcceptOrder>>>post(CANCELED_ORDER)
//                                             .tag(this)
//                                             .headers("token", AppUtil.getToken())
//                                             .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
//                                             .upJson(gson.toJson(emptyArray))
//                                             .execute(new JsonCallback<Result<List<AcceptOrder>>>() {
//                                                          @Override
//                                                          public void onSuccess(Response<Result<List<AcceptOrder>>> response) {
//                                                              Result result = response.body();
//                                                              if (result.isSuccess()) {
//                                                                  List<AcceptOrder> list = (List<AcceptOrder>) result.getData();
//                                                                  mList.addAll(list);
//                                                                  mAdapter.notifyDataSetChanged();
//                                                                  mRefreshLayout.finishRefresh();
//                                                              } else {
//                                                                  toast(result.getMessage());
//                                                                  mRefreshLayout.finishRefresh(false);
//                                                              }
//                                                          }
//
//                                                          @Override
//                                                          public void onError(Response<Result<List<AcceptOrder>>> response) {
//                                                              super.onError(response);
//                                                              toastNetErr();
//                                                              mRefreshLayout.finishRefresh(false);
//                                                          }
//                                                      }
//                                             );
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
    }

    /**
     * 开启抢单
     */

    private void openOrder() {
        nowOrder = null;
        OkGo.<Result<List<OpenOrder>>>post(OPEN_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .execute(new JsonCallback<Result<List<OpenOrder>>>() {
                             @Override
                             public void onSuccess(Response<Result<List<OpenOrder>>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                     String day = sdf.format(System.currentTimeMillis());
                                     PassOrder passOrder;
                                     if (!TextUtils.isEmpty(SpUtil.getStr(SpConstant.PASS_ORDER))) {
                                         passOrder = gson.fromJson(SpUtil.getStr(SpConstant.PASS_ORDER), PassOrder.class);
                                         if (!day.equals(passOrder.getTime())) {
                                             passOrder.setTime(day);
                                             passOrder.setIds(new ArrayList<>());
                                             SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
                                         }
                                     } else {
                                         passOrder = new PassOrder();
                                         passOrder.setTime(day);
                                         passOrder.setIds(new ArrayList<>());
                                         SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
                                     }
                                     List<OpenOrder> openOrders = (List<OpenOrder>) result.getData();
                                     if (openOrders == null || openOrders.size() == 0) {
                                         handler.postDelayed(getOrderRun, loopGetOrderTime);
                                     } else {
                                         for (OpenOrder openOrder :
                                                 openOrders) {
                                             if (!passOrder.getIds().contains(openOrder.getId())) {
                                                 nowOrder = openOrder;
                                                 showDialog();
                                                 break;
                                             }
                                         }
                                         if (nowOrder == null) {
//                                             handler.postDelayed(getOrderRun, loopGetOrderTime);
                                             passOrder = new PassOrder();
                                             passOrder.setTime(day);
                                             passOrder.setIds(new ArrayList<>());
                                             SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
                                             for (OpenOrder openOrder :
                                                     openOrders) {
                                                 if (!passOrder.getIds().contains(openOrder.getId())) {
                                                     nowOrder = openOrder;
                                                     showDialog();
                                                     break;
                                                 }
                                             }
                                         }
                                     }
                                 } else {
                                     toast(result.getMessage());
                                     handler.postDelayed(getOrderRun, loopGetOrderTime);
                                 }
                             }

                             @Override
                             public void onError(Response<Result<List<OpenOrder>>> response) {
                                 super.onError(response);
                                 handler.postDelayed(getOrderRun, loopGetOrderTime);
                             }
                         }
                );

    }

    /**
     * 司机接单
     */
    private void acceptOrder(String order_id) {
        OkGo.<Result<String>>post(ACCEPT_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("order_id", order_id)
                .params("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id())
                .execute(new JsonCallback<Result<String>>() {
                             @Override
                             public void onSuccess(Response<Result<String>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     toast("抢单成功!");
                                     isOpen = false;
                                     tv_order.setText("开启抢单");
                                     dialog.cancel();
                                     handler.removeCallbacksAndMessages(null);
                                     EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<String>> response) {
                                 super.onError(response);
                                 toastNetErr();
                             }
                         }
                );
    }

    private void showDialog() {
        Logger.d("showDialog: "+nowOrder.getId());
        AppUtil.alarm();
        if (dialog == null) {
            dialog = new CustomDialog(getContext(), R.layout.view_open_order);
            dialog.setCancelable(false);
            tvOrderId = dialog.findViewById(R.id.tv_order_id);
            tvTarget = dialog.findViewById(R.id.tv_target);
            tvStart = dialog.findViewById(R.id.tv_start);
            tvTime = dialog.findViewById(R.id.tv_time);
            tvDistance = dialog.findViewById(R.id.tv_distance);
            mCountDownTextView = dialog.findViewById(R.id.ctv_time);
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPassOrder(nowOrder);
                    dialog.cancel();
                    handler.postDelayed(getOrderRun, loopGetOrderTime);
                }
            });
            dialog.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptOrder(nowOrder.getId());
                    dialog.cancel();
                    handler.postDelayed(getOrderRun, loopGetOrderTime);
                }
            });
        }

        dialog.show();
        tvOrderId.setText(nowOrder.getId());
        tvStart.setText(nowOrder.getStart_place());
        tvTarget.setText(nowOrder.getTarget_place());
        tvTime.setText(sdf.format(nowOrder.getStart_time()));
        mCountDownTextView.setNormalText("30")
                .setCountDownText("", "")
                .setCloseKeepCountDown(false)//关闭页面保持倒计时开关
                .setCountDownClickable(false)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        addPassOrder(nowOrder);
                        handler.postDelayed(getOrderRun, loopGetOrderTime);
                        dialog.cancel();
                    }
                });
        mCountDownTextView.startCountDown(30);
        try {
            float distance = AMapUtils.calculateLineDistance(new LatLng(AppUtil.getNowLatlng().getLatitude(), AppUtil.getNowLatlng().getLongitude())
                    , new LatLng(nowOrder.getStart_latitude(), nowOrder.getStart_longitude()));
            Logger.d("distance: "+distance);
            DecimalFormat decimalFormat =new DecimalFormat("0.00");
            tvDistance.setText("距离您" + decimalFormat.format(distance/1000) + "km");
        }catch (Exception e){
            e.printStackTrace();
            tvDistance.setText("距离您0.0km");
//            tvDistance.setText("距离您null米"+e.getMessage());
        }
//        tvDistance.setText("距离您" + openOrder.getKilo() + "米");
//        searchDistance();
    }

    public void addPassOrder(OpenOrder openOrder) {
        Logger.d("addPassOrder: "+openOrder.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(System.currentTimeMillis());
        PassOrder passOrder;
        if (!TextUtils.isEmpty(SpUtil.getStr(SpConstant.PASS_ORDER))) {
            passOrder = gson.fromJson(SpUtil.getStr(SpConstant.PASS_ORDER), PassOrder.class);
            if (!day.equals(passOrder.getTime())) {
                passOrder.setTime(day);
                passOrder.setIds(new ArrayList<>());
                SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
            }
        } else {
            passOrder = new PassOrder();
            passOrder.setTime(day);
            passOrder.setIds(new ArrayList<>());
            SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
        }
        Logger.d("passOrder.getIds(): "+passOrder.getIds());
        passOrder.getIds().add(openOrder.getId());
        Logger.d("passOrder.getIds(): "+passOrder.getIds());
        SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
    }

    public void searchDistance() {
        if (AppUtil.getNowLatlng() != null && dialog != null && dialog.isShowing()) {
            DistanceSearch.DistanceQuery distanceQuery = new DistanceSearch.DistanceQuery();
//设置起点和终点，其中起点支持多个
            List<LatLonPoint> latLonPoints = new ArrayList<LatLonPoint>();
            latLonPoints.add(new LatLonPoint(AppUtil.getNowLatlng().getLatitude(), AppUtil.getNowLatlng().getLongitude()));
            distanceQuery.setOrigins(latLonPoints);
            distanceQuery.setDestination(new LatLonPoint(nowOrder.getStart_latitude(), nowOrder.getStart_longitude()));
//设置测量方式，支持直线和驾车
            distanceQuery.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);
            distanceSearch.calculateRouteDistanceAsyn(distanceQuery);
        }
    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        if (i == 1000 && dialog != null && dialog.isShowing()) {
            for (DistanceItem item :
                    distanceResult.getDistanceResults()) {
                if (item.getDistance() > 1000) {
                    tvDistance.setText("距离您" + (item.getDistance() / 1000) + "KM");
                } else {
                    tvDistance.setText("距离您" + item.getDistance() + "M");
                }
            }
        }
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_LOCATION:
//                searchDistance();
                break;
            case MessageConstant.NOTIFY_ORDER:
                refreshData();
                break;
            case MessageConstant.NOTIFY_HOME:
                refreshData();
                break;
        }
    }
}
