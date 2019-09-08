package com.example.weburlshortener.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.weburlshortener.controller.rest.model.ResponseError;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	logger.info(ex.getClass().getName());

        final ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Malformed JSON request");
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
	}
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	logger.info(ex.getClass().getName());

        List<String> errors = new ArrayList<String>();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }
    
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }
    
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
    	logger.error(ex.getMessage(), ex);

        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, responseError, new HttpHeaders(), responseError.getStatus(), request);
    }
    
    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        final ResponseError responseError = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final ResponseError responseError = new ResponseError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return handleExceptionInternal(ex, responseError, headers, responseError.getStatus(), request);
    }
    
    // 500 
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        final ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Internal server error occurred.");
        return handleExceptionInternal(ex, responseError, new HttpHeaders(), responseError.getStatus(), request);
    }
    
//	@ExceptionHandler({
//		HttpRequestMethodNotSupportedException.class,
//		HttpMediaTypeNotSupportedException.class,
//		HttpMediaTypeNotAcceptableException.class,
//		MissingPathVariableException.class,
//		MissingServletRequestParameterException.class,
//		ServletRequestBindingException.class,
//		ConversionNotSupportedException.class,
//		TypeMismatchException.class,
//		HttpMessageNotReadableException.class,
//		HttpMessageNotWritableException.class,
//		MethodArgumentNotValidException.class,
//		MissingServletRequestPartException.class,
//		BindException.class,
//		NoHandlerFoundException.class,
//		AsyncRequestTimeoutException.class
//	})
//	@Nullable
//    public ResponseEntity<Object> handleAllExceptions(final Exception ex, final WebRequest request) {
//        log.info(ex.getClass().getName());
//        log.error("error", ex);
//        //
//        final ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
//        return new ResponseEntity<Object>(responseError, new HttpHeaders(), responseError.getStatus());
//    }
    
    
    
}