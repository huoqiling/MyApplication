package com.greendao;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.greendao.entity.User;

import java.util.List;

/**
 * Created by zyfx_ on 2017/5/6.
 */
public class StudyDaoViewModel extends BaseObservable {

    List<User> userList;

    public StudyDaoViewModel(List<User> userList) {
        this.userList = userList;
    }

    @Bindable
    public String getUserList() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("编号").append("\t\t\t\t\t").append("姓名").append("\t\t\t\t\t\t")
                .append("年龄").append("\t\t\t\t\t\t\t\t").append("技能").append("\n\n");
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                buffer.append("\t").append(user.getId()).append("\t\t\t\t\t\t\t");
                buffer.append(user.getName()).append("\t\t\t\t\t\t\t");
                buffer.append(user.getAge()).append("\t\t\t\t\t\t");
                buffer.append(user.getSkill()).append("\n");
            }
        }
        return buffer.toString();
    }

    @BindingAdapter("study")
    public static void showData(TextView tvUser, String userList) {
        tvUser.setText(userList);
    }
}
