package com.foodlist.controller;

import com.foodlist.domain.LoginMember;
import com.foodlist.domain.Refrige;
import com.foodlist.security.JwtTokenProvider;
import com.foodlist.security.TokenInfo;
import com.foodlist.service.RefrigeService;
import com.foodlist.service.SecurityService;
import com.foodlist.util.Message;
import com.foodlist.util.SuccessCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/refrige")
public class RefrigeController {

    private final RefrigeService refrigeService;
    private final JwtTokenProvider jwtTokenProvider;

    // 해당 멤버의 냉장고 재료 가져오기
    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getRefrigeList(HttpServletRequest request) {

        System.out.println("-------controller 옴-------");

        String token = jwtTokenProvider.resolveAccessToken(request);
        Claims claims = jwtTokenProvider.parseClaims(token);

        System.out.println("claims.getSubject() : " + claims.getSubject());

        // id를 따로 받으면 토큰을 쓰는 의미가 없지 않나?
        List<Refrige> all = refrigeService.findAll(claims.getSubject());

        // body 구성
        Message message = new Message();
        message.setStatus(SuccessCode.OK.getHttpStatus().value());
        message.setMessage("재료 가져오기 성공");
        message.setData(all);

        // ResponseEntity 는 결과값, 헤더값, 상태코드를 모두 프론트에 넘겨줄 수 있고,
        // 에러코드도 설정해서 보내줄 수 있다.
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



}
