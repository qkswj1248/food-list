package com.foodlist.controller;

import com.foodlist.error.CustomException;
import com.foodlist.error.code.SecurityErrorCode;
import com.foodlist.security.JwtTokenProvider;
import com.foodlist.security.TokenInfo;
import com.foodlist.service.SecurityService;
import com.foodlist.util.Message;
import com.foodlist.util.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SecurityService securityService;
    private final JwtTokenProvider jwtTokenProvider;

    // requestBody 없으면 controller 가 두 번 호출된다.
    @PostMapping(value = "/tokentest", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> tokenTest(@RequestBody TokenInfo tokenInfo) {

        System.out.println("controller 도착");

        // body 구성
        Message message = new Message();
        message.setStatus(SuccessCode.OK.getHttpStatus().value());
        message.setMessage("토큰 인증 성공");

        System.out.println("끝");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // requestBody 없으면 controller 가 두 번 호출된다.
    @PostMapping(value = "/get_token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getAccessToken(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("controller 도착");

        // refreshToken 가져오기
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        System.out.println("refreshToken = " + refreshToken);

        // access token 발급
        TokenInfo tokenInfo = securityService.createTokenForRefreshToken(refreshToken);

        // body 구성
        Message message = new Message();
        message.setStatus(SuccessCode.OK.getHttpStatus().value());
        message.setMessage("토큰 재발급");
        message.setData(tokenInfo);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
