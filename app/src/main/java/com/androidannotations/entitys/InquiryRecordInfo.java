package com.androidannotations.entitys;

import com.androidannotations.net.spring.annotation.JsonProperty;
import com.androidannotations.net.spring.annotation.JsonResponse;
import com.example.zyfx_.myapplication.bean.BaseEntity;
import com.google.gson.annotations.SerializedName;

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
        public long tradeAt;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", askWorldMapId='" + askWorldMapId + '\'' +
                    ", askUserAddress='" + askUserAddress + '\'' +
                    ", replyUseraddress='" + replyUseraddress + '\'' +
                    ", askPrice=" + askPrice +
                    ", status=" + status +
                    ", replyReason=" + replyReason +
                    ", createAt=" + createAt +
                    ", updateAt=" + updateAt +
                    ", tradeAt=" + tradeAt +
                    ", tradeStatus=" + tradeStatus +
                    ", askUserName=" + askUserName +
                    ", replyUserName=" + replyUserName +
                    ", worldMapAddress='" + worldMapAddress + '\'' +
                    ", statusArray=" + statusArray +
                    ", tradeStatusArray=" + tradeStatusArray +
                    ", orderById=" + orderById +
                    ", startCreatAt=" + startCreatAt +
                    ", endCreateAt=" + endCreateAt +
                    ", noIncluedeAskId=" + noIncluedeAskId +
                    '}';
        }
    }
}
