package com.example.weburlshortener.exception;

public class EntityAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExists() {
	}
	
	public EntityAlreadyExists(Throwable cause) {
		super(cause);
	}
	
	public EntityAlreadyExists(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
}
