package com.androidannotations.entitys;

import com.example.zyfx_.myapplication.bean.BaseEntity;

import java.util.List;

/**
 * @Author zhangxin
 * @date 2017/5/19 10:44
 * @description 询价记录信息
 **/
public class InquiryRecordInfo extends BaseEntity {


    public Object mapData;
    public List<DataBean> data;

    public static class DataBean {

        public String id;
        public String askWorldMapId;
        public String askUserAddress;
        public String replyUseraddress;
        public String askPrice;
        public int status;
        public Object replyReason;
        public long createAt;
        public long updateAt;
        public Object tradeAt;
        public int tradeStatus;
        public Object askUserName;
        public Object replyUserName;
        public String worldMapAddress;
        public Object statusArray;
        public Object tradeStatusArray;
        public Object orderById;
        public Object startCreatAt;
        public Object endCreateAt;
        public Object noIncluedeAskId;
    }
}
