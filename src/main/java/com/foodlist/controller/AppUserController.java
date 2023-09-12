package com.foodlist.controller;
import com.foodlist.domain.LoginMember;
import com.foodlist.domain.Member;
import com.foodlist.security.TokenInfo;
import com.foodlist.service.MemberService;
import com.foodlist.service.SecurityService;
import com.foodlist.util.Message;
import com.foodlist.util.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class AppUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;
    private final SecurityService securityService;

    // RESTful한 요청을 위해서는 GET이 맞으나 GET 요청은 서버 데이터의 상태를 변경하지 않기 때문에
    // 쿼리를 적극적으로 캐싱할 수 있어 보안에 취약함
    // (다음페이지 로딩이나 이동할 때까지 로그파일이나 사용자 브라우저에 일반텍스트로 표시된다.)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> loginForJson(@RequestBody LoginMember loginMember) {

        // 받은 값(id, pw)
        System.out.println("user id : " + loginMember.getId());
        System.out.println("user pw : " + loginMember.getPassword());
        // 토큰 발급
        TokenInfo tokenInfo = securityService.createToken(loginMember);

        // 토큰 저장소에 저장
        securityService.addRefreshToken(tokenInfo.getRefreshToken());

        // body 구성
        Message message = new Message();
        message.setCode(SuccessCode.OK.getHttpStatus().value());
        message.setMessage("로그인 성공");
        message.setData(tokenInfo);

        // ResponseEntity 는 결과값, 헤더값, 상태코드를 모두 프론트에 넘겨줄 수 있고,
        // 에러코드도 설정해서 보내줄 수 있다.
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //어플에서 하는 요청
    //어플에선 json 형식으로 보내며 requestBody는 json을 받는다.
    @PostMapping(value = "/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> joinForJson(@RequestBody Member member){
        System.out.println("----------Response join-------------");
        System.out.println("member = " + member);
        System.out.println("member.id = " + member.getId());
        System.out.println("member.name = " + member.getName());
        System.out.println("member.pw = " + member.getPassword());
        System.out.println("member.email = " + member.getEmail());
        System.out.println("member.nickname = " + member.getNickname());

        memberService.addMember(member);

        // body 구성
        Message message = new Message();
        message.setCode(SuccessCode.OK.getHttpStatus().value());
        message.setMessage("가입 성공");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    //웹에서 하는 요청
    //웹에서 x-www-form-urlencoded로 요청하며 ModelAtrribute는 이를 이해한다.
//    @PostMapping(value = "/join", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)

}
