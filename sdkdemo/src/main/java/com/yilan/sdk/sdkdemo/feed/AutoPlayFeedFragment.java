package com.yilan.sdk.sdkdemo.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.ui.inter.OnItemClickListener;
import com.yilan.sdk.common.ui.recycle.BaseViewHolder;
import com.yilan.sdk.common.ui.recycle.IViewHolderCreator;
import com.yilan.sdk.common.ui.recycle.ViewAttachedToWindowListener;
import com.yilan.sdk.common.ui.recycle.YLRecycleAdapter;
import com.yilan.sdk.player.ylplayer.PlayerStyle;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayer;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

import java.util.List;

public class AutoPlayFeedFragment extends Fragment {

    IYLPlayer playerEngine;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    FeedMedia currentMedia;
    private List<FeedMedia> mockFeed;

    public static AutoPlayFeedFragment newInstance() {
        AutoPlayFeedFragment fragment = new AutoPlayFeedFragment();
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
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View viewRoot, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(viewRoot, savedInstanceState);
        recyclerView = viewRoot.findViewById(R.id.rv_video);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new FeedItemDecoration());
        YLRecycleAdapter<FeedMedia> adapter = new YLRecycleAdapter<FeedMedia>()
                .itemCreator(new IViewHolderCreator<FeedMedia>() {
                    @Override
                    public BaseViewHolder<FeedMedia> createViewHolder(Context context, ViewGroup viewGroup, int i) {
                        return new FeedViewHolder(context, viewGroup);
                    }
                }).clickListener(new OnItemClickListener<FeedMedia>() {
                    @Override
                    public void onClick(View view, int position, FeedMedia feedMedia) {
                        //??????item????????????
                        playVideo(feedMedia, position);
                    }
                }).viewAttachListener(new ViewAttachedToWindowListener() {
                    @Override
                    public void onViewAttachedToWindow(BaseViewHolder baseViewHolder) {

                    }

                    @Override
                    public void onViewDetachedFromWindow(BaseViewHolder holder) {
                        if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE
                                && currentMedia == holder.getData()) {
                            playerEngine.stop();
                        }
                    }
                });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int first = manager.findFirstCompletelyVisibleItemPosition();
                    int last = manager.findLastCompletelyVisibleItemPosition();
                    int position = first + (last - first) / 2;
                    if (position >= 0 ) {
                        playVideo(mockFeed.get(position),position);
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
        mockFeed = MockData.getMockFeed();
        adapter.setDataList(mockFeed);
        //??????????????????
        ViewGroup playerContainer = viewRoot.findViewById(R.id.feed_player_container_inner);
        playerEngine = YLPlayerFactory.createEngine(playerContainer)
                .videoLoop(false).withController(new PGCPlayerUI());
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                playVideo(mockFeed.get(0),0);
            }
        });
    }

    private void playVideo(FeedMedia feedMedia, int position) {
        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof FeedViewHolder) {
            currentMedia = feedMedia;
            playerEngine.play(new TaskInfo.Builder()
                    .url(feedMedia.url)
                    .coverID(R.id.layout_content)
                    .playerStyle(PlayerStyle.STYLE_MATCH)
                    .videoID(feedMedia.videoId).build(), ((FeedViewHolder) holder).contentContainer);
        }
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