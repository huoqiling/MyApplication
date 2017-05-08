package com.example.zyfx_.myapplication.bean;

/**
 * @Author zhangxin
 * @date 2017/3/17 12:05
 * @description
 **/
public class FoodBean {

    private int foodImageResId;
    private String foodName;

    public FoodBean() {
    }

    public FoodBean(int foodImageResId, String foodName) {
        this.foodImageResId = foodImageResId;
        this.foodName = foodName;
    }

    public int getFoodImageResId() {
        return foodImageResId;
    }

    public void setFoodImageResId(int foodImageResId) {
        this.foodImageResId = foodImageResId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
