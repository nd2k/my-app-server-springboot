package com.nd2k.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessExceptionDto {

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
}
