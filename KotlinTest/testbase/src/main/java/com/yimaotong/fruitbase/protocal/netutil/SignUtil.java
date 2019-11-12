package com.yimaotong.fruitbase.protocal.netutil;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SignUtil {
    private static final Random RANDOM = new Random();
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * 获取随机数
     */
    public static String getRndStr(int length) {
        StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < length; i++) {
            ch = CHARS.charAt(RANDOM.nextInt(CHARS.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 按照key的自然顺序进行排序，并返回
     */
    private static Map<String, String> getSortedMapByKey(Map<String, String> map) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        };
        Map<String, String> treeMap = new TreeMap<>(comparator);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        return treeMap;
    }

    /**
     * singLogin签名
     *
     * @param username
     * @param pwd
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String signLogin(String username, String pwd, String timestamp, String nonce) {
        Map<String, String> map = new TreeMap<>();
        map.put("userName", username);
        map.put("pwd", pwd);
        map.put("nonce", nonce);
        map.put("timestamp", timestamp);
        return Md5Utils.createSign(map, Config.COMMEN.KEY);
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeStamp(long l) {
        Timestamp timeStamp = new Timestamp(l);
        return timeStamp.toString();
    }

    /**
     * 公共签名
     * @param map
     * @return
     */
    public static String sign( Map<String, String> map) {
        return Md5Utils.createSign(map, Config.COMMEN.KEY);
    }
}
