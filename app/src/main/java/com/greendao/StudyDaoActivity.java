package com.greendao;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.databinding.ActivityMyDaoBinding;
import com.example.zyfx_.myapplication.databinding.ActivityStudyDaoBinding;
import com.greendao.entity.User;
import com.greendao.util.greenDaoUtil;

import java.util.List;

/**
 * Created by zyfx_ on 2017/5/6.
 */
public class StudyDaoActivity extends FragmentActivity implements View.OnClickListener {

    ActivityStudyDaoBinding studyDaoBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studyDaoBinding = DataBindingUtil.setContentView(this, R.layout.activity_study_dao);
        studyDaoBinding.btnAdd.setOnClickListener(this);
        studyDaoBinding.btnDelete.setOnClickListener(this);
        studyDaoBinding.btnUpdate.setOnClickListener(this);
        studyDaoBinding.btnQuery.setOnClickListener(this);
        setData();
    }

    private void setData() {
        List<User> userList = greenDaoUtil.queryUserList();
        StudyDaoViewModel viewModel = new StudyDaoViewModel(userList);
        studyDaoBinding.setStudy(viewModel);
    }

    @Override
    public void onClick(View view) {
        String name = studyDaoBinding.etName.getText().toString();
        String age = studyDaoBinding.etAge.getText().toString();
        String skill = studyDaoBinding.etSkill.getText().toString();
        Long userId = Long.parseLong(studyDaoBinding.etId.getText().toString());
        switch (view.getId()) {
            case R.id.btnAdd:
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(skill)) {
                    User user = new User();
                    user.setName(name);
                    user.setAge(Integer.parseInt(age));
                    user.setSkill(skill);
                    greenDaoUtil.saveUser(user);
                    setData();
                }
                break;
            case R.id.btnDelete:
                if (userId > 0) {
                    greenDaoUtil.deleteUser(userId);
                    setData();
                }
                break;
            case R.id.btnUpdate:
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(skill)) {
                    User user = new User();
                    user.setId(userId);
                    user.setName(name);
                    user.setAge(Integer.parseInt(age));
                    user.setSkill(skill);
                    greenDaoUtil.updateUser(user);
                    setData();
                }
                break;
            case R.id.btnQuery:
                setData();
                break;
        }
    }
}
