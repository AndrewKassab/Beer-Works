package andrewkassab.spring.spring_6_rest_mvc.controller;

import andrewkassab.spring.spring_6_rest_mvc.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ExceptionController {

    //@ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

}
