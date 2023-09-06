package com.foodlist.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/*

서버 개인키 가져와서
accessToken 이랑 RefreshToken 만들고
받은 토큰 서버 개인키로 복호화해서 돌려주는 함수가 있는 클래스

JwtTokenProvider 를 생성하기 위해서는 Key 가 필요한데
이 키는 application.yml 에서 jwt.secret 값을 가져와서 넣어준다.

 */
@Slf4j
@Component //@Component 는 개발자가 직접 작성한 Class 를 Bean 으로 등록하기 위한 어노테이션이다.
public class JwtTokenProvider {
    private final Key key;
    private static final int SECOND = 60 * 60000; // 60000 = 1분

    /*
    @Value : properties 나 yml 에 있는 값을 가져오는 방법
    Value 에러 이유 -> 라이브러리 lombok.Value 로 되어있었음^^
     */
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveAccessToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println("Authorization" + " = " + token);

        if(request.getHeader("Authorization") != null){
            return request.getHeader("Authorization");
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request){
        String token = request.getHeader("RefreshToken");
        System.out.println("RefreshToken" + " = " + token);

        if(request.getHeader("RefreshToken") != null){
            return request.getHeader("RefreshToken");
        }
        return null;
    }


    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메소드
    public TokenInfo generateToken(Authentication authentication){ // 인증
        // 권한 가져오기 authorities = 권한인듯
        /*
        < 검색 키워드 : java stream map >
        getAuthorities() 에서 GrantedAuthority 를 포함한 collection 으로 반환함
        collection 에서 GrantedAuthority 안에 있는 getAuthority 만 가져오기
        가져온 값들 사이에 "," 이거 넣고 String 으로 만들기
         */
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //!! 권한에 어떤게 있는지 알아내야함!
        System.out.println("authorities(권한) : " + authorities);
        // 결과 : ROLE_basic
        System.out.println("authentication(인증).getName() : " + authentication.getName());
        // 결과 : a

        // 현재시간
        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + SECOND);

        // authentication 마다 이름이 다르나? 이 메소드를 부르는 쪽을 봐야할듯
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // sub 이름
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(new Date(now + SECOND*100))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /*
        jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
        Authentication = 인증
        accessToken 을 받아서 정보 빼냄

        // getAuthentication 메소드는 토큰에 담겨있는 권한 정보들을 이용해 Authentication 객체를 리턴합니다.

     */
    public Authentication getAuthentication(String accessToken){
        // token 에서 payload 부분만 불러내는 메소드인듯?
        // 일단 토큰 복호화하는 부분
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null){
            log.info("권한 정보가 없는 토큰입니다.");
        }

        // claim 에서 권한 정보 가져와서 리스트로 만들기?
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 개체를 만들어서 Authentication(인증) 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        System.out.println("TokenProvider getAuthentication 완료");
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메소드
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e){
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }


    public Claims parseClaims(String token) {
        try {
            // header 에 있는 알고리즘 정보없이 해독이 가능한가?
            return Jwts.parserBuilder().
                    setSigningKey(key).build().
                    parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }



}
