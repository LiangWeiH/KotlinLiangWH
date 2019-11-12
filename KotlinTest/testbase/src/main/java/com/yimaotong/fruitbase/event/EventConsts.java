package com.yimaotong.fruitbase.event;

/**
 * @time 2017/9/19 15:59
 */
public class EventConsts {
    public static final String USER_ID = "userid";
    public static final String RESOLUTION = "resolution";
    public static final String MAC = "mac";
    public static final String IMEI = "imei";
    public static final String APPKEY = "appkey";

    public static final int MAX_AGE_OFFLINE = 24 * 60 * 60;//默认最大离线缓存时间（秒）
    public static final int MAX_AGE_ONLINE = 60;//默认最大在线缓存时间（秒）
}
