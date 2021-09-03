package com.yilan.sdk.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
                NewPageActivity.start(MainActivity.this,NewPageActivity.FEED);
            }
        });
        findViewById(R.id.player_little).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.player_custom_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.player_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}