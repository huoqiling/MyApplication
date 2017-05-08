package com.example.zyfx_.myapplication.net;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.example.zyfx_.myapplication.R;
import com.google.gson.JsonParseException;

import java.util.concurrent.TimeoutException;

import okhttp3.Response;

/**
 * @Author zhangxin
 * @date 2017/3/21 11:10
 * @description 错误说明
 **/
public class ErrorHelper {


    /**
     * Returns appropriate message which is to be displayed to the user against
     * the specified error object.
     *
     * @param error
     * @param context
     * @return
     */
    public static String getErrorMessage(Context context, Exception error, Response response) {
        if (error instanceof TimeoutException) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (error instanceof NetworkErrorException) {
            Log.d("zhangxin","NetworkErrorException");
            return context.getResources().getString(R.string.no_internet);
        } else if (error instanceof JsonParseException) {
            Log.d("zhangxin","JsonParseException");
            return context.getResources().getString(R.string.json_parse_error);
        }
        return handleServerError(context, response);
    }


    /**
     * Handles the server error, tries to determine whether to show a stock
     * message or to show a message retrieved from the server.
     *
     * @param context
     * @return
     */
    private static String handleServerError(Context context, Response response) {


        if (response != null) {
            switch (response.code()) {
                case 404:
                case 422:
                case 401:
                    return context.getResources().getString(R.string.generic_server_down);

                default:
                    return context.getResources().getString(R.string.generic_server_down);
            }
        }
        return context.getResources().getString(R.string.generic_error);
    }
}
