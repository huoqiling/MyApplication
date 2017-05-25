package com.androidannotations.base;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidannotations.net.RestManager;
import com.androidannotations.utils.ToastUtil;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;

@EViewGroup
public class BaseFrameLayout extends FrameLayout {

	@Bean
	protected RestManager restManager;

	@Bean
	public ToastUtil toastUtil;


	public BaseFrameLayout(Context context) {
		this(context, null);
	}

	public BaseFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void showView(View view) {
		if (null != view) {
			view.setVisibility(View.VISIBLE);
		}
	}

	public void goneView(View view) {
		if (null != view) {
			view.setVisibility(View.GONE);
		}
	}

	public void invisibleView(View view) {
		if (null != view) {
		view.setVisibility(View.INVISIBLE);
		}
	}
	
	protected void log(String msg){
		Log.v(getClass().getName(), msg);
	}

	public void setTextViewData(TextView textView, String str) {
		try {
			if (!TextUtils.isEmpty(str)) {
				textView.setText(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTextViewData(TextView textView, String str, String unit) {
		try {
			if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(unit)) {
				textView.setText(String.format("%s%s", str, unit));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTextViewHtmlData(TextView textView, String htmlStr) {
		try {
			if (!TextUtils.isEmpty(htmlStr)) {
				textView.setText(Html.fromHtml(htmlStr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
