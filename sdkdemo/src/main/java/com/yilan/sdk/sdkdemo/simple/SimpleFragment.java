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
import com.yilan.sdk.player.ylplayer.callback.OnSimplePlayerCallBack;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLMultiPlayerEngine;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;
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
        playerContainer = view.findViewById(R.id.player_container);
        editText = view.findViewById(R.id.edit_input_url);
        preEditText = view.findViewById(R.id.edit_input_preurl);
        playerEngine = YLMultiPlayerEngine.getEngineByContainer(playerContainer);
        playerContainer.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = playerContainer.getLayoutParams();
                params.height = FSScreen.getScreenWidth() * 9 / 16;
                playerContainer.setLayoutParams(params);
            }
        });
        playerContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerEngine.play("adfadffwe", MockData.getPlayerUrl(), playerContainer, R.id.img_cover, PlayerStyle.STYLE_MATCH);
            }
        },100);
        playerEngine.setPlayerCallBack(new OnSimplePlayerCallBack(){
            @Override
            public void onError(String pager, String videoID, String taskID) {
                super.onError(pager, videoID, taskID);
                ToastUtil.show(getActivity(),"播放失败");
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
    public void onDestroy() {
        super.onDestroy();
        if (playerEngine != null) {
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
     *
     */
    public void onPauseClick() {
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    /**
     * 从edit的url
     *
     */
    public void playWithUrl() {
        String url = editText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play("playurl", url, playerContainer, R.id.img_cover, PlayerStyle.STYLE_MATCH);
        }
    }

    public void preloadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.prePlay("preplayer001", url);
        }
    }

    public void playPreLoadUrl() {
        String url = preEditText.getText().toString();
        if (!TextUtils.isEmpty(url)) {
            playerEngine.play("preplayer001", url, playerContainer, R.id.img_cover, PlayerStyle.STYLE_MATCH);
        }
    }
}