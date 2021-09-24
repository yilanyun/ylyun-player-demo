package com.yilan.sdk.sdkdemo.simple;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yilan.sdk.sdkdemo.NewPageActivity;
import com.yilan.sdk.sdkdemo.R;

public class SimplePageActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SimplePageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
    }

    public void simplePlayer(View view) {
        NewPageActivity.start(this, NewPageActivity.SIMPLE);
    }

    public void simpleDetail(View view) {
        NewPageActivity.start(this,NewPageActivity.SIMPLE_WITH_CONTROL);
    }

    public void simplePlayer2(View view) {
        NewPageActivity.start(this,NewPageActivity.SIMPLE_VIEW);
    }

}