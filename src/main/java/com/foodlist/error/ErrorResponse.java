package com.foodlist.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodlist.error.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/*
에러 응답 클래스 -> JSON 을 어떤 포맷으로 응답할지 정하는 클래스
자기 자신을 생성하는 함수도 포함하고 있음.

[수정전]
{
    "code" : "INACTIVATE_USER",
    "message" : "user is inactive
}
[수정후]
{
    "status" : 404 -> errorCode.getHttpStatus().value()
    "error" : "MEMEBER_NOT_FOUND" -> errorCode.name()
    "message" : "유저를 찾을 수 없음" -> errorCode.getMessage()
}
{
    "status" : 300
    "error" : "INVALID_PARAMETER"
    "message" : "적절하지 않은 인자"
    "detail" : ~~~~~ ->
}
이런 식으로 답해주기 위함

 */
@Getter
@Builder
@RequiredArgsConstructor // 생성자 자동 주입(DI)
public class ErrorResponse {

    // requiredArgs는 fianl이나 NonNull 붙은 필드에 DI를 해준다.
    // 여기서 에러메시지를 돌려주면 프론트는 어떤식으로 받게되지?
    private final int code;
    private final String message;
    private final Map<String, String> data;

    // null 값이나 length 가 0인 값들을 제외시키도록 조정할 수 있는 어노테이션
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String detail;

    public static ResponseEntity<ErrorResponse> toCustomResponseEntity(ErrorCode errorCode){
        Map<String, String> map = new HashMap<>();
        map.put("error", errorCode.name());
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .data(map)
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String e){
        Map<String, String> map = new HashMap<>();
        map.put("error", errorCode.name());
        return ResponseEntity
                .status(404)
                .body(ErrorResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .data(map)
                        .detail(e)
                        .build()
                );
    }

}
