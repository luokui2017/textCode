package com.lk.testlist;

import android.graphics.drawable.Drawable;

/**
 * Created by L奎 on 2018/2/28.
 */

public class MyAppInfo {
    private Drawable image;
    private String appName;
    private int versionCode;
    private long lastUpdateTime;

    public MyAppInfo() {
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public MyAppInfo(Drawable image, String appName, int versionCode, long lastUpdateTime) {

        this.image = image;
        this.appName = appName;
        this.versionCode = versionCode;
        this.lastUpdateTime = lastUpdateTime;
    }
}
