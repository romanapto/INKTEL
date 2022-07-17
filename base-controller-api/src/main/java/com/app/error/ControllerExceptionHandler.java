package com.app.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.app.error.ErrorMessageFactory.ErrorMessage;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public void externalException(Exception exception) {
		LOG.error("Error caught", exception);
	}

	@ExceptionHandler({ IllegalStateException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage illegalStateException(Exception exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage illegalArgumentException(Exception exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorMessage dataIntegrityViolationException(DataIntegrityViolationException exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception.getMostSpecificCause().getMessage());
	}

	@ExceptionHandler({ UsernameNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorMessage usernameNotFoundException(Exception exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorMessage accessDeniedException(Exception exception) {
		LOG.error("Error caught", exception);
		return ErrorMessageFactory.get(exception);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> clientException(HttpClientErrorException e) {
		LOG.error("Error caught", e);
		String bodyAsString = e.getResponseBodyAsString();
		try {
			return new ResponseEntity<ErrorMessageFactory.ErrorMessage>(ErrorMessageFactory.get(bodyAsString),
					e.getStatusCode());
		} catch (Exception exception) {
			return new ResponseEntity<ErrorMessageFactory.ErrorMessage>(HttpStatus.BAD_GATEWAY);
		}
	}

	@ExceptionHandler(HttpServerErrorException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> serverException(HttpServerErrorException e) {
		LOG.error("Error caught", e);
		String bodyAsString = e.getResponseBodyAsString();
		try {
			return new ResponseEntity<ErrorMessageFactory.ErrorMessage>(ErrorMessageFactory.get(bodyAsString),
					e.getStatusCode());
		} catch (Exception exception) {
			return new ResponseEntity<ErrorMessageFactory.ErrorMessage>(HttpStatus.BAD_GATEWAY);
		}
	}

}
