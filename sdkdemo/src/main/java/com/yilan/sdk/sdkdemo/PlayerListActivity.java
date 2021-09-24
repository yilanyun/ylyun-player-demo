package com.yilan.sdk.sdkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.ui.inter.OnItemClickListener;
import com.yilan.sdk.common.ui.recycle.BaseViewHolder;
import com.yilan.sdk.common.ui.recycle.IViewHolderCreator;
import com.yilan.sdk.common.ui.recycle.ViewAttachedToWindowListener;
import com.yilan.sdk.common.ui.recycle.YLRecycleAdapter;
import com.yilan.sdk.player.ylplayer.PlayerState;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLCloudPlayerEngine;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.ui.HybridPlayerUI;
import com.yilan.sdk.sdkdemo.feed.FeedItemDecoration;
import com.yilan.sdk.sdkdemo.feed.FeedMedia;
import com.yilan.sdk.sdkdemo.feed.FeedViewHolder;

import java.util.List;

/**
 * 此页面演示，在列表页面播放时，如果做到跳转详情页无缝衔接
 */
public class PlayerListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    LinearLayoutManager manager;
    IYLPlayerEngine playerEngine;
    private int position;
    private ViewGroup container;
    List<FeedMedia> mockFeed;
    //当前播放的media
    FeedMedia currentMedia;

    public static void start(Context context) {
        Intent intent = new Intent(context, PlayerListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_jump1);
        recyclerView = findViewById(R.id.recycle_view);
        /**
         * 创建播放器，通过YLPlayerFactory.createEngine()创建后，需要通过 YLPlayerFactory.makeCloudEngine 让播放器具备跨页面能力
         */
        container = findViewById(R.id.player_container);
        playerEngine = YLPlayerFactory.makeCloudEngine(YLPlayerFactory.createEngine(container)).withController(new HybridPlayerUI());
        manager = new LinearLayoutManager(this);
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
                        /**
                         * 点击视频开始播放，再次点击则跳转到下一页
                         */
                        if (playerEngine.getPlayerState().value >= PlayerState.PREPARING.value
                                && playerEngine.getPlayerState().value < PlayerState.STOP.value&&PlayerListActivity.this.position==position) {
                            onViewClick(position);
                        } else {
                            playVideo(position);
                        }
                        PlayerListActivity.this.position = position;
                    }
                }).viewAttachListener(new ViewAttachedToWindowListener() {
                    @Override
                    public void onViewAttachedToWindow(BaseViewHolder baseViewHolder) {

                    }

                    @Override
                    public void onViewDetachedFromWindow(BaseViewHolder holder) {
                        /**
                         * itemview 从界面detach时候的回调，
                         * 当前播放的视频与detach的itemview相对应的时候，停止当前视频的播放
                         */
                        if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE
                                && currentMedia == holder.getData()) {
                            playerEngine.stop();
                        }
                    }
                });
        recyclerView.setAdapter(adapter);
        mockFeed = MockData.getMockFeed();
        adapter.setDataList(mockFeed);
    }

    /**
     *
     */
    private void onViewClick(int position) {
        /**
         * 通过ActivityOptionsCompat.makeSceneTransitionAnimation 来实现 页面自然跳转
         */
        FeedViewHolder holder = (FeedViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, holder.contentContainer, "player").toBundle();
        PlayerDetailActivity.start(this, mockFeed.get(position).videoId, bundle);
    }

    private void playVideo(int position) {
        FeedViewHolder holder = (FeedViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (holder == null) return;
        currentMedia = mockFeed.get(position);
        TaskInfo taskInfo = new TaskInfo.Builder()
                .videoID(currentMedia.videoId)
                .coverID(R.id.layout_content)//此view 在播放器播放时会将其隐藏，通常为封面imageview
                .title("测试视频")
                .url(currentMedia.url)
                .build();
        playerEngine.play(taskInfo, holder.contentContainer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Transition transition = getWindow().getSharedElementExitTransition();
        if (transition != null) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    if (playerEngine != null) {
                        /**
                         * 从上个页面返回时，transition!=null 成立，当动画执行完毕后，重新调用play()或者 2.changeContainer + changeAnchorView
                         * 注意：play（） 不仅有播放的能力，还会负责检测当前container 和achorView是否正确，将播放器归位
                         */
                        playVideo(position);
                        /* 2.
                        if (position >= 0) {
                            playerEngine.changeContainer(container);// 如果时通过 createEngine(context) 创建的，则传入null即可
                            FeedViewHolder holder = (FeedViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                            if (holder != null) {
                                playerEngine.changeAnchorView(holder.contentContainer, R.id.layout_content);
                            }
                        }
                         */

                    }
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
            });
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
    public void onPause() {
        super.onPause();
        if (playerEngine != null) {
            playerEngine.pause();
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