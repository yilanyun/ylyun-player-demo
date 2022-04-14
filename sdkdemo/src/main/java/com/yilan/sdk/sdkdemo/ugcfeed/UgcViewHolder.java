package com.yilan.sdk.sdkdemo.ugcfeed;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yilan.sdk.common.ui.recycle.BaseViewHolder;
import com.yilan.sdk.sdkdemo.R;
import com.yilan.sdk.sdkdemo.feed.FeedMedia;

import java.util.List;

public class UgcViewHolder extends BaseViewHolder<FeedMedia> {

    TextView title;
    TextView cpName;
    ImageView cover;

    public UgcViewHolder(Context context, ViewGroup parent) {
        super(context, parent,R.layout.ugc_item);
    }

    @Override
    protected void initView() {
        title = itemView.findViewById(R.id.title);
        cpName = itemView.findViewById(R.id.cpname);
        cover = itemView.findViewById(R.id.little_video_cover);
    }

    @Override
    public void onBindViewHolder(FeedMedia feedMedia) {
        title.setText(feedMedia.title);
        cpName.setText(feedMedia.name);
        Glide.with(itemView).load(feedMedia.cover).into(cover);
    }
}
