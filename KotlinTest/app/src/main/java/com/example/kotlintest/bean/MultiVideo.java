package com.example.kotlintest.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Time:2020/05/09 17:15
 * Author: LiangWH
 * Description:
 */
public class MultiVideo implements MultiItemEntity {
    public static final int VIDEO = 1;
    public static final int TEXT = 2;
    public static final int BANNER = 3;

    public String feed;
    public int itemType;
    public String text;
    public String image;
    public String title;
    public String category;
    public int duration;

    public MultiVideo(int itemType) {
        this.itemType = itemType;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
}
