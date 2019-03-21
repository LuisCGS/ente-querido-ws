package br.com.entequerido.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError.toString(), apiError.getStatus());
	}
	
	@Override
	protected final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex, null));
	}
	
	@ExceptionHandler(ValidacaoException.class)
	protected final ResponseEntity<?> handleValicacaoException (ValidacaoException ev, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ev, ev.getPath()));
	}
	
	@ExceptionHandler(Exception.class)
	protected final ResponseEntity<?> handleAllException(Exception ev, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ev, null));
	}
}
