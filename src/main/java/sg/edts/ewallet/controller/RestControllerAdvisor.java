package sg.edts.ewallet.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sg.edts.ewallet.dto.response.ApiBody;
import sg.edts.ewallet.exception.KtpTakenException;
import sg.edts.ewallet.exception.LimitExceededException;
import sg.edts.ewallet.exception.MinAmountException;
import sg.edts.ewallet.exception.NotEnoughBalanceException;
import sg.edts.ewallet.exception.PasswordInvalidException;
import sg.edts.ewallet.exception.UserBannedException;
import sg.edts.ewallet.exception.UserNotFoundException;
import sg.edts.ewallet.exception.UsernameTakenException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ApiBody<Void>> handleUsernameTakenException(UsernameTakenException ex, WebRequest request) {
        var body = ApiBody.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KtpTakenException.class)
    public ResponseEntity<ApiBody<Void>> handleKtpTakenException(KtpTakenException ex, WebRequest request) {
        var body = ApiBody.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiBody<Void>> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        var body = ApiBody.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ApiBody<Void>> handlePasswordIncorrectException(PasswordInvalidException ex, WebRequest request) {
        var body = ApiBody.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            LimitExceededException.class,
            MinAmountException.class,
            NotEnoughBalanceException.class,
            UserBannedException.class,
    })
    public ResponseEntity<ApiBody<Void>> handleCustomException(RuntimeException ex) {
        var body = ApiBody.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(ApiBody.error(msg), HttpStatus.BAD_REQUEST);
    }
}
