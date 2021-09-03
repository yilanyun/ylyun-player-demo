package com.yilan.sdk.sdkdemo.feed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilan.sdk.common.ui.recycle.BaseViewHolder;
import com.yilan.sdk.common.util.FSScreen;
import com.yilan.sdk.sdkdemo.R;

import java.util.List;

/**
 * Author And Date: liurongzhi on 2020/7/27.
 * Description: com.yilan.sdk.ui.little
 */
public class FeedViewHolder extends BaseViewHolder<FeedMedia> {

    private TextView mediaTitle;
    private ImageView mediaCover;
    public ViewGroup contentContainer;

    public FeedViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.feed_media_item);
    }

    @Override
    protected void initView() {
        mediaTitle = itemView.findViewById(R.id.tv_media_title);
        mediaCover = itemView.findViewById(R.id.iv_media_cover);
        contentContainer = itemView.findViewById(R.id.layout_item_container);
        ViewGroup.LayoutParams params = contentContainer.getLayoutParams();
        params.width = FSScreen.getScreenWidth();
        params.height = params.width * 9 / 16;
        contentContainer.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(FeedMedia item, final List dataList) {
        mediaTitle.setText(item.title);
        Glide.with(itemView).load(item.cover).into(mediaCover);
    }

    public View getItemView() {
        return itemView;
    }
}
