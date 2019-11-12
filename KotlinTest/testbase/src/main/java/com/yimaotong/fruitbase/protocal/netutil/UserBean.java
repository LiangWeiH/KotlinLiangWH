package com.yimaotong.fruitbase.protocal.netutil;

import com.yimaotong.fruitbase.bean.BaseBean;

/**
 * @time 2017/9/22 13:52
 */
public class UserBean extends BaseBean {
    public UserInfo userInfo;
    public String token;
    public String rightNum;

    /**
     * "rightNum":139
     * "userInfo":{
     * "id":5,
     * "codePre":"2017",
     * "codeSuf":"703",
     * "version":0,
     * "petName":"185****4737",
     * "sex":null,
     * "act_login_phone":"18513254737",
     * "email":null,
     * "creationDate":null,
     * "lastLoginTime":null,
     * "state":"01",
     * "loginId":5,
     * "headImage":null,
     * "operatorId":null,
     * "userName":null,
     * "balance":0.00,
     * "points":0
     * realState:"02" 已实名
     * },
     * "token":"B68489306A10369A49BB4010A41FD93A"
     *
     */
    public class UserInfo {
        public String id;
        public String codePre;
        public String codeSuf;
        public String version;
        public String petName;
        public String sex;
        public String phone;
        public String email;
        public String creationDate;
        public String lastLoginTime;
        public String state;
        public String loginId;
        public String headImage;
        public String operatorId;
        public String userName;
        public double balance;
        public String points;
        public String realState;
        public String inviteCode;
        public String ticket;
        public String repeatNum;
    }

}

