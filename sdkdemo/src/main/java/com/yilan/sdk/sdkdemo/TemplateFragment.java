package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;

public class TemplateFragment extends Fragment {
    IYLPlayerEngine playerEngine;
    public TemplateFragment() {
    }

    public static TemplateFragment newInstance() {
        TemplateFragment fragment = new TemplateFragment();
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
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}