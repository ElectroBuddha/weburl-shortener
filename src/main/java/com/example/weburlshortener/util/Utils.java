package com.example.weburlshortener.util;

public class Utils {

	
	public static void ThreadSleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
	
	
}
