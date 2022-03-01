package first.iteration.endlesscreation.controller;

import first.iteration.endlesscreation.exception.ApiExpection;
import first.iteration.endlesscreation.exception.DefaultExpection;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestExpection(ResourceNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExpection apiExpection = new ApiExpection(
                e.getMessage(),
                notFound,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExpection, notFound);
    }

    @ExceptionHandler(value = {InvalidPathVariableExpection.class})
    public ResponseEntity<Object> handleApiRequestExpection(InvalidPathVariableExpection e){
        HttpStatus invalidPathVariableStatus = HttpStatus.BAD_REQUEST;
        ApiExpection apiExpection = new ApiExpection(
                e.getMessage(),
                invalidPathVariableStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExpection, invalidPathVariableStatus);
    }

    @ExceptionHandler(value = {DefaultExpection.class})
    public ResponseEntity<Object> handleApiRequestExpection(DefaultExpection e){
        HttpStatus defaultExceptionStatus = HttpStatus.BAD_REQUEST;
        ApiExpection apiExpection = new ApiExpection(
                e.getMessage(),
                defaultExceptionStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiExpection, defaultExceptionStatus);
    }
}
