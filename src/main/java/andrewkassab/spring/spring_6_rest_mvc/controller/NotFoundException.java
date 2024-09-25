package andrewkassab.spring.spring_6_rest_mvc.controller;

public class NotFoundException extends RuntimeException {
	
	public NotFoundException() {}
	
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(Throwable cause) {
		super(cause);
	}
	
	public NotFoundException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
		super(message, cause, enableSupression, writableStackTrace);
	}

}
