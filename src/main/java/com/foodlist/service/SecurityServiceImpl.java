package com.foodlist.service;

import com.foodlist.domain.LoginMember;
import com.foodlist.domain.Member;
import com.foodlist.error.CustomException;
import com.foodlist.error.TestException;
import com.foodlist.error.code.CommonErrorCode;
import com.foodlist.error.code.SecurityErrorCode;
import com.foodlist.mapper.MemberMapper;
import com.foodlist.mapper.RefreshTokenMapper;
import com.foodlist.security.JwtTokenProvider;
import com.foodlist.security.TokenInfo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/*
    토큰 발급하는 클래스
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{

    private final RefreshTokenMapper refreshTokenMapper;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;

    /*
        Spring Security 사용을 위한 login 메서드
        id, pw로 로그인 요청을 받으면 로직을 수행한 후
        token 을 만들어(?) 반환한다.
    */
    @Override
    public TokenInfo createToken(LoginMember loginMember) {
        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginMember.getId(), loginMember.getPassword());
        // 실제 사용자 비밀번호 체크가 이루어지는 부분
        // CustomUserDetailsService 에서 만든 loadUserByUsername 이 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return tokenInfo;
    }

    @Override
    public TokenInfo createTokenForRefreshToken(String refreshToken) {

        // refresh token 저장소 확인
        if(refreshToken == null){
            throw new CustomException(SecurityErrorCode.NO_HEADER_REFRESH_TOKEN);
        }
        getRefreshToken(refreshToken); // 없으면 Exception 발생

        // refresh 에서 유저 아이디 얻기
        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String id = claims.getSubject();

        // 아이디로 유저 찾기
        Member member = memberMapper.findById(id)
                .orElseThrow(() -> new TestException(SecurityErrorCode.SECURITY_USER_NOT_FOUND, id));

        // access token 발급
        TokenInfo token = createToken(new LoginMember(member.getId(), member.getPassword()));
        token.setRefreshToken(refreshToken);
        return token;
    }

    @Override
    public String getRefreshToken(String refreshToken) {
        return refreshTokenMapper.getRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(SecurityErrorCode.REFRESH_TOKEN_NOT_EXIST));
    }

    @Override
    public void addRefreshToken(String refreshToken) {
        try{
            refreshTokenMapper.addRefreshToken(refreshToken);
        }catch (DataAccessException e){
            throw new TestException(CommonErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }



}
