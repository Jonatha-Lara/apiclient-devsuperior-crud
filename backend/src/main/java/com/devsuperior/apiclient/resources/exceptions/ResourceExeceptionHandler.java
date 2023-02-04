package com.devsuperior.apiclient.resources.exceptions;


import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.apiclient.services.exceptions.DataBaseException;
import com.devsuperior.apiclient.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExeceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestemp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> entityDataBase(DataBaseException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestemp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Data base error");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
}
