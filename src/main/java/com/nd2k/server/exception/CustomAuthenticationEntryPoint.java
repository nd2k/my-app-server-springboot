package com.nd2k.server.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        final String expired = (String) request.getAttribute("expired");
        if(expired == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            HashMap<String, String> responseObject = new HashMap<>();
            responseObject.put("status", "403");
            responseObject.put("errorMessage", "Authentication failed");
            responseObject.put("errorCode", "Authentication failed");
            response.getWriter().write(mapper.writeValueAsString(responseObject));
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            HashMap<String, String> responseObject = new HashMap<>();
            responseObject.put("status", "403");
            responseObject.put("errorMessage", "JWT Token expired");
            responseObject.put("errorCode", "JWT Token is expired");
            response.getWriter().write(mapper.writeValueAsString(responseObject));
        }
    }
}
