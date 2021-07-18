package com.cy.travelking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.cy.travelking.R;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.OrderStatus;
import com.cy.travelking.constant.UrlConstant;
import com.cy.travelking.entity.AcceptOrder;
import com.cy.travelking.entity.OpenOrder;
import com.cy.travelking.entity.Result;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.widget.CountDownTextView;
import com.cy.travelking.widget.CustomDialog;
import com.cy.travelking.widget.TitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.CHANGE_ORDER;
import static com.cy.travelking.constant.UrlConstant.DONE_ORDER;
import static com.cy.travelking.constant.UrlConstant.GET_ORDER;

public class NaviActivity extends BaseActivity implements AMapNaviViewListener, AMapNaviListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.navi_view)
    AMapNaviView mAMapNaviView;
    @BindView(R.id.tv_action)
    TextView mTvAction;
    @BindView(R.id.tv_title_action)
    TextView mTvTitleAction;

    private AMapNavi mAMapNavi;
    private AcceptOrder order;
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navi;
    }

    @Override
    protected void initData() {
        order = (AcceptOrder) getIntent().getSerializableExtra("order");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        notifyStatus();
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.onCreate(savedInstanceState);

        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setUseInnerVoice(true);
    }

    public void notifyStatus(){
        if (OrderStatus.ACCEPTED.equals(order.getOrder_status())) {
            titleBar.setTitle("去接乘客");
            mTvAction.setText("到达约定点");
            mTvTitleAction.setVisibility(View.VISIBLE);
            mTvTitleAction.setText("申请改派");
            sList.clear();
            eList.clear();
            sList.add(AppUtil.getNowLatlng());
            eList.add(new NaviLatLng(order.getStart_latitude(), order.getStart_longitude()));
        }else if(OrderStatus.ON_THE_WAY.equals(order.getOrder_status())){
            titleBar.setTitle("服务中");
            mTvAction.setText("结束行程");
//            mTvTitleAction.setVisibility(View.GONE);
            mTvTitleAction.setText("刷新");
            sList.clear();
            eList.clear();
            sList.add(AppUtil.getNowLatlng());
            eList.add(new NaviLatLng(order.getTarget_latitude(), order.getTarget_longitude()));
        }
    }

    public void updateOrder(AcceptOrder acceptOrder) {
        showLoading();
        OkGo.<Result<AcceptOrder>>get(GET_ORDER)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("order_id", acceptOrder.getId())
                .execute(new JsonCallback<Result<AcceptOrder>>() {
                    @Override
                    public void onSuccess(Response<Result<AcceptOrder>> response) {
                        dismissLoading();
                        Result result = response.body();
                        if (result.isSuccess()) {
                            EventBusUtil.post(MessageConstant.NOTIFY_ORDER);
                            notifyStatus();
                            calculateRoute();
                        }
                    }
                    @Override
                    public void onError(Response<Result<AcceptOrder>> response) {
                        super.onError(response);
                        dismissLoading();
                        toastNetErr();
                    }
                });
    }

    public void changeOrder(AcceptOrder acceptOrder) {
        if (OrderStatus.ON_THE_WAY.equals(order.getOrder_status())) {
            updateOrder(acceptOrder);
            return;
        }
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
                                     finish();
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

    @OnClick({R.id.tv_title_action,R.id.tv_action})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_title_action:
                changeOrder(order);
                break;
            case R.id.tv_action:
                if (OrderStatus.ACCEPTED.equals(order.getOrder_status())) {
                    executeOrder();
                }else if(OrderStatus.ON_THE_WAY.equals(order.getOrder_status())){
                    showDoneDialog();
                }
                break;
        }
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
                                     calculateRoute();
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

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAMapNaviView != null) {
            mAMapNaviView.onDestroy();
        }

        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    @Override
    public void onNaviSetting() {
    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {
    }

    @Override
    public void onNextRoadClick() {
    }

    @Override
    public void onScanViewButtonClick() {
    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public void onNaviViewShowMode(int i) {

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        calculateRoute();
    }

    public void calculateRoute(){
        int strategy = 0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //驾车路径计算
        mAMapNavi.calculateDriveRoute(sList, eList, null, strategy);
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
//        mAMapNavi.startNavi(NaviType.EMULATOR);
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }
}
