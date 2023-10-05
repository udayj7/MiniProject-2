package com.uday.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

	public static String generateRandomPwd() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(6, characters);
	}

}
