package com.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by zyfx_ on 2017/5/6.
 */
@Entity
public class User {

    @Id(autoincrement = true)
    @Unique
    private Long id;
    private String name;
    private int age;
    private String skill;

    public String getSkill() {
        return this.skill;
    }
    public void setSkill(String skill) {
        this.skill = skill;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1842539051)
    public User(Long id, String name, int age, String skill) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.skill = skill;
    }
    @Generated(hash = 586692638)
    public User() {
    }


    

  
}
