package com.santiagolizardo.jerba.utilities;

import javax.servlet.http.HttpServletRequest;

public class RequestParam {

	private HttpServletRequest req;

	public RequestParam(HttpServletRequest req) {
		this.req = req;
	}

	public String getString(String name) {
		return req.getParameter(name);
	}

	public Long getLong(String name) {
		return getLong(name, null);
	}

	public Long getLong(String name, Long defaultValue) {
		Long longValue = defaultValue;

		String value = req.getParameter(name);
		if (null != value) {
			try {
				longValue = Long.valueOf(value);
			} catch (Exception e) {
			}
		}

		return longValue;
	}

	public Boolean getBoolean(String name) {
		return getBoolean(name, null);
	}

	public Boolean getBoolean(String name, Boolean defaultValue) {
		Boolean booleanValue = defaultValue;

		String value = req.getParameter(name);
		if (null != value) {
			try {
				booleanValue = Boolean.valueOf(value);
			} catch (Exception e) {
			}
		}

		return booleanValue;
	}

	public Integer getInteger(String name) {
		return getInteger(name, null);
	}

	public Integer getInteger(String name, Integer defaultValue) {
		Integer integerValue = defaultValue;

		String value = req.getParameter(name);
		if (null != value) {
			try {
				integerValue = Integer.valueOf(value);
			} catch (Exception e) {
			}
		}

		return integerValue;
	}
}
