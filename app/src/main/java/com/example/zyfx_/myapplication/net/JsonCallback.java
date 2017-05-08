package com.example.zyfx_.myapplication.net;


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.util.Convert;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zyfx_ on 2017/3/20.
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if(!checkNet(CustomApplication.getInstance())){
            try {
                throw new NetworkErrorException("");
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }



    }

    /**
     * 判断Android客户端网络是否连接
     * 只能判断是否有可用的连接，而不能判断是否能连网
     * @param context
     * @return true/false
     */
    public boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {

    }

    @Override
    public T convertSuccess(Response response) {
        T data = null;
        try {
            //com.lzy.demo.callback.DialogCallback<com.lzy.demo.model.Login> 得到类的泛型，包括了泛型参数
            Type genType = getClass().getGenericSuperclass();
            //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
            //com.lzy.demo.model.Login
            Type type = params[0];

            //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
            //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
            JsonReader jsonReader = new JsonReader(response.body().charStream());
            //有数据类型，表示有data
            data = Convert.fromJson(jsonReader, type);
            response.close();
        } catch (JsonParseException e) {
            throw new JsonParseException("");
        }
        return data;
    }


    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        Log.d("zhangx", "e.getMessage" + e.getMessage());
        try {
            Context context = CustomApplication.getInstance();
            Toast.makeText(context, ErrorHelper.getErrorMessage(context, e, response), Toast.LENGTH_LONG).show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


}
