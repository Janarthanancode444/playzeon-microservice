package org.application.exception;

import org.app.entity.ErrorHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.user.dto.ResponseDTO;
import org.user.exception.BadRequestServiceException;
import org.user.repository.ErrorHistoryRepository;
import org.user.util.Constants;

public class AppGlobalExceptionHandler {
    @Autowired
    private ErrorHistoryRepository errorHistoryRepository;

    @ExceptionHandler(org.user.exception.BadRequestServiceException.class)
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

    @ExceptionHandler(OrganizationRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(OrganizationRequestServiceException exception) {
        final ErrorHistory error = new ErrorHistory();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(CenterRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(CenterRequestServiceException exception) {
        final ErrorHistory error = new ErrorHistory();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(SportRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(SportRequestServiceException exception) {
        final ErrorHistory error = new ErrorHistory();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(ImageRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(ImageRequestServiceException exception) {
        final ErrorHistory error = new ErrorHistory();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(SportCenterMapRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(SportCenterMapRequestServiceException exception) {
        final ErrorHistory error = new ErrorHistory();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndPoint());
        error.setMethodName(exception.getMethodName());
        this.errorHistoryRepository.save(error);
        return new ResponseDTO(Constants.NOT_FOUND, exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }


}
