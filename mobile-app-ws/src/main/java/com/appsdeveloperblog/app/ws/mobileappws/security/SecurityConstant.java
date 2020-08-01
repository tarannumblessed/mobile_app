package com.appsdeveloperblog.app.ws.mobileappws.security;

import com.appsdeveloperblog.app.ws.mobileappws.SpringApplicationContext;

public class SecurityConstant {
	public static final long EXPIRATION_TIME=864000000 ;
	public static final String TOKEN_PREFIX="Bearer";
	public static final String HEADER_STRING="Authorization";
	public static final String SIGNUP_URL="/users";
	
	public static String  getTokenSecrate()
	{
		AppProperties appProperties=(AppProperties)SpringApplicationContext.getBean("appProperties");
		return appProperties.getTokenSecret();
	}

}
