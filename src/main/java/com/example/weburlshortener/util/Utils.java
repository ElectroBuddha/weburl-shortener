package com.example.weburlshortener.util;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

	
	public static void ThreadSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
	
	public static String getRandomStringForAccountPassword() {
		int length = 8;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	
	public static String getRandomStringForUrlShortKey() 
	{
		int length = 6;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    
	    return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	
}
