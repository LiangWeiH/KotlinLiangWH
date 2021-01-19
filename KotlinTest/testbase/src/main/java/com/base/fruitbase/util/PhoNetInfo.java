package com.base.fruitbase.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.base.fruitbase.event.EventConsts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @time 2017/9/19 16:00
 */
public class PhoNetInfo {

    public static final String SHARED_PREFERENCES_DATA_STATISTICS = "shared_preferences_data_statistics";
    public static final String NONE = "none";
    public static final String WIFI = "WIFI";
    public static final String G4 = "4G";
    public static final String G3 = "3G";
    public static final String G2 = "2G";
    private static final String MARKET_ID = "marketId";
    private static final boolean LOG_ON = false;
    private static final String TAG = "Utils";

    private static boolean IS_AUTO_WIFI = false;
    private static String mMarketId = NONE;

    public static String getMarketId(Context context) {
        String ret = NONE;
        if (IS_AUTO_WIFI) {
            ret = mMarketId;
        } else {
            ApplicationInfo info = null;
            try {
                info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            } catch (Exception e) {
                if (LOG_ON)
                    e.printStackTrace();
            }
            if (LOG_ON)
                Log.i(TAG, "[getMarketId] metaData:" + info.metaData);

            if (info.metaData != null) {
                ret = info.metaData.getString(MARKET_ID);
                if (TextUtils.isEmpty(ret)) {
                    ret = NONE;
                }
            }
        }
        Log.i("MarketId", "[MarketId]=" + ret);
        return ret;
    }

    public static String getAppVersion(Context context) {
        String appVersion = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                appVersion = info.versionName;
                if (TextUtils.isEmpty(appVersion)) {
                    appVersion = NONE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    public static int getRandom() {
        Random r = new Random();
        int i = r.nextInt(1000);
        return i;
    }

    public static String getUUID(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_DATA_STATISTICS, Context.MODE_PRIVATE);
        String uuid = sp.getString(EventConsts.USER_ID, null);
        if (uuid == null) {
            StringBuilder sb = new StringBuilder();
            String src = sb.append(System.currentTimeMillis()).append(getRandom()).toString();

            try {
                uuid = UUID.nameUUIDFromBytes(src.getBytes("UTF-8")).toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(uuid)) {
                uuid = NONE;
            } else {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(EventConsts.USER_ID, uuid);
                editor.commit();
            }
        }
        return uuid;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getManufacturer() {
        return Build.BRAND;
    }

    public static String getDeviceType() {
        return Build.MODEL;
    }

    public static String getResolution(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_DATA_STATISTICS, Context.MODE_PRIVATE);
        String resolution = sp.getString(EventConsts.RESOLUTION, null);
        if (resolution == null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            StringBuilder sb = new StringBuilder();
            resolution = sb.append(dm.widthPixels).append("x").append(dm.heightPixels).toString();

            if (TextUtils.isEmpty(resolution)) {
                resolution = NONE;
            } else {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(EventConsts.RESOLUTION, resolution);
                editor.commit();
            }
        }
        return resolution;
    }

    public static String getNetworkType(Context context) {
        String type = NONE;
        try {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null) {
                    type = ni.getTypeName();
                    if (TextUtils.isEmpty(type)) {
                        type = NONE;
                    }
                }
            }
        } catch (Exception e) {
            type = NONE;
        }
        return type;
    }

    public static String getApn(Context context) {
        String apn = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                apn = ni.getExtraInfo();
                if (TextUtils.isEmpty(apn)) {
                    apn = NONE;
                }
            }
        } catch (Exception e) {
            apn = NONE;
        }
        return apn;
    }

    public static String getLocalMacAddress(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_DATA_STATISTICS, Context.MODE_PRIVATE);
        String mac = sp.getString(EventConsts.MAC, null);

        if (mac == null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                mac = info.getMacAddress();
                if (TextUtils.isEmpty(mac)) {
                    mac = NONE;
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(EventConsts.MAC, mac);
                    editor.commit();
                }
            }
        }
        return mac;
    }

    public static String getLocalLanguage() {
        String l = null;
        l = Locale.getDefault().getLanguage();
        if (TextUtils.isEmpty(l)) {
            l = NONE;
        }
        return l;
    }

    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_DATA_STATISTICS, Context.MODE_PRIVATE);
        String imei = sp.getString(EventConsts.IMEI, null);

        if (imei == null) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                imei = NONE;
            } else {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(EventConsts.IMEI, imei);
                editor.commit();
            }
        }
        return imei;
    }

    public static void setRoadwifiState(boolean roadwifi) {
        IS_AUTO_WIFI = roadwifi;
    }

    public static void setRoadwifiMarketId(String marketId) {
        mMarketId = marketId;
    }

    public static String getAppKey(Context context) {
        //String ret = NONE;
        // SharedPreferences sp = context
        // .getSharedPreferences(SHARED_PREFERENCES_DATA_STATISTICS,
        // Context.MODE_PRIVATE);
        // String appKey = sp.getString(EventConsts.APPKEY, NONE);
        //
        // if (DopoolEnvironment.getSDKConf() != null) {
        // ret = DopoolEnvironment.getSDKConf().mAppToken;
        // if (!TextUtils.isEmpty(ret)) {
        // if (!ret.equals(appKey)) {
        // SharedPreferences.Editor editor = sp.edit();
        // editor.putString(EventConsts.APPKEY, ret);
        // editor.commit();
        // }
        // }
        // } else {
        // ret = appKey;
        // }
        // return ret;

        String ret = NONE;
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (Exception e) {
            if (LOG_ON)
                e.printStackTrace();
        }
        if (LOG_ON)
            Log.i(TAG, "[getAppKey] metaData:" + info.metaData);

        if (info != null && info.metaData != null) {
            if (IS_AUTO_WIFI) {
                ret = info.metaData.getString("appkey_roadwifi");
            } else {
                ret = info.metaData.getString(EventConsts.APPKEY);
            }
            if (TextUtils.isEmpty(ret)) {
                ret = NONE;
            }
        }
        return ret;
    }

    public static boolean getWiFiState(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED && info[i].getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public static String convertMapToString(Map<String, String> data) {
        if (data != null) {
            JSONObject object = new JSONObject();
            Iterator<Map.Entry<String, String>> Data = data.entrySet().iterator();
            while (Data.hasNext()) {
                Map.Entry<String, String> item = Data.next();
                if (item.getKey() != null && item.getValue() != null) {
                    try {
                        if (!object.has(item.getKey())) {
                            object.put(item.getKey(), item.getValue());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return object.toString();
        } else {
            return NONE;
        }
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }


    /**
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否手机网络
     *
     * @return
     */
    public static boolean NetWorkIsMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        int networkType = ni.getType();
        if (ConnectivityManager.TYPE_MOBILE == networkType) {
            return true;
        }
        return false;
    }

    public enum NetWorkType {
        none(NONE),
        Wifi(WIFI),
        _2G(G2),
        _3G(G3),
        _4g(G4);

        NetWorkType(String value) {
            this.value = value;
        }

        public String value;
    }


    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }


    public static int getConnectedTypeINT(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            Log.i(TAG, "NetworkInfo: " + net.toString());
            return net.getType();
        }
        return -1;
    }

    public static String getAllNetworkType(Context context) {
        int type = getConnectedTypeINT(context);
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return NetWorkType.Wifi.value;
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                int teleType = getTelephonyManager(context).getNetworkType();
                switch (teleType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetWorkType._2G.value;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetWorkType._3G.value;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetWorkType._4g.value;
                    default:
                        return NetWorkType.none.value;
                }
            default:
                return NetWorkType.none.value;
        }
    }

}
