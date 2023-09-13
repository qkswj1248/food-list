package com.foodlist.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode{

    // 바꿀 수 없는 에러는 400번대로
    // 재요청하거나 대응할 수 있는 에러는 1000번대로
    TOKEN_AUTHENTICATION_FAILED(404, "토큰 인증 실패"),
    UNKNOWN_ERROR(404, "알 수 없는 에러"),
    SECURITY_USER_NOT_FOUND(1101, "유저를 찾을 수 없습니다."),
    SECURITY_PASSWORD_IS_WRONG(1102, "패스워드가 틀립니다."),
    ACCESS_DENIED(404, "접근 금지"),

    CUSTOM_WRONG_TYPE_TOKEN(404, "잘못된 타입의 토큰"),
    CUSTOM_EXPIRED_TOKEN(1000, "토큰이 만료됨"),
    CUSTOM_UNSUPPORTED_TOKEN(404, "지원되지 않는 토큰"),
    REFRESH_TOKEN_NOT_EXIST(404, "Refresh Token 이 존재하지 않습니다."),
    NO_HEADER_REFRESH_TOKEN(404, "헤더에 Refresh Token 이 존재하지 않습니다.")
    ;

    private final int code;
    private final String message;
}
