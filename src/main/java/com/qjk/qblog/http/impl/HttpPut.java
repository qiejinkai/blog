package com.qjk.qblog.http.impl;

import java.net.URL;

import com.qjk.qblog.http.IPut;


public class HttpPut extends HttpPost implements IPut {

	public HttpPut(URL url) throws Throwable {
		super(url);
		conn.setRequestMethod("PUT");
	}

}
