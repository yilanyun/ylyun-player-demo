package com.yilan.sdk.sdkdemo.ugcfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.sdkdemo.R;

public class UgcFeedFragment extends Fragment {

    IYLPlayerEngine playerEngine;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    public UgcFeedFragment() {
    }

    public static UgcFeedFragment newInstance() {
        UgcFeedFragment fragment = new UgcFeedFragment();
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
        return inflater.inflate(R.layout.fragment_feed_ugc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_view);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);

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