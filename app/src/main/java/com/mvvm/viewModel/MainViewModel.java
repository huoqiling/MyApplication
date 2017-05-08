package com.mvvm.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mvvm.model.entitys.Person;

/**
 * Created by zyfx_ on 2017/5/6.
 */
public class MainViewModel extends BaseObservable{

    private Person person;

    public MainViewModel(Person person) {
        this.person = person;
    }

    @Bindable
    public String getName() {
        return person.getName();
    }

    @Bindable
    public String getAge() {
        return person.getAge();
    }

    @Bindable
    public String getSex() {
        return person.getSex();
    }

    @Bindable
    public String getAddress() {
        return person.getAddress();
    }

}
