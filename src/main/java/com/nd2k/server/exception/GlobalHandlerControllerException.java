package com.nd2k.server.exception;

import com.nd2k.server.dto.BusinessExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.nd2k.server"})
public class GlobalHandlerControllerException extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessExceptionDto> exceptionHandler(Exception ex) {
        BusinessExceptionDto response = new BusinessExceptionDto();
        response.setErrorCode("Technical Error");
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessExceptionDto> businessExceptionHandler(BusinessException ex) {
        BusinessExceptionDto businessExceptionDto = new BusinessExceptionDto();
        businessExceptionDto.setHttpStatus(ex.getHttpStatus());
        businessExceptionDto.setErrorCode(ex.getErrorCode());
        businessExceptionDto.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(businessExceptionDto, businessExceptionDto.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BusinessExceptionDto> nullPointerExceptionHandler(NullPointerException ex) {
        BusinessExceptionDto businessExceptionDto = new BusinessExceptionDto();
        businessExceptionDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        businessExceptionDto.setErrorCode("Null values Exception");
        businessExceptionDto.setErrorMessage("The request received is null");
        return new ResponseEntity<>(businessExceptionDto, businessExceptionDto.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BusinessExceptionDto businessExceptionDto = new BusinessExceptionDto();
        businessExceptionDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        businessExceptionDto.setErrorCode("Message not readable");
        businessExceptionDto.setErrorMessage("The request received cannot be red");
        return new ResponseEntity<>(businessExceptionDto, businessExceptionDto.getHttpStatus());
    }


}
