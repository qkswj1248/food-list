package com.foodlist.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodlist.error.code.ErrorCode;
import com.foodlist.error.code.SecurityErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 인증 실패 시
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        System.out.println("entry porint 왔음");
        System.out.println("auth exception");
        System.out.println(authException);

        if (authException instanceof BadCredentialsException){
            setResponse(response, SecurityErrorCode.SECURITY_PASSWORD_IS_WRONG);
        }else if(authException instanceof InsufficientAuthenticationException) {
            setResponse(response, SecurityErrorCode.CUSTOM_EXPIRED_TOKEN);
        }else{
            setResponse(response, SecurityErrorCode.UNKNOWN_ERROR);
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode code) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> data = new HashMap<>();
        data.put("code", code.getCode());
        data.put("error", code.name());
        data.put("message", code.getMessage());

        /*
        와아악 에러 해결했다 ObjectMapper writeValue 랑 response.write()
        둘 다 쓰면 에러남
         */
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), data);

    }
}
