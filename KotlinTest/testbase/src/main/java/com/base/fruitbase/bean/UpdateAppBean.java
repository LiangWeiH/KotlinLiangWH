package com.base.fruitbase.bean;
import java.io.Serializable;

/**
 * 版本信息
 */
public class UpdateAppBean implements Serializable {

    public VersionInfo versionInfo;

    public static class VersionInfo implements Serializable{
        //是否有新版本
        public String isUpdate;
        //新版本name
        public String appVersionName;
        //新版本号
        public int appVersionCode;
        //新app下载地址
        public String apkUrl;
        public String targetSize;
        //更新日志
        public String appDescribe;
        //是否强制更新
        public String isPush;

        @Override
        public String toString() {
            return "VersionInfo{" +
                    "isUpdate='" + isUpdate + '\'' +
                    ", appVersionName='" + appVersionName + '\'' +
                    ", appVersionCode=" + appVersionCode +
                    ", apkUrl='" + apkUrl + '\'' +
                    ", targetSize='" + targetSize + '\'' +
                    ", appDescribe='" + appDescribe + '\'' +
                    ", isPush='" + isPush + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpdateAppBean{" +
                "versionInfo=" + versionInfo +
                '}';
    }
}
