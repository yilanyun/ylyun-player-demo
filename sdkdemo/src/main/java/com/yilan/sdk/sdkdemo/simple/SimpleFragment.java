package com.yilan.sdk.sdkdemo.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.yilan.sdk.common.util.FSScreen;
import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

public class SimpleFragment extends Fragment {

    IYLPlayerEngine playerEngine;
    FrameLayout playerContainer;
    EditText editText;
    EditText preEditText;

    public static SimpleFragment newInstance() {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * playerContainer 用于放置播放器的容器
         */
        playerContainer = view.findViewById(R.id.player_container);
        editText = view.findViewById(R.id.edit_input_url);
        preEditText = view.findViewById(R.id.edit_input_preurl);
        /**
         * 在playerContainer中创建播放器
         * 如果需要使用预加载功能，通过此方法创建播放器引擎 YLPlayerFactory.createMultiEngine(playerContainer);
         * 通过 YLPlayerFactory.createSimpleEngine(playerContainer)创建播放器会更加节省内存
         */
        playerEngine = YLPlayerFactory.createMultiEngine(playerContainer);


        playerContainer.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = playerContainer.getLayoutParams();
                params.height = FSScreen.getScreenWidth() * 9 / 16;
                playerContainer.setLayoutParams(params);
            }
        });
        /**
         *
         * 调用play播放视频
         * 其中TaskInfo表示本次播放的任务，传入参数
         * videoID：视频的id，要保证和视频对应
         * title：可选参数，若传入此参数，将会在 controller 的ui上显示，详见 {@link SimpleWithControllerFragment}
         * url:视频地址
         * coverID：该视频的封面 的view，在视频播放时，会将该view隐藏，可选参数
         * playerContainer ：该视频所应该出现的位置，通常时 封面 view 的父布局
         */
        playerEngine.play(new TaskInfo.Builder().videoID("adfadffwe").title("测试视频").url(MockData.getPlayerUrl()).build(), playerContainer);


        playerEngine.setPlayerCallBack(new OnSimplePlayerCallBack() {
            @Override
            public void onError(String pager, String videoID, String taskID) {
                super.onError(pager, videoID, taskID);
                ToastUtil.show(getActivity(), "播放失败");
            }
        });
        view.findViewById(R.id.onPlay).setOnClickListener((v) -> {
            onPlay();
        });
        view.findViewById(R.id.onPause).setOnClickListener((v) -> {
            onPauseClick();
        });
        view.findViewById(R.id.playWithUrl).setOnClickListener((v) -> {
            playWithUrl();
        });
        view.findViewById(R.id.preloadUrl).setOnClickListener((v) -> {
            preloadUrl();
        });
        view.findViewById(R.id.playPreLoadUrl).setOnClickListener((v) -> {
            playPreLoadUrl();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerEngine != null) {
            /**
             * 暂停
             */
            playerEngine.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playerEngine != null) {
            /**
             * 续播
             */
            playerEngine.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerEngine != null) {
            /**
             * 释放播放器
             */
            playerEngine.release();
        }
    }

    /**
     * 点击播放
     */
    public void onPlay() {
        if (playerEngine != null) {
            playerEngine.resume();
        }
    }

    /**
     * 点击暂停
     */
    public void onPauseClick() {
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    /**
     * 从edit的url
     */
    public void playWithUrl() {
        String url = editText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play(new TaskInfo.Builder().videoID("playurl").url(url).coverID(R.id.img_cover).build(), playerContainer);
        }
    }

    public void preloadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            /**
             * 在视频播放前，可通过此方法预加载视频
             */
            playerEngine.prePlay(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build());
        }
    }

    public void playPreLoadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build(), playerContainer);
        }
    }
}