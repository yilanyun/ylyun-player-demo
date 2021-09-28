package com.yilan.sdk.sdkdemo.customui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yilan.sdk.common.executor.Dispatcher;
import com.yilan.sdk.common.executor.handler.YLCoroutineScope;
import com.yilan.sdk.common.executor.handler.YLJob;
import com.yilan.sdk.player.utils.TimeUtil;
import com.yilan.sdk.player.ylplayer.PlayerState;
import com.yilan.sdk.player.ylplayer.callback.OnPlayerCallBack;
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.player.ylplayer.ui.AbsYLPlayerUI;
import com.yilan.sdk.reprotlib.body.player.PlayData;
import com.yilan.sdk.sdkdemo.R;

/**
 * 自定义播放器UI需要继承自SDK内部提供的{@link AbsYLPlayerUI}，并重写必须的方法来完成UI的填充
 * SDK内部提供了几个完成的控制器UI以供使用
 * 1.横屏页面使用的{@link com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI}
 * 2.带手势控制的{@link com.yilan.sdk.player.ylplayer.ui.TouchPlayerUI}
 * 3.小视频页面使用的{@link com.yilan.sdk.player.ylplayer.ui.UGCPlayerUI}
 * <p>
 * 若以上场景都不满足，参考自定义UI使用来个性化定制
 */
public class CustomPlayerUI extends AbsYLPlayerUI implements SeekBar.OnSeekBarChangeListener {

    private View uiController;
    private ImageView playIcon;
    private ImageView fullScreen;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private TextView currentTime;
    private TextView totalTime;
    private boolean isSeekTouching = false;

    /**
     * 初始化控制器ui并返回
     *
     * @param parent
     * @return
     */
    @Override
    protected View OnCreateView(ViewGroup parent) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_ui, null);
        currentTime = rootView.findViewById(R.id.curr_time);
        totalTime = rootView.findViewById(R.id.total_time);
        fullScreen = rootView.findViewById(R.id.fullscreen);
        uiController = rootView.findViewById(R.id.bottom_container);
        seekBar = rootView.findViewById(R.id.seekBar);
        playIcon = rootView.findViewById(R.id.iv_play);
        progressBar = rootView.findViewById(R.id.video_progress);
        seekBar.setOnSeekBarChangeListener(this);
        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerEngine != null && playerEngine.get() != null) {
                    if (playerEngine.get().getPlayerState() == PlayerState.PAUSE
                            || playerEngine.get().getPlayerState() == PlayerState.PREPARED) {
                        playerEngine.get().resume();
                    } else {
                        playerEngine.get().pause();
                    }
                }
                startTimeTask();
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeTask();
                showOrHideControllerUI();
            }
        });
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerEngine.get() != null) {
                    if (playerEngine.get().isFullScreen()) {
                        playerEngine.get().exitFull();
                    } else {
                        playerEngine.get().toFull();
                    }
                }
            }
        });
        return rootView;
    }

    public void showOrHideControllerUI() {
        if (uiController.getVisibility() == View.VISIBLE) {
            uiController.setVisibility(View.INVISIBLE);
            uiController.startAnimation(alphaAnimation(1, 0));
            progressBar.setVisibility(View.VISIBLE);
        } else {
            uiController.setVisibility(View.VISIBLE);
            uiController.startAnimation(alphaAnimation(0, 1));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private AlphaAnimation alphaAnimation(int from, int to) {
        AlphaAnimation animation = new AlphaAnimation(from, to);
        animation.setDuration(300);
        return animation;
    }

    /**
     * 返回界面的根布局id，SDK内部通过此id查找添加控制器ui
     *
     * @return
     */
    @Override
    public int getRootID() {
        return R.id.controller_root;
    }

    /**
     * 播放器状态改变的监听，详细状态参考{@link PlayerState},状态含义文档有对应说明
     *
     * @param data
     * @param oldState
     * @param newState
     */
    @Override
    public void onPlayStateChange(PlayData data, PlayerState oldState, final PlayerState newState) {
        super.onPlayStateChange(data, oldState, newState);
        if (rootView != null) {
            rootView.post(new Runnable() {
                @Override
                public void run() {
                    if (newState == PlayerState.PAUSE) {
                        onPlayerPause();
                    } else {
                        onPlayerResume();
                    }
                }
            });
        }
    }

    protected void onPlayerResume() {
        if (playIcon != null) {
            playIcon.setSelected(true);
        }
    }

    protected void onPlayerPause() {
        if (playIcon != null) {
            playIcon.setSelected(false);
        }
    }

    /**
     * 播放进度回调，封装在PlayData里
     *
     * @param data
     */
    @Override
    public void onProgress(final PlayData data) {
        super.onProgress(data);
        if (progressBar != null) {
            if (!isSeekTouching) {
                setMax((int) data.duration);
                progressBar.setProgress((int) data.pos);
                seekBar.setProgress((int) data.pos);
            }
        }
    }

    int lastMax = 0;

    private void setMax(int max) {
        if (max == lastMax) return;
        lastMax = max;
        progressBar.setMax(max);
        seekBar.setMax(max);
        totalTime.setText(TimeUtil.convertTime(max));
    }

    /**
     * 播放器UI需要重置。
     */
    @Override
    public void resetUI() {
        super.resetUI();
        if (rootView != null) {
            rootView.post(new Runnable() {
                @Override
                public void run() {
                    onResetUI();
                }
            });
        }
    }

    YLJob job;

    private void startTimeTask() {
        if (job != null) {
            job.cancel();
            job = null;
        }
        job = YLCoroutineScope.instance.executeDelay(Dispatcher.MAIN, new Runnable() {
            @Override
            public void run() {
                if (playerEngine != null && playerEngine.get() != null) {
                    if (playerEngine.get().getPlayerState() != PlayerState.PAUSE) {
                        if (uiController.getVisibility() == View.GONE || uiController.getVisibility() == View.INVISIBLE) {
                            return;
                        }
                        uiController.setVisibility(View.GONE);
                        uiController.startAnimation(alphaAnimation(1, 0));
                    }
                }
            }
        }, 3000);
    }

    protected void onResetUI() {
        if (progressBar != null) {
            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 全屏的回调
     */
    @Override
    public void onFull() {
        super.onFull();
    }

    /**
     * 退出全屏时的回调
     */
    @Override
    public void onExitFull() {
        super.onExitFull();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentTime.setText(TimeUtil.convertTime(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (job != null) {
            job.cancel();
        }
        isSeekTouching = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeekTouching = false;
        playerEngine.get().seekTo(seekBar.getProgress());
        startTimeTask();
    }
}
