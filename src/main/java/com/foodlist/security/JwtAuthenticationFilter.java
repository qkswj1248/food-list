package com.foodlist.security;

import com.foodlist.error.code.SecurityErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    public final JwtTokenProvider jwtTokenProvider;
    private static final String ACCESS_TOKEN = "Authorization";
    private static final String REFRESH_TOKEN = "RefreshToken";

    /*
    음 antMatcher 를 하면 여기 실행 안되는줄 알았는데 실행되네?
    일단 토큰이 있으면 유효성 검사해서 SecurityContext 에 넣고

    토큰이 null 이면 그냥 다음필터로 넘어가는 듯
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            // Request Header 에서 토큰 추출하기(없으면 null 반환)
            String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);

            // null 인지 확인하고 validateToken 으로 토큰 유효성 검사
            if(accessToken != null && jwtTokenProvider.validateToken(accessToken)){
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가져와서 SecurityContext 에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            logger.warn(e);
        }
        chain.doFilter(request, response);
    }


}
