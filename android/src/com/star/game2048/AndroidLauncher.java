package com.star.game2048;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.star.game2048.adservice.Ad;
import com.star.game2048.adservice.AdMob2Helper;
import com.star.game2048.adservice.IActivityRequestHandler;

import java.util.UUID;

import es.dmoral.prefs.Prefs;
import io.fabric.sdk.android.Fabric;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
    public LinearLayout layout;
    View gameView;
    private Ad ad;
    private static final String SP_KEY_UID = "sp_uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainGame.BANNER_HEIGHT = 50;//指定广告的高度为50

        String uid = Prefs.with(this).read(SP_KEY_UID);
        if (TextUtils.isEmpty(uid)) {
            uid = UUID.randomUUID().toString();
            Prefs.with(this).write(SP_KEY_UID, uid);
        }
        Fabric.with(this, new Crashlytics());
        Crashlytics.setUserIdentifier(uid);
		MobileAds.initialize(this, "ca-app-pub-6001362307342407~2606697218");
//
//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new MainGame(), config);

        layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(params);
        ad = new AdMob2Helper(this);
        ad.embedView(layout);


        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        gameView = initializeForView(new MainGame(), config);
        LinearLayout.LayoutParams gameViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        gameView.setLayoutParams(gameViewParams);
        layout.addView(gameView);

        setContentView(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ad.resume();
    }

    @Override
    protected void onPause() {

        ad.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        ad.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ad.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ad.stop();
    }

    @Override
    public void showBannerAds(boolean isTop,boolean isBottom) {
        ad.showAd(isTop,isBottom);
    }

    @Override
    public void showOrLoadInterstitial() {
        ad.showOrLoadInterstitial();
    }

    @Override
    public boolean showVideoAd(boolean isRewarded) {
        return ad.showVideoAd(isRewarded);
    }

}
