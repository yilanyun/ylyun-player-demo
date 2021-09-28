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

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.player.PlayerEngineView;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

/**
 * 通过PlayerEngineView 播放视频
 */
public class SimpleViewFragment extends Fragment {

    PlayerEngineView playerView;
    EditText editText;
    EditText preEditText;

    public static SimpleViewFragment newInstance() {
        SimpleViewFragment fragment = new SimpleViewFragment();
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
        return inflater.inflate(R.layout.activity_player2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.edit_input_url);
        preEditText = view.findViewById(R.id.edit_input_preurl);
        /**
         * 在布局中直接使用 PlayerEngineView 来播放视频
         * 可以将PlayerEngineView 作为封面ImageView的父容器来使用，也可以单独使用 {@link R.layout.activity_player2}
         * PlayerEngineView 的功能接口和Engine基本想同
         * 它是一个FrameLayout，可以将视频封面等放在此布局下面
         */
        playerView = view.findViewById(R.id.view_playerView);



        /**
         *
         * 调用play播放视频
         * 其中TaskInfo表示本次播放的任务，传入参数
         * videoID：视频的id，要保证和视频对应
         * title：可选参数，若传入此参数，将会在 controller 的ui上显示，详见 {@link SimpleWithControllerFragment}
         * url:视频地址
         * coverID：该视频的封面 的view，在视频播放时，会将该view隐藏，可选参数
         */
        playerView.play(new TaskInfo.Builder().videoID("adfadffwe").coverID(R.id.img_cover).title("测试视频").url(MockData.getPlayerUrl()).build());


        playerView.setPlayerCallBack(new OnSimplePlayerCallBack() {
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
        if (playerView != null) {
            /**
             * 暂停
             */
            playerView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playerView != null) {
            /**
             * 续播
             */
            playerView.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
            /**
             * 释放播放器
             */
            playerView.release();
        }
    }

    /**
     * 点击播放
     */
    public void onPlay() {
        if (playerView != null) {
            playerView.resume();
        }
    }

    /**
     * 点击暂停
     */
    public void onPauseClick() {
        if (playerView != null) {
            playerView.pause();
        }
    }

    /**
     * 从edit的url
     */
    public void playWithUrl() {
        String url = editText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerView.play(new TaskInfo.Builder().videoID("playurl").url(url).coverID(R.id.img_cover).build());
        }
    }

    public void preloadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            /**
             * 在视频播放前，可通过此方法预加载视频
             */
            playerView.prePlay(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build());
        }
    }

    public void playPreLoadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerView.play(new TaskInfo.Builder().videoID("preplayer001").url(url).coverID(R.id.img_cover).build());
        }
    }
}