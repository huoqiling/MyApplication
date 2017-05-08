package com.greendao.util;

import com.example.zyfx_.myapplication.CustomApplication;
import com.greendao.entity.User;
import com.greendao.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyfx_ on 2017/5/6.
 */
public class greenDaoUtil {

    /**
     * 增加
     *
     * @param user
     */
    public static void saveUser(User user) {
        UserDao userDao = CustomApplication.getInstance().getDaoSession().getUserDao();
        if (null != user) {
            userDao.insert(user);
        }
    }

    /**
     * 删除
     *
     * @param userId
     */
    public static void deleteUser(Long userId) {
        UserDao userDao = CustomApplication.getInstance().getDaoSession().getUserDao();
        if (null != userId && userId > 0) {
            userDao.deleteByKey(userId);
        }
    }

    /**
     * 更新
     *
     * @param user
     */
    public static void updateUser(User user) {
        UserDao userDao = CustomApplication.getInstance().getDaoSession().getUserDao();
        if (null != user) {
            userDao.update(user);
        }
    }

    /**
     * 查找
     *
     * @return
     */
    public static List<User> queryUserList() {
        UserDao userDao = CustomApplication.getInstance().getDaoSession().getUserDao();
        List<User> userList = userDao.loadAll();
        if (userList != null && userList.size() > 0) {
            return userList;
        } else {
            userList = new ArrayList<>();
            return userList;
        }
    }

}
