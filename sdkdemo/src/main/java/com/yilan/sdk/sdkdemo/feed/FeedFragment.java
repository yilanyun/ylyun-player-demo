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
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayer;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

public class FeedFragment extends Fragment {

    IYLPlayer player;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    FeedMedia currentMedia;

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
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
                            player.stop();
                        }
                    }
                });
        recyclerView.setAdapter(adapter);
        adapter.setDataList(MockData.getMockFeed());
        /**
         * ??????????????????????????????
         */
        ViewGroup playerContainer = viewRoot.findViewById(R.id.feed_player_container_inner);
        /**
         * ??????playerContainer???????????????
         * playerContainer ????????????match_parent
         * ??????createEngine(ViewGroup)???????????????????????????????????????????????????????????????????????? ?????? ????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????????????????????????????
         */
        player = YLPlayerFactory.createCloudEngine(playerContainer)
                .videoLoop(false).withController(new PGCPlayerUI());
    }

    private void playVideo(FeedMedia feedMedia, int position) {
        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof FeedViewHolder) {
            currentMedia = feedMedia;
            player.play(new TaskInfo.Builder().url(feedMedia.url).coverID(R.id.layout_content).videoID(feedMedia.videoId).build(), ((FeedViewHolder) holder).contentContainer);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}