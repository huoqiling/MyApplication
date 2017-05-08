package com.example.zyfx_.myapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseRecyclerAdapter;
import com.example.zyfx_.myapplication.base.CommonHolder;
import com.example.zyfx_.myapplication.bean.FoodBean;

import butterknife.Bind;

/**
 * @Author zhangxin
 * @date 2017/3/17 14:37
 * @description 食物
 **/
public class FoodAdapter extends BaseRecyclerAdapter {

    @Override
    public CommonHolder setViewHolder(ViewGroup parent) {
        return new FoodHolder(parent.getContext(), parent);
    }

    class FoodHolder extends CommonHolder<FoodBean> {

        @Bind(R.id.foodImage)
        ImageView foodImage;

        @Bind(R.id.tvFoodName)
        TextView tvFoodName;

        public FoodHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_food);
        }


        @Override
        public void bindData(FoodBean foodBean) {
            foodImage.setImageResource(foodBean.getFoodImageResId());
            tvFoodName.setText(foodBean.getFoodName());
        }
    }

}
