package com.base.fruitbase.protocal.netutil;

import com.base.fruitbase.util.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Md5Utils {

    /**
     * 额外添加字符串
     */
    private static final String attcahCh = "aasdfd3r234&^$234sd%^&*fsr354rfd3454";

    /**
     * 对字符串加密
     * @param pasword
     * @return
     */
    public static String encode(String pasword) {

        try {
//            pasword = pasword + attcahCh;
            MessageDigest diget = MessageDigest.getInstance("md5"); // 消息摘要
            StringBuffer sb = new StringBuffer();

            // 输入的是密码明文字节数组 ,返回的就是加密以后的字节数组
            byte[] bytes = diget.digest(pasword.getBytes());

            for (byte b : bytes) {
                // 将字节转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制的字符,如果16进制是1位，前面补0
                String hex = Integer.toHexString(n);

                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }

    public static String createSign(Map<String, String> params, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        sb.append("{");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append('"' + k + '"' + ":" + '"' + v + '"' + ",");
            }
        }
        sb.append('"' + "key" + '"' + ":" + "\"" + key + "\"");
        sb.append("}");
        String sign = sign(sb.toString()).toUpperCase();
//        PrintUtil.printRespones("签名参数", sb + ">>>>>>>" + sign);
        return sign;
    }


    public static String sign(String s) {
        byte[] btInput = s.getBytes();
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    /**
     * 获得文件的MD5值
     *
     * @param filePath
     * @return 如果文件不存在，返回 null
     */
    public static String getFileMd5(String filePath) {
        InputStream fin = null;
        try {
            fin = new FileInputStream(filePath);

            MessageDigest diget = MessageDigest.getInstance("md5"); // 消息摘要

            StringBuffer sb = new StringBuffer();

            int len = -1;
            byte[] buffer = new byte[512];

            while ((len = fin.read(buffer)) != -1) {
                diget.update(buffer, 0, len); // 将读到的内容写入加密器
            }

            byte[] bytes = diget.digest(); // 对读到的内容进行加密运算

            for (byte b : bytes) {
                // 将字节转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制的字符,如果16进制是1位，前面补0

                String hex = Integer.toHexString(n);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();

        } catch (Exception e) {

        } finally {
            IOUtils.close(fin);
        }

        return null;
    }


}
