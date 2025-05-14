package org.user.exception;

import org.app.entity.ErrorHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user.dto.ResponseDTO;
import org.user.repository.ErrorHistoryRepository;
import org.user.repository.UserRepository;
import org.user.util.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ErrorHistoryRepository errorHistoryRepository;

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
        final ErrorHistory errorHistory = new ErrorHistory();
        errorHistory.setErrorMessage(exception.getMessage());
        errorHistory.setEndPoint(exception.getEndPoint());
        errorHistory.setUser(null);
        errorHistory.setClassName(exception.getMethodName());
        errorHistory.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(errorHistory);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}