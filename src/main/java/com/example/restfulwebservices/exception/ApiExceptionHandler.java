package com.example.restfulwebservices.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = {InternalErrorException.class})
    public ResponseEntity<Object> handleApiRequestException(@NotNull InternalErrorException ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException exception = new ApiException(
                new Date(),
                ex.getMessage(),
                internalServerError);

        return new ResponseEntity<>(exception, internalServerError);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(@NotNull NotFoundException ex) {
        HttpStatus notFoundError = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                new Date(),
                ex.getMessage(),
                notFoundError);

        return new ResponseEntity<>(exception, notFoundError);
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        String errMessage = getNotValidArgumentErrorMessage(ex);
        HttpStatus badRequestError = HttpStatus.BAD_REQUEST;
        ApiException exception = new ApiException(
                new Date(),
                String.format("Fields validation failed - %s", errMessage),
                badRequestError);

        return new ResponseEntity<>(exception, badRequestError);
    }

    @NotNull
    private String getNotValidArgumentErrorMessage(MethodArgumentNotValidException ex) {
        try {
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String[] errParts = errors.get(0).toString().split(";");
            String fullMessage = errParts[errParts.length - 1];
            return fullMessage.substring(fullMessage.indexOf("[") + 1, fullMessage.indexOf("]"));
        } catch (Exception e) {
            return "";
        }
    }
}

