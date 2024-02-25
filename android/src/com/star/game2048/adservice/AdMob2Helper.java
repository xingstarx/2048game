package com.star.game2048.adservice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdMob2Helper implements Ad {

//    private static final String TopAdUnitId = "ca-app-pub-3940256099942544/6300978111";//测试广告单元
    private static final String TopAdUnitId = "ca-app-pub-6001362307342407/9144185400";//真实广告单元
    private final int SHOW_TOP_ADS = 0;
    private final int SHOW_BOTTOM_ADS = 1;
    private final int SHOW_TOP_BOTTOM_ADS = 2;
    private final int SHOW_NONE_ADS = 3;
    protected Activity activity;
    private AdView topView;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOP_ADS: {
                    topView.setVisibility(View.VISIBLE);
                    break;
                }
                case SHOW_BOTTOM_ADS: {
                    topView.setVisibility(View.GONE);
                    break;
                }
                case SHOW_TOP_BOTTOM_ADS: {
                    topView.setVisibility(View.VISIBLE);
                    break;
                }
                case SHOW_NONE_ADS: {
                    topView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    public AdMob2Helper(Activity activity) {
        this(activity, true);
    }

    public AdMob2Helper(Activity activity, boolean b) {
        this.activity = activity;
        if (b) {
            initView();
        }
    }

    protected void initView() {

        // Create and setup the AdMobHelper view
        topView = new AdView(activity);
        topView.setAdSize(AdSize.BANNER);
        topView.setAdUnitId(TopAdUnitId);

        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        //adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        topView.loadAd(adRequestBuilder.build());
    }


    @Override
    public void embedView(ViewGroup layout) {
        // Add the AdMobHelper view
        if (layout instanceof LinearLayout) {
            LinearLayout.LayoutParams topParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(topView, topParams);
            topView.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public void resume() {
        if (topView != null) topView.resume();
    }

    @Override
    public void pause() {
        if (topView != null) topView.pause();
    }

    @Override
    public void destroy() {
        if (topView != null) topView.destroy();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void showAd(boolean isTop, boolean isBottom) {
        if (isBottom && isTop)
            handler.sendEmptyMessage(SHOW_TOP_BOTTOM_ADS);
        else if (!isBottom && !isTop)
            handler.sendEmptyMessage(SHOW_NONE_ADS);
        else if (isTop && !isBottom)
            handler.sendEmptyMessage(SHOW_TOP_ADS);
        else if (!isTop && isBottom)
            handler.sendEmptyMessage(SHOW_BOTTOM_ADS);
    }


    public void showOrLoadInterstitial() {
    }

    @Override
    public boolean showVideoAd(boolean isReward) {
        return false;
    }
}
