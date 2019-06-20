package com.mt.mtdialoglibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mt.mtdialoglibrary.utils.MSizeUtils;
import com.mt.mtdialoglibrary.view.MCircularProgressBar;

/**
 * Created by renzhengwei on 2017/12/29.
 * 带进度条的Dialog
 */

public class MProgressBarCancelDialog {

    //圆形的
    public final static int MProgressBarDialogStyle_Circle = 1;

    //动画时长
    private long mDuration = 300;

    private Context mContext;
    private Dialog mDialog;

    private MProgressBarCancelDialog.Builder mBuilder;

    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private TextView tvShow;
    private TextView tvCancel;
    private ImageView iv_status;
    private MCircularProgressBar circularProgressBar;

    public MProgressBarCancelDialog(Context context) {
        this(context, new MProgressBarCancelDialog.Builder(context));
    }

    public MProgressBarCancelDialog(Context context, MProgressBarCancelDialog.Builder builder) {
        mContext = context;
        mBuilder = builder;
        if (mBuilder == null) {
            mBuilder = new MProgressBarCancelDialog.Builder(mContext);
        }
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mt_progress_bar_cancel_dialog_layout, null);// 得到加载view
        mDialog = new Dialog(mContext, R.style.MTCustomDialog);// 创建自定义样式dialog
        mDialog.setCancelable(false);// 不可以用“返回键”取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mProgressDialogView);// 设置布局

        //设置整个Dialog的宽高
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        mDialog.getWindow().setAttributes(layoutParams);

        //设置动画
        if (mBuilder.animationID != 0) {
            try {
                mDialog.getWindow().setWindowAnimations(mBuilder.animationID);
            } catch (Exception e) {

            }
        }

        //获取布局
        dialog_window_background = mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg = mProgressDialogView.findViewById(R.id.dialog_view_bg);
        tvShow = mProgressDialogView.findViewById(R.id.tvShow);
        tvCancel = mProgressDialogView.findViewById(R.id.tvCancel);
        iv_status = mProgressDialogView.findViewById(R.id.iv_status);
        circularProgressBar = mProgressDialogView.findViewById(R.id.circularProgressBar);

        circularProgressBar.setVisibility(View.GONE);

        circularProgressBar.setProgress(0);
        tvShow.setText("");

        //默认配置
        configView();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBuilder.onClickCancelListener != null) {
                    mBuilder.onClickCancelListener.onClickCancel();
                }
            }
        });
    }

    private void configView() {
        if (mBuilder == null) {
            mBuilder = new MProgressBarCancelDialog.Builder(mContext);
        }

        dialog_window_background.setBackgroundColor(mBuilder.backgroundWindowColor);
        tvShow.setTextColor(mBuilder.textColor);

        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(mBuilder.backgroundViewColor);
        myGrad.setStroke(MSizeUtils.dp2px(mContext, mBuilder.strokeWidth), mBuilder.strokeColor);
        myGrad.setCornerRadius(MSizeUtils.dp2px(mContext, mBuilder.cornerRadius));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dialog_view_bg.setBackground(myGrad);
        } else {
            dialog_view_bg.setBackgroundDrawable(myGrad);
        }

        //circularProgressBar 配置
        circularProgressBar.setBackgroundColor(mBuilder.progressbarBackgroundColor);
        circularProgressBar.setColor(mBuilder.progressColor);
        circularProgressBar.setProgressBarWidth(MSizeUtils.dp2px(mContext, mBuilder.circleProgressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(MSizeUtils.dp2px(mContext, mBuilder.circleProgressBarBackgroundWidth));

    }

    public void showProgress(int progress, String message) {
        showProgress(progress, 0, message, true);
    }

    public void showProgress(int progress, String message, boolean animate) {
        showProgress(progress, 0, message, animate);
    }

    public void showProgress(final int progress, final int secondProgress, String message) {
        showProgress(progress, secondProgress, message, true);
    }

    /**
     * 显示dialog
     *
     * @param progress       当前进度
     * @param secondProgress 二级进度
     * @param message        消息体
     * @param animate        是否平滑过度动画
     */
    public void showProgress(final int progress, final int secondProgress, String message, boolean animate) {
        if (circularProgressBar.getVisibility() == View.GONE) {
            circularProgressBar.setVisibility(View.VISIBLE);
        }
        //添加动画平滑过度
        circularProgressBar.setProgress(progress, animate);
        tvShow.setText(message);
        mDialog.show();
    }

    public void showStatusProgress(int resId, String message) {
        if (circularProgressBar.getVisibility() == View.VISIBLE) {
            circularProgressBar.setVisibility(View.GONE);
        }

        if (iv_status.getVisibility() == View.GONE) {
            iv_status.setVisibility(View.VISIBLE);
        }

        if (tvCancel.getVisibility() == View.VISIBLE) {
            tvCancel.setVisibility(View.GONE);
        }

        iv_status.setImageResource(resId);

        tvShow.setText(message);
        mDialog.show();
    }


    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        } else {
            return false;
        }
    }

    public void dismiss() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
                mContext = null;
                mBuilder = null;
                dialog_window_background = null;
                dialog_view_bg = null;
                tvShow = null;
                circularProgressBar = null;
            }
        } catch (Exception e) {

        }
    }

    public void refreshBuilder(MProgressBarCancelDialog.Builder builder) {
        mBuilder = builder;
        if (mBuilder == null) {
            mBuilder = new MProgressBarCancelDialog.Builder(mContext);
        }
        configView();
    }

    public static final class Builder {

        private Context mContext;

        //窗体背景色
        int backgroundWindowColor;
        //View背景色
        int backgroundViewColor;
        //View边框的颜色
        int strokeColor;
        //View背景圆角
        float cornerRadius;
        //View边框的宽度
        float strokeWidth;
        //文字的颜色
        int textColor;
        //Progressbar 背景色
        int progressbarBackgroundColor;
        //Progressbar 条颜色
        int progressColor;
        //水平进度条Progress圆角
        float progressCornerRadius;
        //style:0:水平，1:圆形
        int style;
        // CircleProgressbar宽度
        int circleProgressBarWidth;
        int circleProgressBarBackgroundWidth;
        // horizontalProgressBar 宽度
        int horizontalProgressBarHeight;
        //Dialog进出动画
        int animationID;

        onClickCancelListener onClickCancelListener;

        public Builder(Context context) {
            mContext = context;
            //默认配置
            backgroundWindowColor = mContext.getResources().getColor(R.color.mt_colorDialogWindowBg);
            backgroundViewColor = mContext.getResources().getColor(R.color.mt_colorDialogViewBg);
            strokeColor = mContext.getResources().getColor(R.color.mt_colorDialogTrans);
            textColor = mContext.getResources().getColor(R.color.mt_colorDialogTextColor);
            cornerRadius = 6;
            strokeWidth = 0;
            progressbarBackgroundColor = mContext.getResources().getColor(R.color.mt_colorDialogProgressBarBgColor);
            progressColor = mContext.getResources().getColor(R.color.mt_colorDialogProgressBarProgressColor);
            progressCornerRadius = 2;
            style = MProgressBarDialogStyle_Circle;
            circleProgressBarWidth = 3;
            circleProgressBarBackgroundWidth = 1;
            horizontalProgressBarHeight = 4;
            animationID = 0;

        }

        public MProgressBarCancelDialog build() {
            return new MProgressBarCancelDialog(mContext, this);
        }


        public Builder setBackgroundWindowColor(@Nullable int backgroundWindowColor) {
            this.backgroundWindowColor = backgroundWindowColor;
            return this;
        }

        public Builder setBackgroundViewColor(@Nullable int backgroundViewColor) {
            this.backgroundViewColor = backgroundViewColor;
            return this;
        }

        public Builder setStrokeColor(@Nullable int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder setStrokeWidth(@Nullable float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder setCornerRadius(@Nullable float cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setTextColor(@Nullable int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setProgressbarBackgroundColor(@Nullable int progressbarBackgroundColor) {
            this.progressbarBackgroundColor = progressbarBackgroundColor;
            return this;
        }

        public Builder setProgressColor(@Nullable int progressColor) {
            this.progressColor = progressColor;
            return this;
        }

        public Builder setProgressCornerRadius(@Nullable int progressCornerRadius) {
            this.progressCornerRadius = progressCornerRadius;
            return this;
        }

        public Builder setStyle(@Nullable int style) {
            this.style = style;
            return this;
        }

        public Builder setCircleProgressBarWidth(@Nullable int circleProgressBarWidth) {
            this.circleProgressBarWidth = circleProgressBarWidth;
            return this;
        }

        public Builder setCircleProgressBarBackgroundWidth(@Nullable int circleProgressBarBackgroundWidth) {
            this.circleProgressBarBackgroundWidth = circleProgressBarBackgroundWidth;
            return this;
        }

        public Builder setHorizontalProgressBarHeight(@Nullable int horizontalProgressBarHeight) {
            this.horizontalProgressBarHeight = horizontalProgressBarHeight;
            return this;
        }

        public Builder setAnimationID(@StyleRes int resId) {
            this.animationID = resId;
            return this;
        }

        public Builder setCancelListener(onClickCancelListener onClickCancelListener) {
            this.onClickCancelListener = onClickCancelListener;
            return this;
        }

    }


    public interface onClickCancelListener {
        void onClickCancel();
    }


}
