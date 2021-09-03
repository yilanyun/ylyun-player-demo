package com.yilan.sdk.sdkdemo.feed;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yilan.sdk.common.util.FSScreen;

public class FeedItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint dividerPaint;
    private final int dividerHeight = FSScreen.dip2px(1);

    public FeedItemDecoration() {
        dividerPaint = new Paint();
        dividerPaint.setColor(Color.parseColor("#efefef"));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + FSScreen.dip2px(16);
        int right = parent.getWidth() - parent.getPaddingRight() - FSScreen.dip2px(16);
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }
}
