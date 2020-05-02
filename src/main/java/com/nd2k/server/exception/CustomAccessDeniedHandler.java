package com.nd2k.server.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nd2k.server.dto.BusinessExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        HashMap<String, String> responseObject = new HashMap<>();
        responseObject.put("status", "403");
        responseObject.put("errorMessage", "Access denied");
        responseObject.put("errorCode", "Access denied");
        response.getWriter().write(mapper.writeValueAsString(responseObject));


    }
}
