package com.yilan.sdk.sdkdemo.floatwindow;

import android.view.View;

import com.yilan.sdk.player.PlayerEngineView;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.sdkdemo.App;
import com.yilan.sdk.sdkdemo.Utils;

/**
 * 悬浮窗管理类
 */
public class FloatManager {

    private static FloatManager instance;
    private PlayerEngineView mVideoView;
    private FloatView mFloatView;
    private boolean mIsShowing;


    private FloatManager() {
        mVideoView = new PlayerEngineView(App.getInstance());
        mFloatView = new FloatView(App.getInstance(), 0, 0);
        mFloatView.setListener(new FloatView.OnCloseListener() {
            @Override
            public void onClose() {
                stopFloatWindow();
                mVideoView.stop();
            }
        });
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

    public void startFloatWindow(TaskInfo taskInfo) {
        if (mIsShowing) return;
        Utils.removeViewFormParent(mVideoView);
        mFloatView.addView(mVideoView);
        mFloatView.addToWindow();
        mVideoView.play(taskInfo);
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

    public void release(boolean force) {
        if (mIsShowing && !force) return;
        Utils.removeViewFormParent(mVideoView);
        mVideoView.release();
        mVideoView.withController(null);
        mFloatView.removeFromWindow();
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
