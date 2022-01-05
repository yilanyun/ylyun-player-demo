package com.yilan.sdk.sdkdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Trace;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

public class Utils {
    /**
     * dp转为px
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转为px
     */
    public static int sp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将View从父控件中移除
     */
    public static void removeViewFormParent(View v) {
        if (v == null) return;
        ViewParent parent = v.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(v);
        }
    }

    public static boolean checkOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);

            intent.setData(Uri.parse("package:" + context.getPackageName()));

            context.startActivity(intent);

            return false;
        } else {
            return true;
        }
    }

}
