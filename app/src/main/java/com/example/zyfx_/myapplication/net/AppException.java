package com.example.zyfx_.myapplication.net;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 *
 *@author: star
 *@time: 2016/7/11 11:10
 *@description: 自定义网络错误
 *
 */
public class AppException extends Exception implements UncaughtExceptionHandler {


	private static final long serialVersionUID = 1L;
	/** 定义异常类型 */
	/**
	 * 连接服务器失败
	 */
	public final static byte TYPE_HTTP_ERROR = 0x01;
	/**
	 * 解析错误
	 */
	public final static byte TYPE_PARSE_ERROR = 0x02;
	/**
	 * 服务器返回的错误， 如用户认证错误
	 */
	public final static byte TYPE_SERVER_ERROR = 0x03;
	/**
	 * IO错误
	 */
	public final static byte TYPE_IO_ERROR = 0x04;
	/**
	 * 运行错误，无法归类的错误
	 */
	public final static byte TYPE_RUN = 0x70;

	private byte type;
	private int code;

	/**
	 * 系统默认的UncaughtException处理类
	 */
	private UncaughtExceptionHandler mDefaultHandler;

	private AppException() {
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	private AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
	}

	private AppException(byte type, int code, String msg) {
		super(msg);
		this.type = type;
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public int getType() {
		return this.type;
	}

	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_ERROR, code, "Network error");
	}

	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, -1, e);
	}

	public static AppException json(Exception e) {
		return new AppException(TYPE_PARSE_ERROR, -1, e);
	}

	public static AppException server(int code, String msg) {
		return new AppException(TYPE_SERVER_ERROR, code, msg);
	}

	public static AppException io(Exception e) {
		return new AppException(TYPE_IO_ERROR, -1, e);
	}

	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, -1, e);
	}

	/**
	 * 获取APP异常崩溃处理对象
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);

		}

	}

	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 *
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		return true;
	}
}
