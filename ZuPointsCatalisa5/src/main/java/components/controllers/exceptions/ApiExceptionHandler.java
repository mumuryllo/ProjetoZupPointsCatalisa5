package components.controllers.exceptions;

import components.services.exceptions.UsernameNotFoundException;
import components.services.exceptions.UsernameUniqueViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result
    ){
        log.error("Erro Api - ",ex);
        return ResponseEntity.
                status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.UNPROCESSABLE_ENTITY,"campo(s) inválido(s)!",result))
                ;
    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> usernameUniqueViolationException(
            RuntimeException ex,
            HttpServletRequest request
    ){
        log.error("Erro Api - ",ex);
        return ResponseEntity.
                status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()))
                ;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> usernameNotFoundException(
            RuntimeException ex,
            HttpServletRequest request
    ){
        log.error("Erro Api - ",ex);
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()))
                ;
    }

}
