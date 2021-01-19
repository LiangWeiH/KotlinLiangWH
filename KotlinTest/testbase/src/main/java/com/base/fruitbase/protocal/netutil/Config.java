package com.base.fruitbase.protocal.netutil;

import com.example.fruitbase.R;
import com.base.fruitbase.util.UIUtils;

/**
 * @time 2017/9/20 14:12
 */
public class Config {

    /**
     * 通用字符串
     */
    public static class COMMEN {
        public static final String KEY1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj";
        public static final String KEY2 = "/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3";
        public static final String KEY3 = UIUtils.getkey(R.string.key3);
        public static final String KEY = KEY1 + KEY2 + KEY3;
        public static final boolean ISDEBUG = true;
        public static final String SALT = "";
    }

    /**
     * 网络请求借口
     */
    public static class API {
        //主机地址-超
//        public static final String HOST_CHAO = "http://192.168.2.200:82";
        //主机地址-小黑
        public static final String HOST_HEI = "http://app.wanguowang.com:82";//开发
        //        public static final String HOST_HEI = "http://192.168.2.222:82";
//        public static final String HOST_HEI = "https://app.guobener.com";//正式
        //        public static final String HOST_HEI = "http://192.168.2.72:80";
//        public static final String HOST_HEI = "https://check.wanguowang.com";//测试
//        public static final String HOST_HEI = "http://test.wanguowang.com";//HTTPS测试
        //主机地址-欢欢
        public static final String HOST_HUAN = "http://192.168.2.118:8080/fruit-farm-web";
        //主机地址-涂涂
        public static final String HOST_TUTU = "http://192.168.2.113:8080/powerDetail?id=";
        //主机地址-线上
        public static final String HOST_TEST = "http://192.168.2.222";
//        public static final String HOST_HEI = "http://192.168.2.222:82";

        public static final String HOST = HOST_HEI;

        public static final String BASEURL = HOST + "/app";
        public static final String BASEURL_NOAPP = HOST;

        public static final String HOMELIST = BASEURL + "/getPushUserAction";//首页数据
        public static final String VERSIONUPGRADE = BASEURL + "/edition/curVersion";//版本升级
        public static final String WXLOGIN = BASEURL + "/wechatlogin";//微信登录
        public static final String WXLOGINBINDING = BASEURL + "/loginBinding";//微信绑定
        public static final String WXregisterForWeChat = BASEURL + "/registerForWeChat";//微信注册
        public static final String LOGINURL_PHONE = BASEURL + "/loginToCode";//快捷登录
        public static final String LOGINURL_ACCOUNT = BASEURL + "/loginToPwd";//常规登录
        public static final String GETCODE = BASEURL + "/sms/send";//获取验证码
        public static final String REGISTER = BASEURL + "/register";//注册
        public static final String CHECKCODE = BASEURL + "/sms/codeCheck";//校验验证码
        public static final String FORGETPWD = BASEURL + "/updatePwd";//忘记密码
        public static final String SHOPPINGLIST = BASEURL + "/goods/index";//获取一级列表
        public static final String TREESHOPDETAILLIST = BASEURL + "/goods/getGoods";//获取二级列表
        public static final String USERCENTER = BASEURL + "/user/userCenter";//获取用户信息
        public static final String ISHASREAD = BASEURL + "/sysmsg/getUnReadCnt";   //是否有未读消息
        public static final String USERCENTERBUTTON = BASEURL + "/goods/getInterfaceConfig";//获取用户信息的五个按钮


        //        public static final String BAIDU=BASEURL + "/user/rights/getNews";
        public static final String UPDATEHEADIMG = BASEURL + "/user/headImage";//上传用户头像
        public static final String UPDATEUSERALTER_PETNAME = BASEURL + "/user/userAlter";//修改昵称
        public static final String UPDATEUSERALTER_GENDER = BASEURL + "/user/userAlter";//修改性别
        public static final String UPDATEUSERALTER_PWD = BASEURL + "/user/updateUserPwd";//修改密码

        public static final String ADDRESS = BASEURL + "/user/address/list";//获取收货地址
        public static final String ADDRESS_SAVE = BASEURL + "/user/address/save";//添加收货地址
        public static final String ADDRESS_ALERT_GET = BASEURL + "/user/addressMsg";//修改获取收货信息
        public static final String ADDRESS_ALERT = BASEURL + "/user/address/update";//修改收货信息
        public static final String ADDRESS_ALERT_COMMEN = BASEURL + "/user/address/updateStatus";//修改默认地址状态
        public static final String ADDRESS_DELETE = BASEURL + "/user/address/delete";//删除收货地址

        public static final String HISTORYVERSION = BASEURL + "/edition/version";//历史版本

        public static final String GETTOKEN = BASEURL + "/user/getUserToken";//获取webViw返回的token
        public static final String MYTREE = BASEURL + "/user/rights/fruit";//我的果树---权益
        public static final String NEWMYTREE = BASEURL + "/user/rights/getRightsBatch";//我的果树---权益

        public static final String ADOPT = BASEURL + "/goods/getGoodsHall";//认养首页
        public static final String ADOPT_LIST = BASEURL + "/goods/getGoods";//认养列表
        public static final String RIGHT_CONFIG = BASEURL + "/goods/rightConfig";//权益列表
        public static final String RIGHT_LIST = BASEURL + "/user/rights/index";//首页权益饼图
        public static final String MSGLIST = BASEURL + "/sysmsg/getMsgList";//消息列表
        public static final String UPDATEMSGSTATE = BASEURL + "/sysmsg/updateState";//更新消息状态
        public static final String GETREGISTERURL = BASEURL + "/user/getRegister";//获取邀请二维码链接
        public static final String WALLETMSG = BASEURL + "/user/rights/getNews";//获取钱包首页消息
        public static final String REALNAMESTATE = BASEURL + "/account/isReal";//获取实名认证状态
        public static final String HEALLIST = BASEURL + "/goodspackage/list";//套餐列表
        public static final String HEALLISTBANNER = BASEURL + "/recommend";//套餐bannner
        public static final String MYORDERLIST = BASEURL + "/order/myOrder";//我的权益变成我的订单
        public static final String ONEKEYREADING = BASEURL + "/sysmsg/updateAllState";//一键阅读
        public static final String REDPACKET = BASEURL + "/redpacket/getActivityRedPacket";//红包
        public static final String BACKMESSAGE = BASEURL + "/ticketTaskMsg";//返回的消息
        public static final String SHARESUCCESSBACK = BASEURL + "/task/saveTaskShareRecord";//分享回调的接口

    }

    /**
     * H5页面地址
     */
    public static class H5API {
        public static final String BASEURL_WAP = API.HOST + "/wap";
//        public static final String BASEURL_BASE_TUTU = "https://wap.guobener.com";//正式
        public static final String BASEURL_BASE_TUTU = "http://app.wanguowang.com:83";//开发
//        public static final String BASEURL_BASE_TUTU = "http://192.168.2.222:83";
        //        public static final String BASEURL_BASE_TUTU = "http://app.wanguowang.com:83";
//        public static final String BASEURL_BASE_TUTU = "https://check-h5.wanguowang.com";   //测试
        //public static final String BASEURL_BASE_TUTU = "http://wanguowang.com";
//        public static final String BASEURL_BASE_TUTU = "http://app.wanguowang.com:83";//https测试

//                public static final String BASEURL_BASE_TUTU = "http://wap.zhouhuiart.top:8888";//正式

        public static final String MYTREEDETAIL = BASEURL_WAP + "/goods/detail";//我的果树详情
        //public static final String MYTREEDETAIL = "http://192.168.2.113:8080/#/";//我的果树详情
        //http://192.168.2.113:8080/walletHome涂涂本地
        public static final String ABOUTAPP = BASEURL_BASE_TUTU + "/about";//关于app
        public static final String STRATEGY = BASEURL_WAP + "/view/raiders";//策略
        public static final String WALLET = BASEURL_BASE_TUTU + "/walletHome";//钱包
        public static final String ADOPT_DETAIL = BASEURL_WAP + "/goods/detail?id=";//认养详情
        public static final String POWER_DETAIL = BASEURL_BASE_TUTU + "/powerDetail?id=";//权益详情
        public static final String PROTOCOL = BASEURL_BASE_TUTU + "/protocol";//用户注册协议
        public static final String INVITAWARD = BASEURL_BASE_TUTU + "/inviteFriends";//用户注册协议
        public static final String HEAL = BASEURL_BASE_TUTU + "/mealDetails?id=";//养生坊详情
        public static final String MYINVITA = BASEURL_BASE_TUTU + "/myInvite";
        public static final String LEGALIZE = BASEURL_BASE_TUTU + "/legalize";//快捷实名
        public static final String VERIFIED = BASEURL_BASE_TUTU + "/verified";//人工审核实名
        public static final String LEGALIZE_WITHDRAW = BASEURL_BASE_TUTU + "/legalize?from=withdraw";//提现时的快捷实名
        public static final String VERIFIED_WITHDRAW = BASEURL_BASE_TUTU + "/verified?from=withdraw";//提现时的人工审核实名
        public static final String WITHDRAW = BASEURL_BASE_TUTU + "/Withdraw?balance=";//提现
        public static final String LEGALIZEHOME = BASEURL_BASE_TUTU + "/legalizeHome";//实名成功页
        public static final String BILLLIST = BASEURL_BASE_TUTU + "/billList";//账单
        public static final String OVER = BASEURL_BASE_TUTU + "/over";//明细
        public static final String MARKET = BASEURL_BASE_TUTU + "/market";//集市
        public static final String FACTORY = BASEURL_BASE_TUTU + "/factory";//加工厂
        public static final String STOREHOUSE = BASEURL_BASE_TUTU + "/warehouse";//仓库
        public static final String HealthCareWorkshop = BASEURL_BASE_TUTU + "/mealIntroduce";//养生坊页面
        public static final String ADOPTDETAILS = BASEURL_BASE_TUTU + "/mymealDetails";
        public static final String GOLD = BASEURL_BASE_TUTU + "/jfw";
        public static final String USER_INVITATION = BASEURL_BASE_TUTU + "/myInvite";//我的邀请
        public static final String GRAINTICKETDETAILS = BASEURL_BASE_TUTU + "/grainCouponDetail";//粮票详情
        public static final String BALANCEDETAILS = BASEURL_BASE_TUTU + "/surplus";//余额详情
        public static final String COUPONDETAILS = BASEURL_BASE_TUTU + "/coupon";//余额详情
        public static final String CHAT = BASEURL_BASE_TUTU + "/personalCenter";//余额详情
        public static final String PAYSETTING = BASEURL_BASE_TUTU + "/paymentSetting";//支付设置
        public static final String MATTERDELAI = BASEURL_BASE_TUTU + "/ordersDetail?id=";//权益详情
        public static final String REDPACKETDETAILS = BASEURL_BASE_TUTU + "/qhb?activityid=";//红包详情
    }

    /**
     * 正则表达式
     */
    public static class REGEX {
        //匹配手机号
        public static final String PHONENUMREGEX = "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(16[0-9])|(17[^4,\\D])|(18[0-9])|(19[0-9]))\\d{8}$";
        //匹配验证码
        public static final String CODEREGEX = "^\\d{6}$";
        // 匹配邀请人
        public static final String INVITREGEX = "[0-9a-zA-Z]{6}";
        //密码
        public static final String PWDREGEX = "[0-9a-zA-Z]{6,18}";
        //        public static final String PWDREGEX = "^.{6,18}$";
        //去除emoj表情
        public static final String EXPECTEMOJ = "[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]";
        public static final int PWDMINNUMBER = 6;
        public static final int PWDMAXNUMBER = 18;
    }

    /**
     * 缓存Key
     */
    public static class CACHEKEY {
        public static final String USERINFO = "userInfo";
        public static final String ISSHOWREDPACKAGE = "isShow";
        public static final String REGISTERMSG = "registerMsg";
        public static final String LOGINMSG = "loginMsg";
        public static final String CITYINFO = "cityInfo";
        public static final String SHAREISSHOW= "shareIsShow";
        public static final String SHARETITLE = "shareTitle";
        public static final String SHAREGOODSID = "shareGoodsId";
        public static final String SHARECODE = "shareCode";
        public static final String TOKEN = "token";
    }

    /**
     * 控制逻辑的一些字段
     */
    public static class CHECKTYPE {
        /**
         * 是否刷新用户信息
         * 登录，退出，修改个人信息置为ture
         */
        public static boolean isRefreshUserInfo = false;
        /**
         * 因为登录时，数据都是最新保存的，不需要再次从服务器取
         */
        public static boolean isLogin = false;

        public static boolean isRefreshRealName = false;
        /**
         * 是否有新的权益
         */
        public static boolean isHasNewPower = false;
        /**
         * 控制微信登录
         */
        public static String isSuccessState;
        public static String isSuccessCode;
    }

    /**
     * startForRequest的请求码
     */
    public static class REQUESTCODE {
        public static final int ADDADDRESS = 1000;//添加收货地址
        public static final int ALERTADDRESS = 2000;//修改收货地址
        public static final int INTENTADDRESS = 3000;//从h5跳转到收货地址列表
        public static final int INTENTDATA = 4000;//从收货地址列表跳转到h5
    }

    /**
     * startForRequest的响应码
     */
    public static class RESPONSECODE {
        public static final int ADDRESSSUCCESS = 1001;//添加收货成功
        public static final int ALERTADDRESSSUCCESS = 2000;//修改收货地址成功
    }

    /**
     * 第三方应用信息
     */
    public static class THIRDKEY {
        public static final String WECHATAPPID = "wxb24160ed6f6bacc2";
        public static final String WESECRET = "071d7e76bc27bec74f796d182eb7ce87";
        public static final String QQAPPID = "1106786916";
        public static final String QQSECRET = "B5RBSQhTjXpWgNXD";
        public static final String SIANAPPID = "2837461567";
        public static final String SIASECRET = "d561426076c1b355983be6a7ae5a850d";
        //        public static final String SIARECETURL = "http://dev-gy-test.cdtupsmfw.com:82";
        public static final String SIARECETURL = "http://sns.whalecloud.com";
    }

}
