package com.yilan.sdk.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.common.util.YLUIUtil;
import com.yilan.sdk.data.YLInit;
import com.yilan.sdk.player.ylplayer.YLPlayerConfig;

public class App extends Application {

    public static final String TAG = "YL_AD_CALLBACK";
    public static final String TAG_LITTLE = "YL_VIDEO_CALLBACK";
    public static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FSLogcat.DEBUG = true;
        YLInit.getInstance()
                .setApplication(this)
                .setAccessKey("")//填写申请的key 和token
                .setAccessToken("")
                .build();
        // 是否启用视频缓存功能，默认开启
        YLPlayerConfig.config()
                .cacheEnable(true);
        YLPlayerConfig.config()
                .setVideoSurfaceModel(YLPlayerConfig.SURFACE_MODEL_CROP);

//在以上配置完成后，初始化播放sdk 必须调用
        YLPlayerConfig.config().build(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getInstance() {
        return instance;
    }
}
