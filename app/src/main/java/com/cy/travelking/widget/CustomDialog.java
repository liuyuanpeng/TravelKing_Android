package com.cy.travelking.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cy.travelking.R;

/**
 * Author: CY
 * Date: 2019/4/30
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int layout) {
        super(context, R.style.DialogTheme);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

}
