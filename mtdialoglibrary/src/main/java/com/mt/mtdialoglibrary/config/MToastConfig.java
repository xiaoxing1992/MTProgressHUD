package com.mt.mtdialoglibrary.config;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by renzhengwei on 2017/8/11.
 */

public class MToastConfig {

    public enum MToastGravity {
        CENTRE, BOTTOM
    }

    public int ToastTextColor = Color.parseColor("#FFFFFFFF");
    public int ToastBackgroundColor = Color.parseColor("#CC000000");
    public float ToastBackgroundCornerRadius = 10.0f;
    public float ToastBackgroundStrokeWidth = 0.0f;
    public int ToastBackgroundStrokeColor = Color.parseColor("#00000000");
    public MToastGravity ToastGravity = MToastGravity.CENTRE;
    public Drawable ToastIcon = null;


    private MToastConfig() {
    }

    public static class Builder {

        private MToastConfig mToastConfig = null;

        public Builder() {
            mToastConfig = new MToastConfig();
        }

        public MToastConfig build() {
            return mToastConfig;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            mToastConfig.ToastTextColor = textColor;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            mToastConfig.ToastBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackgroundCornerRadius(float radius) {
            mToastConfig.ToastBackgroundCornerRadius = radius;
            return this;
        }

        public Builder setGravity(MToastGravity toastGravity) {
            mToastConfig.ToastGravity = toastGravity;
            return this;
        }

        public Builder setToastIcon(Drawable ToastIcon) {
            mToastConfig.ToastIcon = ToastIcon;
            return this;
        }

        public Builder setBackgroundStrokeWidth(float width) {
            mToastConfig.ToastBackgroundStrokeWidth = width;
            return this;
        }

        public Builder setBackgroundStrokeColor(@ColorInt int strokeColor) {
            mToastConfig.ToastBackgroundStrokeColor = strokeColor;
            return this;
        }
    }

}
