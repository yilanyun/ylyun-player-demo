package com.yilan.sdk.sdkdemo.ugcfeed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.executor.Dispatcher;
import com.yilan.sdk.common.executor.handler.YLCoroutineScope;
import com.yilan.sdk.common.executor.handler.YLJob;
import com.yilan.sdk.common.ui.inter.OnItemClickListener;
import com.yilan.sdk.common.ui.recycle.BaseViewHolder;
import com.yilan.sdk.common.ui.recycle.IViewHolderCreator;
import com.yilan.sdk.common.ui.recycle.YLRecycleAdapter;
import com.yilan.sdk.player.ylplayer.PlayerState;
import com.yilan.sdk.player.ylplayer.PlayerStyle;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayer;
import com.yilan.sdk.player.ylplayer.engine.YLMultiPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.engine.YLVideoService;
import com.yilan.sdk.player.ylplayer.ui.UGCPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.feed.FeedMedia;
import com.yilan.sdk.sdkdemo.simple.SimpleWithControllerFragment;

import java.util.List;

/**
 * 类抖音小视频demo
 * 使用RecyclerView配合PagerSnapHelper实现分页滑动
 */
public class UgcFeedFragment extends Fragment {

    //播放器引擎
    IYLPlayer playerEngine;
    //播放器容器
    ViewGroup playerContainer;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    YLRecycleAdapter<FeedMedia> recycleAdapter;
    //当前播放的位置
    int currentPosition = 0;
    //模拟数据
    List<FeedMedia> mockMediaList;
    YLJob job;

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
        playerContainer = view.findViewById(R.id.little_player);
        recyclerView = view.findViewById(R.id.recycle_view);
        //初始化播放器引擎
        initPlayerEngine();
        //初始化recyclerview
        initRecycler();
        //模拟获取数据并播放
        getDataAndPlay();
    }

    /**
     * 初始化播放器
     * playerContainer：播放器的容器，需要注意的是此处的playerContainer
     * 是与recyclerView处于同一级的，并不是每个item
     */
    private void initPlayerEngine() {
        playerEngine = YLPlayerFactory
                .createEngine(playerContainer)
                .videoLoop(true)
                //播放器的控制UI
                .withController(new UGCPlayerUI());
    }

    private void initRecycler() {
        manager = new LinearLayoutManager(getContext());
        manager.setInitialPrefetchItemCount(2);
        recyclerView.setLayoutManager(manager);
        //设置item滑动效果为一次滑动一页
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recycleAdapter = new YLRecycleAdapter<FeedMedia>().itemCreator(new IViewHolderCreator<FeedMedia>() {
            @Override
            public BaseViewHolder<FeedMedia> createViewHolder(Context context, ViewGroup viewGroup, int i) {
                return new UgcViewHolder(context, viewGroup);
            }
        }).clickListener(new OnItemClickListener<FeedMedia>() {
            /*
             * 列表点击时的回调
             */
            @Override
            public void onClick(View view, int i, FeedMedia feedMedia) {
                if (playerEngine.getPlayerState() == PlayerState.PAUSE) {
                    resumeVideo();
                } else if (playerEngine.getPlayerState() == PlayerState.START || playerEngine.getPlayerState() == PlayerState.RESUME) {
                    pauseVideo();
                }
            }
        });
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = manager.findFirstCompletelyVisibleItemPosition();
                    if (position >= 0) {
                        updatePosition(position);
                    }
                }
            }
        });
    }

    /**
     * 获取模拟数据，并播放
     * 需要注意：播放要再列表数据渲染成功后调用
     */
    private void getDataAndPlay() {
        mockMediaList = MockData.getMockUgc();
        recycleAdapter.setDataList(mockMediaList);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                playVideos(mockMediaList.get(currentPosition), currentPosition);
            }
        }, 300);
    }


    /**
     * 根据当前位置播放视频
     *
     * @param feedMedia
     * @param position
     */
    private void playVideos(FeedMedia feedMedia, int position) {
        if (feedMedia == null || position >= mockMediaList.size()) {
            playerEngine.stop();
            return;
        }
        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            /**
             * 其中TaskInfo表示本次播放的任务
             *
             * videoID：视频的id，要保证和视频对应
             * title：可选参数，若传入此参数，将会在 controller 的ui上显示，详见 {@link SimpleWithControllerFragment}
             * url:视频地址
             * coverID：该视频的封面 的view，在视频播放时，会将该view隐藏，可选参数
             * playerStyle 这是播放器 适配模式 PlayerStyle.STYLE_MATCH 匹配封面图尺寸 STYLE_16_9 宽：高 = 16；9 以此类推STYLE_9_16 ,STYLE_4_3
             */
            TaskInfo taskInfo = new TaskInfo.Builder()
                    .videoID(feedMedia.videoId)
                    .title(feedMedia.title)
                    .url(feedMedia.url)
                    .coverID(R.id.little_video_cover)
                    .playerStyle(PlayerStyle.STYLE_MATCH)
                    .build();
            // holder.itemView 作为锚点view传入，当列表滑动过程中，当前视频会跟随锚点view进行滑动
            // 需要注意的是，播放时传入的view是每个item的根view或其子view
            playerEngine.play(taskInfo, (ViewGroup) holder.itemView);
        } else {
            System.out.println(" >>>>>>>>>>>>>  播放错误  <<<<<<<<<<<");
        }
    }

    /**
     * 当滑动位置发生变化时，播放对应位置的视频
     *
     * @param position
     */
    private void updatePosition(int position) {
        FeedMedia feedMedia = mockMediaList.get(position);
        playVideos(feedMedia, position);
        //YLCoroutineScope是SDK内部的一个线程切换工具，可以替换为自己的子线程工具
        if (job != null) {
            job.cancel();
        }
        job = YLCoroutineScope.instance.executeDelay(Dispatcher.IO, new Runnable() {
            @Override
            public void run() {
                cacheVideo(position + 1);
                cacheVideo(position + 2);
            }
        }, 800);
    }

    /**
     * 视频预缓存，通过 YLVideoService.preLoadVideo()
     * 来进行视频的预缓存功能，以便切换到下个视频时能快速起播
     *
     * @param position
     */
    private void cacheVideo(int position) {
        if (position >= 0 && position < mockMediaList.size()
                && !TextUtils.isEmpty((mockMediaList.get(position).videoId))) {
            YLVideoService.preLoadVideo(mockMediaList.get(position).videoId, mockMediaList.get(position).url);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    /**
     * 暂停播放
     */
    private void pauseVideo() {
        if (playerEngine != null) {
            playerEngine.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideo();
    }

    /**
     * 恢复播放
     */
    private void resumeVideo() {
        if (playerEngine != null) {
            playerEngine.resume();
        }
    }

    /**
     * 释放播放器资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (playerEngine != null) {
            playerEngine.release();
        }
    }
}