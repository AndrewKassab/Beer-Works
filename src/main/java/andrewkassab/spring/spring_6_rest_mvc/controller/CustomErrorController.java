package andrewkassab.spring.spring_6_rest_mvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException ex) {
        List errorList = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleJPAViolations(TransactionSystemException ex) {
        if (ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException ve = (ConstraintViolationException) ex.getCause().getCause();
            List errors = ve.getConstraintViolations().stream()
                    .map(violation -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
                        return errorMap;
                    }).toList();
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.badRequest().build();
    }

}
