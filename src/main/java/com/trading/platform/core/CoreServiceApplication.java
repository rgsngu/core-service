package com.trading.platform.core;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.security.MessageDigest;


@SpringBootApplication
@EnableScheduling
public class CoreServiceApplication {

	public static String sha256(String s) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(s.getBytes("UTF-8"));
		StringBuilder hex = new StringBuilder();
		for (byte b : hash) hex.append(String.format("%02x", b));
		return hex.toString();
	}

	public static void main(String[] args) throws Exception, KiteException {
//		String apiKey = "9wr48ugsqzmmxtmm";
//		String apiSecret = "fozpdja8kcn89yl446gsp2uby5qnb09e";
//
//		KiteConnect kite = new KiteConnect(apiKey);
//		kite.setUserId("BI6169");
//
//		System.out.println("Open this URL and login:");
//		System.out.println(kite.getLoginURL());

		// After login, paste request_token from browser URL
//		String requestToken = "8MRzx3jeqAweTwcRPecsg8Dv7e7cNCAU";
//		String checksum = sha256(apiKey + requestToken + apiSecret);
//		User user = kite.generateSession(requestToken, apiSecret);
//
//		System.out.println("ACCESS TOKEN = " + user.accessToken);
		SpringApplication.run(CoreServiceApplication.class, args);
	}

}
