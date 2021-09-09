package com.yilan.sdk.sdkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.yilan.sdk.sdkdemo.feed.FeedFragment;
import com.yilan.sdk.sdkdemo.simple.SimpleFragment;
import com.yilan.sdk.sdkdemo.simple.SimpleWithControllerFragment;
import com.yilan.sdk.sdkdemo.ugcfeed.UgcFeedFragment;

public class NewPageActivity extends AppCompatActivity {
    public static int SIMPLE = 1;
    public static int SIMPLE_WITH_CONTROL = 2;
    public static int FEED = 3;
    public static int UGC_FEED = 4;
    int type = -1;

    public static void start(Context context,int type) {
        Intent intent = new Intent(context,NewPageActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type",-1);
        }
        Fragment fragment = getFragmentByType(type);
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commitAllowingStateLoss();
        }
    }

    private Fragment getFragmentByType(int type) {
        Fragment fragment = null;
        if (type == NewPageActivity.SIMPLE) {
            fragment = SimpleFragment.newInstance();
        } else if (type == NewPageActivity.SIMPLE_WITH_CONTROL) {
            fragment = SimpleWithControllerFragment.newInstance();
        } else if (type == NewPageActivity.FEED) {
            fragment = FeedFragment.newInstance();
        } else if (type == NewPageActivity.UGC_FEED) {
            fragment = UgcFeedFragment.newInstance();
        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}