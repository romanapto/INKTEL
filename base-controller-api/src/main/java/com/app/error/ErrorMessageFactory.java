package com.app.error;

public class ErrorMessageFactory {

	private ErrorMessageFactory() {
	}

	public static final ErrorMessage get(Exception e) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.message = e.getMessage();
		return errorMessage;
	}

	public static final ErrorMessage get(String message) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.message = message;
		return errorMessage;
	}

	public static class ErrorMessage {

		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public ErrorMessage() {
		}
	}

}
