package com.dinhphuc.learningspringsecurity.exceptionHandler;

import com.dinhphuc.learningspringsecurity.objects.ResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage("You dont have permission for this action");
        responseObject.setError(HttpStatus.UNAUTHORIZED.value());
        responseObject.setObject(null);
        new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
    }
}
