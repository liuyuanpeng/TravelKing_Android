package com.cy.travelking.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cy.travelking.R;
import com.cy.travelking.activity.RouteDetailActivity;
import com.cy.travelking.entity.RouteDetail;
import com.cy.travelking.entity.TaskOrder;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.TimeUtil;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CY on 2018/12/11
 */
public class RouteDetailAdapter extends BaseQuickAdapter<RouteDetail.RoadsBean, BaseViewHolder> {

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public RouteDetailAdapter(List<RouteDetail.RoadsBean> data) {
        super(R.layout.item_route_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RouteDetail.RoadsBean item) {
        helper.setText(R.id.tv_time,sdf.format(item.getStart_time()))
                .setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_duration,item.getPlay_time()+"小时")
                .setText(R.id.tv_detail,item.getDay_road())
                .setVisible(R.id.view_top_ine,helper.getAdapterPosition() !=0);
        Banner banner = helper.getView(R.id.banner);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(mContext).load(path).into(imageView);
            }
        });
        if (!TextUtils.isEmpty(item.getImages())) {
            banner.setVisibility(View.VISIBLE);
            //设置图片集合
            List<String> images = new ArrayList<>();
            for (String img :
                    item.getImages().split(",")) {
//                img = AppUtil.replaceDomin(img);
                images.add(img);
            }
            banner.setImages(images);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        } else {
            banner.setVisibility(View.GONE);
        }
    }
}
