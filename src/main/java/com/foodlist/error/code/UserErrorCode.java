package com.foodlist.error.code;

import com.foodlist.error.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(404, "해당 유저 정보가 없습니다."),
    USER_PASSWORD_FAIL(404, "비밀번호가 맞지 않습니다."),
    EXISTING_USER(404, "이미 존재하는 유저입니다."),
    ;

    private final int code;
    private final String message;
}
