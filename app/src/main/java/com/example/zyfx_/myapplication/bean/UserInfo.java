package com.example.zyfx_.myapplication.bean;

import java.io.Serializable;

/**
 * @author: zhangxin
 * @time: 2016/7/15 14:40
 * @description: 用户信息
 */
public class UserInfo extends BaseEntity implements Serializable {


    public DataBean data;
    public boolean success;

    public static class DataBean implements Serializable {

        public AccountBean account;
        public String appLoginToken;
        public UserBean user;
    }

    public static class AccountBean implements Serializable {
        public String address;
        public long createAt;
        public String id;
        public String piBalance;
        public String piBalanceEntrustFreeze;
        public String piBalanceFreeze;
        public String piBalancePackageFreeze;
        public long updateAt;
        public String usdBalance;
        public String usdBalanceEntrustFreeze;
        public String usdBalanceFreeze;
        public String usdBalancePackageFreeze;
    }

    public static class UserBean implements Serializable {
        public int loginFlag;//0表示第一次登录
        public String address;
        public String email;
        public boolean iOS;
        public String id;
        public String loginPassword;
        public String mobile;
        public int openMobileCode;
        public int openTradePassword;
        public String tradePassword;
        public String username;
        public String phoheCode;

        @Override
        public String toString() {
            return "UserBean{" +
                    "loginFlag=" + loginFlag +
                    ", address='" + address + '\'' +
                    ", email='" + email + '\'' +
                    ", iOS=" + iOS +
                    ", id=" + id +
                    ", loginPassword='" + loginPassword + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", openMobileCode=" + openMobileCode +
                    ", openTradePassword=" + openTradePassword +
                    ", tradePassword='" + tradePassword + '\'' +
                    ", username='" + username + '\'' +
                    ", phoheCode='" + phoheCode + '\'' +
                    '}';
        }
    }

}
