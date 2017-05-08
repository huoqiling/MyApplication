package com.mvvm.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.databinding.ItemMainBinding;
import com.mvvm.model.entitys.Person;
import com.mvvm.viewModel.MainViewModel;

import java.util.List;

/**
 * Created by zyfx_ on 2017/5/6.
 * Error:Execution failed for task ':app:dataBindingProcessLayoutsDebug'.
 >
 *
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.BindingHolder> {

    private Context context;
    private List<Person> personList;
    private LayoutInflater mInflater;

    public MainAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding itemMainBinding = DataBindingUtil.inflate(mInflater, R.layout.item_main, parent, false);
        return new BindingHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        MainViewModel viewModel = new MainViewModel(personList.get(position));
        holder.mainBinding.setMainModel(viewModel);
}

    @Override
    public int getItemCount() {
        return personList != null && personList.size() > 0 ? personList.size() : 0;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ItemMainBinding mainBinding;

        public BindingHolder(ItemMainBinding mainBinding) {
            super(mainBinding.llContent);
            this.mainBinding = mainBinding;
        }
    }
}
