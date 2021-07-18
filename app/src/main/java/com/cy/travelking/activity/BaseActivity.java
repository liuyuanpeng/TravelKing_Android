package com.cy.travelking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cy.travelking.App;
import com.cy.travelking.R;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.widget.TitleBar;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.titleBar)
    public TitleBar titleBar;
    private MaterialDialog dialog;
    private Toast mToast;
    private ImmersionBar mImmersionBar;
    private Unbinder mUnBinder;
    public Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initBeforeOnCreate();
        super.onCreate(savedInstanceState);
        initBefore();
        setContentView(getLayoutResId());
        App.getInstance().addActivity(this);
        EventBusUtil.register(this);
        mUnBinder=ButterKnife.bind(this);
        setStatusBarColor();
        initTitle();
        initData();
        initView(savedInstanceState);
        getData();
    }

    public void initBeforeOnCreate() {
    }

    public void initBefore() {
    }

    protected abstract int getLayoutResId();

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
        mImmersionBar = ImmersionBar.with(this).reset().fitsSystemWindows(false).statusBarDarkFont(false).statusBarColor(color);
        mImmersionBar.init();
    }

    public void initTitle() {
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setBackgroundResource(R.color.titleGreen);
        titleBar.setLeftImageResource(R.mipmap.arrow_back);
//        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
//        titleBar.setLeftImageResource(R.mipmap.back_green);
//        titleBar.setLeftText("返回");
//        titleBar.setLeftTextColor(Color.WHITE);
//        titleBar.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        titleBar.setTitle("文章详情\n副标题");
//        titleBar.setTitleColor(Color.WHITE);
//        titleBar.setSubTitleColor(Color.WHITE);
//        titleBar.setDividerColor(Color.GRAY);
//        titleBar.setActionTextColor(Color.WHITE);
//        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.mipmap.collect) {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(MainActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
//                mCollectView.setImageResource(R.mipmap.fabu);
//                titleBar.setTitle(mIsSelected ? "文章详情\n朋友圈" : "帖子详情");
//                mIsSelected = !mIsSelected;
//            }
//        });
//        titleBar.addAction(new TitleBar.TextAction("发布") {
//            @Override
//            public void performAction(View view) {
//                Toast.makeText(MainActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    protected abstract void initData();

    public void initView(Bundle savedInstanceState) {
    }

    public void getData() {
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityFinish(Class clazz) {
        startActivity(new Intent(this, clazz));
        this.finish();
    }

    public void startActivityFinish(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    public void toastNetErr() {
        toast("网络异常，请稍后再试");
    }

    public void toast(int msgId) {
        toast(getResources().getString(msgId));
    }

    public void toast(final String msg) {
        Logger.d("toast: "+msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                dismissLoading();
                if (mToast == null) {
                    mToast = Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT);
                } else {
                    View view = mToast.getView();
                    mToast.cancel();
                    mToast = new Toast(BaseActivity.this);
                    mToast.setView(view);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    mToast.setText(msg);
                }
                mToast.show();
            }
        });
    }

    public void toastError(Object object) {
        if (object == null) {
            toast("网络异常");
        } else {
            toast(object.toString());
        }
    }

    public void showLoading() {
        showLoading("");
    }

    public void showLoadingContent(String content, boolean cancel) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(this)
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
            dialog = new MaterialDialog.Builder(this)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar = null;
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        if (dialog != null) {
            dialog = null;
        }
        App.getInstance().removeActivity(this);
        EventBusUtil.unregister(this);
    }

}
