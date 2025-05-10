package org.user.exception;

import org.app.entity.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user.dto.ResponseDTO;
import org.user.repository.ErrorRepository;
import org.user.util.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorRepository errorRepository;

    @ExceptionHandler(BadRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestServiceAlertException(BadRequestServiceException exception) {
        final ResponseDTO responseDTO = new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        exception.printStackTrace();
        return responseDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleException(Exception exception) {
        final ResponseDTO responseDTO = new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        exception.printStackTrace();
        return responseDTO;
    }

    @ExceptionHandler(UserRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(UserRequestServiceException exception) {
        final Error error = new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}