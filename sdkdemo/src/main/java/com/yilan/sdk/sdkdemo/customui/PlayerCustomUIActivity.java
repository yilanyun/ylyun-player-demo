package com.yilan.sdk.sdkdemo.customui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.player.PlayerEngineView;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.ui.HybridPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;

/**
 * 自定义播放器UI使用示例：详情请参考{@link CustomPlayerUI}
 */
public class PlayerCustomUIActivity extends AppCompatActivity {

    PlayerEngineView player;
    String videoId = "adfasdfsd";

    public static void start(Context context) {
        Intent intent = new Intent(context, PlayerCustomUIActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_custom);
        player = findViewById(R.id.player);
        //设置播放器UI
        player.withController(new CustomPlayerUI());
        playVideo();
    }


    private void playVideo() {
        TaskInfo taskInfo = new TaskInfo.Builder()
                .videoID(videoId)
                .title("测试视频")
                .url(MockData.getPlayerUrl())
                .build();
        player.play(taskInfo);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.resume();
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
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}