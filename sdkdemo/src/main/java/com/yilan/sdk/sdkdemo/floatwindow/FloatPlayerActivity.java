package com.yilan.sdk.sdkdemo.floatwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.yilan.sdk.common.util.ToastUtil;
import com.yilan.sdk.player.PlayerEngineView;
import com.yilan.sdk.player.ylplayer.TaskInfo;
import com.yilan.sdk.player.ylplayer.ui.HybridPlayerUI;
import com.yilan.sdk.sdkdemo.MockData;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.Utils;

public class FloatPlayerActivity extends AppCompatActivity {

    PlayerEngineView player;
    FrameLayout playerContainer;
    String videoId = "floatplayeractivity";
    TaskInfo taskInfo;
    private FloatManager floatManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, FloatPlayerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_player);
        playerContainer = findViewById(R.id.player_container);
        floatManager = FloatManager.getInstance();
        player = floatManager.getPlayerView();
        player.withController(new HybridPlayerUI());
        if (floatManager.isStartFloatWindow()) {
            floatManager.stopFloatWindow();
        }
        Utils.removeViewFormParent(player);
        playerContainer.addView(player);
        playVideo();
    }

    public void startFloat(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                //启动Activity让用户授权
                setFloatWindowDialog();
            } else {
                floatManager.startFloatWindow(createTaskInfo());
                finish();
            }
        } else {
            floatManager.startFloatWindow(createTaskInfo());
            finish();
        }
    }

    //提醒开启悬浮窗的弹框
    private void setFloatWindowDialog() {
        new AlertDialog.Builder(this)
                .setTitle("启用悬浮窗")//这里是表头的内容
                .setMessage("为了模拟定位的稳定性，建议开启\"显示悬浮窗\"选项")//这里是中间显示的具体信息
                .setPositiveButton("设置",//这个string是设置左边按钮的文字
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    ToastUtil.show(FloatPlayerActivity.this,"无法跳转到设置界面，请在权限管理中开启该应用的悬浮窗");
                                    e.printStackTrace();
                                }
                            }
                        })//setPositiveButton里面的onClick执行的是左边按钮
                .setNegativeButton("取消",//这个string是设置右边按钮的文字
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })//setNegativeButton里面的onClick执行的是右边的按钮的操作
                .show();
    }


    private void playVideo() {
        taskInfo = createTaskInfo();
        player.play(taskInfo);
    }

    private TaskInfo createTaskInfo() {
        if (taskInfo == null) {
            taskInfo = new TaskInfo.Builder()
                    .videoID(videoId)
                    .title("测试视频")
                    .url(MockData.getPlayerUrl())
                    .build();
        }
        return taskInfo;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (floatManager != null) {
            floatManager.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (floatManager != null) {
            floatManager.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatManager != null) {
            floatManager.release(false);
        }
    }

}