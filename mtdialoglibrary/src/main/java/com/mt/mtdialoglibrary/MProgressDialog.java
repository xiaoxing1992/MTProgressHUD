package com.mt.mtdialoglibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mt.mtdialoglibrary.config.MDialogConfig;
import com.mt.mtdialoglibrary.utils.MSizeUtils;
import com.mt.mtdialoglibrary.view.MProgressWheel;


/**
 * Created by renzhengwei on 2017/8/9.
 * 进度Dialog
 */

public class MProgressDialog {

    private static Dialog mDialog;
    private static MDialogConfig mDialogConfig;

    //布局
    private static RelativeLayout dialog_window_background;
    private static RelativeLayout dialog_view_bg;
    private static MProgressWheel progress_wheel;
    private static TextView tv_show;


    private static void initDialog(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mt_progress_dialog_layout, null);// 得到加载view
        mDialog = new Dialog(mContext, R.style.MTCustomDialog);// 创建自定义样式dialog
        mDialog.setCancelable(false);// 不可以用“返回键”取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mProgressDialogView);// 设置布局

        //设置整个Dialog的宽高
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        mDialog.getWindow().setAttributes(layoutParams);


        //布局相关
        dialog_window_background =  mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg =  mProgressDialogView.findViewById(R.id.dialog_view_bg);
        progress_wheel =  mProgressDialogView.findViewById(R.id.progress_wheel);
        tv_show =  mProgressDialogView.findViewById(R.id.tv_show);

        //默认相关
        progress_wheel.spin();

        //设置配置
        if (mDialogConfig == null) {
            mDialogConfig = new MDialogConfig.Builder().build();
        }
        configView(mContext);
        //点击事件
        dialog_window_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消Dialog
                if (mDialogConfig != null && mDialogConfig.canceledOnTouchOutside) {
                    dismissProgress();
                }
            }
        });
    }

    private static void configView(Context mContext) {
        mDialog.setCancelable(mDialogConfig.cancelable);
        mDialog.setCanceledOnTouchOutside(mDialogConfig.canceledOnTouchOutside);
        dialog_window_background.setBackgroundColor(mDialogConfig.backgroundWindowColor);

        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setColor(mDialogConfig.backgroundViewColor);
        myGrad.setStroke(MSizeUtils.dp2px(mContext, mDialogConfig.strokeWidth), mDialogConfig.strokeColor);
        myGrad.setCornerRadius(MSizeUtils.dp2px(mContext, mDialogConfig.cornerRadius));
        dialog_view_bg.setBackground(myGrad);



        progress_wheel.setBarColor(mDialogConfig.progressColor);
        progress_wheel.setBarWidth(MSizeUtils.dp2px(mContext, mDialogConfig.progressWidth));
        progress_wheel.setRimColor(mDialogConfig.progressRimColor);
        progress_wheel.setRimWidth(mDialogConfig.progressRimWidth);

        tv_show.setTextColor(mDialogConfig.textColor);
        tv_show.setTextSize(mDialogConfig.textSize);
    }

    public static void showProgress(Context context) {
        showProgress(context, "加载中");
    }

    public static void showProgress(Context context, String msg) {
        showProgress(context, msg, null);
    }

    public static void showProgress(Context context, MDialogConfig mDialogConfig) {
        showProgress(context, "加载中", mDialogConfig);
    }

    public static void showProgress(Context context, String msg, MDialogConfig mDialogConfig) {
        MProgressDialog.mDialogConfig = mDialogConfig;
        dismissProgress();
        initDialog(context);
        if (mDialog != null && tv_show != null) {
            if (TextUtils.isEmpty(msg)) {
                tv_show.setVisibility(View.GONE);
            } else {
                tv_show.setVisibility(View.VISIBLE);
                tv_show.setText(msg);
            }
            mDialog.show();
        }
    }

    public static void dismissProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            //判断是不是有监听
            if(mDialogConfig.onDialogDismissListener != null){
                mDialogConfig.onDialogDismissListener.onDismiss();
            }
            //清除
            mDialog = null;
            mDialogConfig = null;
            dialog_window_background = null;
            dialog_view_bg = null;
            progress_wheel = null;
            tv_show = null;
        }
    }

    public static boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }
}
