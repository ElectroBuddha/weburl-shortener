package com.example.weburlshortener.controller.rest.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    
    public ResponseError() {
    }

    public ResponseError(final HttpStatus status, final String message, final List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ResponseError(final HttpStatus status, final String message, final String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
    
}