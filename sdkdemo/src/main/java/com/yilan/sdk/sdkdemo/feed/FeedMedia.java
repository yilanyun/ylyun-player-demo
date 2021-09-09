package com.yilan.sdk.sdkdemo.feed;

import java.io.Serializable;

public class FeedMedia implements Serializable {
    public String videoId;
    public String title;
    public String name;
    public String cover;
    public String url;

    public FeedMedia(String title, String cover, String url) {
        this.title = title;
        this.cover = cover;
        this.url = url;
    }
}
