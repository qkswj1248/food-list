package com.foodlist.util;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
/*
응답할 JSON 형태
{
    "status" : "OK",
    "message" : "로그인 성공",
    "data" : {
        "~" : "~",
        "~" : "~",
    }
}
 */
@Getter @Setter
public class Message {

    // StatusResponse는 일단 에러코드를 4가지로 정해놓고 고르는 곳?
    // 여기서 에러코드를 쓰면 Exception이 발생했을때의 ErrorResponse가 필요없지 않나?
    private int status; // 에러, 상태표시
    private String message; // 응답메세지
    private Object data; // 전달할 객체 내용

}
