package com.cy.travelking.activity;

import android.view.View;
import android.widget.TextView;

import com.cy.travelking.R;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class ChooseTimeActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.picker_year)
    NumberPickerView mPickerYear;
    @BindView(R.id.picker_month)
    NumberPickerView mPickerMonth;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private int year;
    private int month;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_choose_time;
    }

    @Override
    protected void initData() {
        titleBar.setTitle("选择时间");
        titleBar.setActionTextColor(getResources().getColor(R.color.white));
        titleBar.addAction(new TitleBar.TextAction("完成") {
            @Override
            public void performAction(View view) {
                EventBusUtil.post(MessageConstant.CHOOSE_SETTLED_TIME,mTvTime.getText().toString());
                finish();
            }
        });
        year = getIntent().getIntExtra("year", AppUtil.getYear());
        month = getIntent().getIntExtra("month", AppUtil.getYear());
        mTvTime.setText(year + " - " + month);
        String yearArr[] = new String[5];
        List yearList = new ArrayList();
        for (int i = 4; i >= 0; i--) {
            yearList.add(AppUtil.getYear() - i + "");
        }
        for (int i = 0; i < yearList.size(); i++) {
            yearArr[i] = yearList.get(i) + "";
        }
        mPickerYear.setDisplayedValues(yearArr);
        mPickerYear.setMinValue(0);
        mPickerYear.setMaxValue(yearList.size() - 1);
        mPickerYear.setValue(yearList.indexOf(year + ""));
        mPickerYear.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Integer.parseInt(mPickerYear.getContentByCurrValue()) == AppUtil.getYear()
                                && Integer.parseInt(mPickerMonth.getContentByCurrValue()) > AppUtil.getMonth()) {
                            mPickerYear.setValue(yearList.indexOf(AppUtil.getYear() + ""));
                            mPickerMonth.setValue(AppUtil.getMonth() - 1);
                            mTvTime.setText(AppUtil.getYear() + " - " + AppUtil.getMonth());
                        } else {
                            mTvTime.setText(mPickerYear.getContentByCurrValue() + " - " + mPickerMonth.getContentByCurrValue());
                        }
                    }
                });
            }
        });
        mPickerMonth.setMinValue(0);
        mPickerMonth.setMaxValue(11);
        mPickerMonth.setValue(month - 1);
        mPickerMonth.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                                if (Integer.parseInt(mPickerYear.getContentByCurrValue()) == AppUtil.getYear()
                        && Integer.parseInt(mPickerMonth.getContentByCurrValue()) > AppUtil.getMonth()) {
                    mPickerYear.setValue(yearList.indexOf(AppUtil.getYear() + ""));
                    mPickerMonth.setValue(AppUtil.getMonth() - 1);
                    mTvTime.setText(AppUtil.getYear() + " - " + AppUtil.getMonth());
                } else {
                    mTvTime.setText(mPickerYear.getContentByCurrValue() + " - " + mPickerMonth.getContentByCurrValue());
                }
                    }
                });
            }
        });
    }
}
