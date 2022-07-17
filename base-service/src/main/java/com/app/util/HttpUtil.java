package com.app.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtil {

	private static final String HEADER_TOTAL_COUNT = "X-Total-Count";

	public static void setHeaderTotalCountResponse(String value) {
		setHeaderResponse(HEADER_TOTAL_COUNT, value);
	}

	public static void setHeaderResponse(String name, String value) {
		HttpServletResponse currentResponse = getCurrentHttpResponse();
		if (currentResponse != null) {
			currentResponse.setHeader(name, value);
		}
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request;
		}
		return null;
	}

	public static HttpServletResponse getCurrentHttpResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
			HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
			return response;
		}
		return null;
	}
}
