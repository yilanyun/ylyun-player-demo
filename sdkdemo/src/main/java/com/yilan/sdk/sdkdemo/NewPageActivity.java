package com.yilan.sdk.sdkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.yilan.sdk.sdkdemo.feed.AutoPlayFeedFragment;
import com.yilan.sdk.sdkdemo.feed.FeedFragment;
import com.yilan.sdk.sdkdemo.simple.SimpleFragment;
import com.yilan.sdk.sdkdemo.simple.SimpleViewFragment;
import com.yilan.sdk.sdkdemo.simple.SimpleWithControllerFragment;
import com.yilan.sdk.sdkdemo.ugcfeed.UgcFeedFragment;

public class NewPageActivity extends FragmentActivity {
    public static int SIMPLE = 1;
    public static int SIMPLE_WITH_CONTROL = 2;
    public static int FEED = 3;
    public static int FEED_AUTO_PLAY = 4;
    public static int UGC_FEED = 5;
    public static int SIMPLE_VIEW = 6;

    int type = -1;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, NewPageActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_page);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", -1);
        }
        fragment = getFragmentByType(type);
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
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
        } else if (type == NewPageActivity.FEED_AUTO_PLAY) {
            fragment = AutoPlayFeedFragment.newInstance();
        } else if (type == SIMPLE_VIEW) {
            fragment = new SimpleViewFragment();
        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}