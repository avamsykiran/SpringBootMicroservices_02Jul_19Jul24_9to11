package com.cts.adb.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.adb.exceptions.AddressBookException;

@RestControllerAdvice
public class GlobalExceptionAdvice {

	private Logger logger;
	
	public GlobalExceptionAdvice() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@ExceptionHandler(AddressBookException.class)
	public ResponseEntity<String> handleAddressBookException(AddressBookException exp){
		return new ResponseEntity<String>(exp.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exp){
		logger.error(exp.getMessage(), exp);
		return new ResponseEntity<String>(exp.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
