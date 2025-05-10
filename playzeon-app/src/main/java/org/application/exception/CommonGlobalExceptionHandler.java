package org.application.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user.dto.ResponseDTO;
import org.user.exception.BadRequestServiceException;
import org.user.util.Constants;

@RestControllerAdvice
public class CommonGlobalExceptionHandler {

    @ExceptionHandler(org.user.exception.BadRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestServiceAlertException(BadRequestServiceException exception) {
        exception.printStackTrace();
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleException(Exception exception) {
        exception.printStackTrace();
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}