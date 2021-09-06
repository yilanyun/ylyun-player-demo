package com.yilan.sdk.sdkdemo.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yilan.sdk.player.ylplayer.PlayerStyle;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLMultiPlayerEngine;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;
import com.yilan.sdk.player.ylplayer.ui.TouchPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

public class SimpleWithControllerFragment extends Fragment {

    IYLPlayerEngine playerEngine;
    FrameLayout playerContainer;

    IYLPlayerEngine playerEngine2;
    FrameLayout playerContainer2;

    public SimpleWithControllerFragment() {
    }

    public static SimpleWithControllerFragment newInstance() {
        SimpleWithControllerFragment fragment = new SimpleWithControllerFragment();
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
        return inflater.inflate(R.layout.fragment_simple_with_controller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerContainer = view.findViewById(R.id.player_container);
        playerEngine = YLMultiPlayerEngine.getEngineByContainer(playerContainer)
                .withController(new PGCPlayerUI());
        final TaskInfo info = new TaskInfo.Builder().videoID("1111111111").coverID(R.id.img_cover).url(MockData.getPlayerUrl()).build();
        playerEngine.play(info, playerContainer);
        playerContainer2 = view.findViewById(R.id.player_container2);
        playerEngine2 = YLMultiPlayerEngine.getEngineByContainer(playerContainer2)
                .withController(new PGCPlayerUI().itemUI(new TouchPlayerUI()));
        playerEngine2.play(new TaskInfo.Builder().videoID("222222222").coverID(R.id.img_cover2).url(MockData.getPlayerUrl(1)).build(), playerContainer2);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerEngine != null) {
            playerEngine.pause();
        }
        if (playerEngine2 != null) {
            playerEngine2.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playerEngine != null) {
            playerEngine.resume();
        }
        if (playerEngine2 != null) {
            playerEngine2.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerEngine != null) {
            playerEngine.release();
        }
        if (playerEngine2 != null) {
            playerEngine2.release();
        }
    }
}