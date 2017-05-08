package com.example.zyfx_.myapplication.net;

import com.google.gson.Gson;


public class GsonTools {

	/**
	 * 用Gson方式 把object 保存为 json字符串
	 *
	 * @param object
	 * @return
	 * @throws AppException
	 */
	public static String creatJsonString(Object object) throws AppException {
		if (object == null) {
			throw AppException.json(new NullPointerException("toJson null"));
		}
		try {
			Gson gson = new Gson();
			String jsonString = gson.toJson(object);
			if (jsonString == null) {
				throw AppException.json(new NullPointerException("toJson null"));
			}
			return jsonString;
		} catch (Exception e) {
			throw AppException.json(e);
		}
	}

	/**
	 * 用Gson方式 把json字符串 保存为 object
	 *
	 * @param response
	 * @param clz
	 * @return
	 * @throws AppException
	 */
	public static <T> T getResponse(String response, Class<T> clz) throws AppException {
		if (isNull(response)) {
			throw AppException.json(new NullPointerException("response null"));
		}
		try {
			Gson gson = new Gson();
			T rsp = gson.fromJson(response, clz);
			if (rsp == null) {
				throw AppException.json(new NullPointerException("response null"));
			}
			return rsp;
		} catch (Exception e) {
			throw AppException.json(e);
		}
	}

	public static boolean isNull(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else
			return false;
	}
}
