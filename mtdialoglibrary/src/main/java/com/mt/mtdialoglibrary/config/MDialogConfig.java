package com.mt.mtdialoglibrary.config;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.mt.mtdialoglibrary.listeners.OnDialogDismissListener;

/**
 * Created by renzhengwei on 2017/8/11.
 */

public class MDialogConfig {
    //点击返回键可以取消
    public boolean cancelable = true;
    //点击外部可以取消
    public boolean canceledOnTouchOutside = false;
    //窗体背景色
    public int backgroundWindowColor = Color.TRANSPARENT;
    //View背景色
    public int backgroundViewColor = Color.parseColor("#FFF9F9FB");
    //View边框的颜色
    public int strokeColor = Color.TRANSPARENT;
    //View背景圆角
    public float cornerRadius = 10;
    //View边框的宽度
    public float strokeWidth = 0;
    //Progress的颜色
    public int progressColor =  Color.GRAY;
    //Progress的宽度
    public float progressWidth = 2;
    //progress背景环的颜色
    public int progressRimColor = Color.TRANSPARENT;
    //progress背景环的宽度
    public int progressRimWidth = 0;
    //文字的颜色
    public int textColor =  Color.GRAY;
    //文字的大小
    public int textSize = 12;
    //消失的监听
    public OnDialogDismissListener onDialogDismissListener;


    private MDialogConfig() {
    }

    public static class Builder {

        private MDialogConfig mToastConfig = null;

        public Builder() {
            mToastConfig = new MDialogConfig();
        }

        public MDialogConfig build() {
            return mToastConfig;
        }

        /**
         * 设置点击返回键取消Dialog
         */
        public Builder isCancelable(@Nullable boolean cancelable) {
            mToastConfig.cancelable = cancelable;
            return this;
        }


        /**
         * 设置点击外部取消Dialog
         *
         * @param canceledOnTouchOutside
         * @return
         */
        public Builder isCanceledOnTouchOutside(@Nullable boolean canceledOnTouchOutside) {
            mToastConfig.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setBackgroundWindowColor(@Nullable int backgroundWindowColor) {
            mToastConfig.backgroundWindowColor = backgroundWindowColor;
            return this;
        }

        public Builder setBackgroundViewColor(@Nullable int backgroundViewColor) {
            mToastConfig.backgroundViewColor = backgroundViewColor;
            return this;
        }

        public Builder setStrokeColor(@Nullable int strokeColor) {
            mToastConfig.strokeColor = strokeColor;
            return this;
        }

        public Builder setStrokeWidth(@Nullable float strokeWidth) {
            mToastConfig.strokeWidth = strokeWidth;
            return this;
        }

        public Builder setCornerRadius(@Nullable float cornerRadius) {
            mToastConfig.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setProgressColor(@Nullable int progressColor) {
            mToastConfig.progressColor = progressColor;
            return this;
        }

        public Builder setProgressWidth(@Nullable float progressWidth) {
            mToastConfig.progressWidth = progressWidth;
            return this;
        }

        public Builder setProgressRimColor(int progressRimColor) {
            mToastConfig.progressRimColor = progressRimColor;
            return this;
        }

        public Builder setProgressRimWidth(int progressRimWidth) {
            mToastConfig.progressRimWidth = progressRimWidth;
            return this;
        }

        public Builder setTextColor(@Nullable int textColor) {
            mToastConfig.textColor = textColor;
            return this;
        }

        public Builder setTextSize(@Nullable int textSize) {
            mToastConfig.textSize = textSize;
            return this;
        }

        public Builder setOnDialogDismissListener(OnDialogDismissListener onDialogDismissListener) {
            mToastConfig.onDialogDismissListener = onDialogDismissListener;
            return this;
        }
    }

}
