package com.yilan.sdk.sdkdemo.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.engine.IYLPlayer;
import com.yilan.sdk.player.ylplayer.engine.YLPlayerFactory;
import com.yilan.sdk.player.ylplayer.ui.PGCPlayerUI;
import com.yilan.sdk.player.ylplayer.ui.TouchPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

public class SimpleWithControllerFragment extends Fragment {

    IYLPlayer playerEngine;
    FrameLayout anchorView;

    FrameLayout anchorView2;

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

        /**
         *  需要播放视频的view，在播放时 通过play() 方法传给播放器，视频画面即会附着在此view上
         */
        anchorView = view.findViewById(R.id.player_container);

        anchorView2 = view.findViewById(R.id.player_container2);
        /**
         * 创建播放器
         * YLPlayerFactory.createEngine(context)
         */
        playerEngine = YLPlayerFactory.createEngine(view.getContext())
                /**
                 * 给播放器设置控制器，此处使用了两种控制器组合
                 */
                .withController(new PGCPlayerUI().itemUI(new TouchPlayerUI()));
        /**
         *
         * 其中TaskInfo表示本次播放的任务，传入参数
         * videoID：视频的id，要保证和视频对应
         * title：可选参数，若传入此参数，将会在 controller 的ui上显示，详见 {@link SimpleWithControllerFragment}
         * url:视频地址
         * coverID：该视频的封面 的view，在视频播放时，会将该view隐藏，可选参数
         */
        TaskInfo info = new TaskInfo.Builder().videoID("1111111111").coverID(R.id.img_cover).url(MockData.getPlayerUrl()).title("视频1").build();

        /**
         * 调用play播放视频
         * anchorView ：需要播放视频的viewgroup，视频画面会附着在此view 上，注：如果设置了coverID，则anchorView应该时封面view 的父布局
         */
        playerEngine.play(info, anchorView);
        final TaskInfo info2 = new TaskInfo.Builder().videoID("222222222").coverID(R.id.img_cover2).title("视频2").url(MockData.getPlayerUrl(1)).build();
        view.findViewById(R.id.img_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerEngine.play(info, anchorView);
            }
        });
        view.findViewById(R.id.img_cover2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerEngine.play(info2, anchorView2);
            }
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
        /**
         * 页面关闭时，需要销毁播放器
         */
        if (playerEngine != null) {
            playerEngine.release();
        }
    }
}