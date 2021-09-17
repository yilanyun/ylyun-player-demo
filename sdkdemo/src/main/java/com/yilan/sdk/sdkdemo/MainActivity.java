package com.yilan.sdk.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yilan.sdk.sdkdemo.con.PlayerActivity1;
import com.yilan.sdk.sdkdemo.simple.SimplePageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //播放器简单使用
        findViewById(R.id.player_simple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimplePageActivity.start(MainActivity.this);
            }
        });
        //在列表中使用播放器
        findViewById(R.id.player_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPageActivity.start(MainActivity.this, NewPageActivity.FEED);
            }
        });
        //在列表中使用播放器
        findViewById(R.id.player_feed_auto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPageActivity.start(MainActivity.this, NewPageActivity.FEED_AUTO_PLAY);
            }
        });
        findViewById(R.id.player_little).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPageActivity.start(MainActivity.this, NewPageActivity.UGC_FEED);
            }
        });
        findViewById(R.id.player_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerActivity1.start(MainActivity.this);
            }
        });
    }

}