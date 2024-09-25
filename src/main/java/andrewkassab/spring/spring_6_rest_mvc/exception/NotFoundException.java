package andrewkassab.spring.spring_6_rest_mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value Not Found")
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
