package com.androidannotations.net.spring;

import org.springframework.http.converter.HttpMessageNotReadableException;

public class InvalidCallbackMethodException extends HttpMessageNotReadableException {

	public InvalidCallbackMethodException(String s) {
		super(s);
	}
}
