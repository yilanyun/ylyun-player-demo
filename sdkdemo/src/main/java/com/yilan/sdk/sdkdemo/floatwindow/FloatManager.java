package com.yilan.sdk.sdkdemo.floatwindow;

import android.view.View;

import com.yilan.sdk.data.entity.ITaskInfo;
import com.yilan.sdk.player.PlayerEngineView;
import com.yilan.sdk.sdkdemo.App;
import com.yilan.sdk.sdkdemo.Utils;

/**
 * 悬浮播放
 * Created by dueeeke on 2018/3/30.
 */

public class FloatManager {

    private static FloatManager instance;
    private PlayerEngineView mVideoView;
    private FloatView mFloatView;
    private boolean mIsShowing;


    private FloatManager() {
        mVideoView = new PlayerEngineView(App.getInstance());
        mFloatView = new FloatView(App.getInstance(), 0, 0);
    }

    public static FloatManager getInstance() {
        if (instance == null) {
            synchronized (FloatManager.class) {
                if (instance == null) {
                    instance = new FloatManager();
                }
            }
        }
        return instance;
    }

    public  PlayerEngineView getPlayerView() {
        return mVideoView;
    }

    public void startFloatWindow(ITaskInfo taskInfo) {
        if (mIsShowing) return;
        Utils.removeViewFormParent(mVideoView);
        mVideoView.play(taskInfo);
        mFloatView.addView(mVideoView);
        mFloatView.addToWindow();
        mIsShowing = true;
    }

    public void stopFloatWindow() {
        if (!mIsShowing) return;
        mFloatView.removeFromWindow();
        Utils.removeViewFormParent(mVideoView);
        mIsShowing = false;
    }

    public void pause() {
        if (mIsShowing) return;
        mVideoView.pause();
    }

    public void resume() {
        if (mIsShowing) return;
        mVideoView.resume();
    }

    public void reset() {
        if (mIsShowing) return;
        Utils.removeViewFormParent(mVideoView);
        mVideoView.release();
        mVideoView.withController(null);
        instance = null;
    }

    public boolean onBackPress() {
        return !mIsShowing;
    }

    public boolean isStartFloatWindow() {
        return mIsShowing;
    }

    /**
     * 显示悬浮窗
     */
    public void setFloatViewVisible() {
        if (mIsShowing) {
            mVideoView.resume();
            mFloatView.setVisibility(View.VISIBLE);
        }
    }

}
