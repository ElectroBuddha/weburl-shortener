package com.example.weburlshortener.exceptions;

public class UniqueValueNotAvailable extends Exception {

	private static final long serialVersionUID = 1L;

	public UniqueValueNotAvailable() {
	}
	
	public UniqueValueNotAvailable(Throwable cause) {
		super(cause);
	}
	
	public UniqueValueNotAvailable(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
}