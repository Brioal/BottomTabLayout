package com.brioal.bottomtab.entity;

/**
 * 地步按钮实体类
 * Created by Brioal on 2016/8/19.
 */

public class TabEntity {
    private int mIcon;
    private String mText;

    public TabEntity(int icon, String text) {
        mIcon = icon;
        mText = text;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
