package com.foodlist.controller;

import com.foodlist.domain.Ingredient;
import com.foodlist.domain.Member;
import com.foodlist.domain.Refrige;
import com.foodlist.domain.RefrigeInsert;
import com.foodlist.security.JwtTokenProvider;
import com.foodlist.service.RefrigeService;
import com.foodlist.util.Message;
import com.foodlist.util.SuccessCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Message message = createSuccessMessage("재료 가져오기 성공", all);

        all.forEach(e -> System.out.println(e.getShelf_life()));

        // ResponseEntity 는 결과값, 헤더값, 상태코드를 모두 프론트에 넘겨줄 수 있고,
        // 에러코드도 설정해서 보내줄 수 있다.
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/add_refrige", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> addRefrige(@RequestBody RefrigeInsert refrigeInsert, HttpServletRequest request){
        String token = jwtTokenProvider.resolveAccessToken(request);
        Claims claims = jwtTokenProvider.parseClaims(token);

        System.out.println("claims.getSubject() : " + claims.getSubject());

        refrigeService.addRefrige(refrigeInsert);

        Message message = createSuccessMessage("재료 추가함");
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 이거 토큰 필요없는데 그럼 get으로 받을까?
    @PostMapping(value = "/ing_all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getIngredientList(HttpServletRequest request) {

        List<Ingredient> all = refrigeService.ingFindAll();

        // body 구성
        Message message = createSuccessMessage("재료 리스트 가져오기 성공", all);

        // ResponseEntity 는 결과값, 헤더값, 상태코드를 모두 프론트에 넘겨줄 수 있고,
        // 에러코드도 설정해서 보내줄 수 있다.
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public Message createSuccessMessage(String text, Object data){
        Message message = new Message();
        message.setCode(SuccessCode.OK.getHttpStatus().value());
        message.setMessage(text);
        message.setData(data);
        return message;
    }

    public Message createSuccessMessage(String text){
        Message message = new Message();
        message.setCode(SuccessCode.OK.getHttpStatus().value());
        message.setMessage(text);
        return message;
    }

}
