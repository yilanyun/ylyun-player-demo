package com.yilan.sdk.sdkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.widget.FrameLayout;

import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;

public class PlayerDetailActivity extends AppCompatActivity {

    private String videoId;
    private FrameLayout container;
    private IYLPlayerEngine playerEngine;

    public static void start(Context context, String videoId) {
        Intent intent = new Intent(context, PlayerDetailActivity.class);
        intent.putExtra("media", videoId);
        context.startActivity(intent);
    }

    public static void start(Context context, String videoId, Bundle bundle) {
        Intent intent = new Intent(context, PlayerDetailActivity.class);
        intent.putExtra("media", videoId);
        context.startActivity(intent, bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoId = getIntent().getStringExtra("media");
        setContentView(R.layout.activity_player_jump2);
        container = findViewById(R.id.player);
        /**
         * 给新的container 设置 TransitionName 。此view 负责执行过度动画
         */
        ViewCompat.setTransitionName(container, "player");

        /**
         * 通过id查找可用的视频播放器核心
         */
        playerEngine = YLPlayerFactory.findEngineByID(videoId);
        if (playerEngine == null) {
            /**
             * 如果没有找到，重新创建
             */
            playerEngine = YLPlayerFactory.createEngine(container);
        }
        /**
         * 设置控制器ui
         */
        playerEngine.withController(new PGCPlayerUI());
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                playVideo();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                transition.removeListener(this);
            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void playVideo() {
        TaskInfo taskInfo = new TaskInfo.Builder()
                .videoID(videoId)
                .title("测试视频")
                .url(MockData.getPlayerUrl())
                .build();
        playerEngine.play(taskInfo, container);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playerEngine != null) {
            playerEngine.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 页面关闭时，需要释放播放器资源
         */
        if (playerEngine != null) {
            playerEngine.release();
        }
    }
}