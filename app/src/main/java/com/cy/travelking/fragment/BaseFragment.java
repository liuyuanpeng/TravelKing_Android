package com.cy.travelking.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cy.travelking.R;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.util.EventBusUtil;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    public View mView;
    private Unbinder mUnBinder;
    private MaterialDialog dialog;
    private ImmersionBar mImmersionBar;
    public Gson gson = new Gson();
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutResId(), null);
            mUnBinder = ButterKnife.bind(this, mView);
            EventBusUtil.register(this);
            initData();
            initView();
            getData();
        }
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        onEvent(event);
    }

    public void onEvent(MessageEvent event) {
        switch (event.getId()) {
           /* case MessageConstant.ERR_LOGOUT:
                showLogoutDialog();
                break;*/
        }
    }

    protected abstract int getLayoutResId();

    public void initData() {
    }

    protected abstract void initView();

    public void getData() {
    }

    /**
     * fitsSystemWindows:false表示状态栏和布局重叠
     * statusBarDarkFont:true表示状态栏字体黑色
     */
    public void setStatusBarColor() {
        mImmersionBar = ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarDarkFont(false).statusBarColor(R.color.titleGreen);
        mImmersionBar.init();
    }

    /**
     * @param noOverlap 是否不重叠
     * @param isBlack   状态栏字体是否黑色
     * @param color     状态栏背景颜色
     */
    public void setStatusBarColor(boolean noOverlap, boolean isBlack, int color) {
        mImmersionBar = ImmersionBar.with(this).reset().fitsSystemWindows(noOverlap).statusBarDarkFont(isBlack).statusBarColor(color);
        mImmersionBar.init();
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void toast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toastNetErr() {
        toast("网络异常，请稍后再试");
    }

    public void showLoading() {
        showLoading("");
    }

    public void showLoadingContent(String content, boolean cancel) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(getContext())
                    .content("请稍候")
//                    .cancelable(false)
                    .progress(true, 0)
                    .build();
        }
        dialog.setContent(content);
        dialog.setCancelable(cancel);
        dialog.show();
    }

    public void showLoading(String title) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(getContext())
                    .content("请稍候")
//                    .cancelable(false)
                    .progress(true, 0)
                    .build();
        }
        dialog.setTitle(title);
        dialog.show();
    }

    public boolean isShowLoading() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public void dismissLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mImmersionBar != null)
            mImmersionBar = null;
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        if (dialog != null) {
            dialog = null;
        }
    }

}
