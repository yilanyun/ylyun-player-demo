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
import com.yilan.sdk.player.ylplayer.PlayerStyle;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayer;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

public class SimpleFragment extends Fragment {

    IYLPlayer player;
    FrameLayout anchorView;
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
         * 需要播放视频的view，在播放时 通过play() 方法传给播放器，视频画面即会附着在此view上
         */
        anchorView = view.findViewById(R.id.player_container);
        editText = view.findViewById(R.id.edit_input_url);
        preEditText = view.findViewById(R.id.edit_input_preurl);
        /**
         * 在playerContainer中创建播放器
         * YLPlayerFactory.createEngine(context)通过上下文创建播放器
         */
        player = YLPlayerFactory.createEngine(view.getContext());


        anchorView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = anchorView.getLayoutParams();
                params.height = FSScreen.getScreenWidth() * 9 / 16;
                anchorView.setLayoutParams(params);
            }
        });
        /**
         *
         * 调用play播放视频
         * 其中TaskInfo表示本次播放的任务，传入参数
         * videoID：视频的id，要保证和视频对应
         * title：可选参数，若传入此参数，将会在 controller 的ui上显示，详见 {@link SimpleWithControllerFragment}
         * url:视频地址
         * coverID：该视频的封面的view 的id，在视频播放时，会将该view隐藏，可选参数
         * anchorView ：需要播放视频的viewgroup，视频画面会附着在此view 上，注：如果设置了coverID，则anchorView应该时封面view 的父布局
         */
        player.play(new TaskInfo.Builder().videoID("adfadffwe").title("测试视频").url(MockData.getPlayerUrl()).build(), anchorView);


        player.setPlayerCallBack(new OnSimplePlayerCallBack() {
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
        if (player != null) {
            /**
             * 暂停
             */
            player.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            /**
             * 续播
             */
            player.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            /**
             * 释放播放器
             */
            player.release();
        }
    }

    /**
     * 点击播放
     */
    public void onPlay() {
        if (player != null) {
            player.resume();
        }
    }

    /**
     * 点击暂停
     */
    public void onPauseClick() {
        if (player != null) {
            player.pause();
        }
    }

    /**
     * 从edit的url
     */
    public void playWithUrl() {
        String url = editText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            player.play(new TaskInfo.Builder().videoID("playurl").url(url).coverID(R.id.img_cover).cacheEnable(true).title("视频标题").playerStyle(PlayerStyle.STYLE_MATCH).build(), anchorView);
        }
    }

    public void preloadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            /**
             * 在视频播放前，可通过此方法预加载视频
             */
            player.prePlay(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build());
        }
    }

    public void playPreLoadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            player.play(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build(), anchorView);
        }
    }
}