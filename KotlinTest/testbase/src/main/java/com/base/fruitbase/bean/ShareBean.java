package com.base.fruitbase.bean;

/**
 * @author LiangWH  Time：2018/7/20 on 10:15
 */

public class ShareBean {

    public ResultBean result;
    public String subTitle;
    public String title;
    public String url;

    public static class ResultBean {
        public String state;
        public String code;
        public Object url;
        public Object obj;
        public Object param;
        public Object value;
        public Object isRollBack;
        public String isAgainLogin;
        public String message;
    }
}
