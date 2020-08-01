package com.appsdeveloperblog.app.ws.mobileappws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppEceptionHandler {
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
		ErrorMessage errorMesage = new ErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleOtherUserServiceException(Exception ex, WebRequest request) {
		ErrorMessage errorMesage = new ErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
