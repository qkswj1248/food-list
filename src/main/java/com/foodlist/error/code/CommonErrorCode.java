package com.foodlist.error.code;

import com.foodlist.error.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
Getter 를 써야 ErrorCode 인터페이스의
getHttpStatus()와 getMessage를 오버라이드 할 수 있다.(직접해도 되지만 이렇게 쉬운방법이 있는데?)
RequiredArgsConstructor은 enum에서 INVALID(a, b)이거 쓸려면 필수적인듯
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 잘못된 매개변수 포함
    INVALID_PARAMETER(404, "잘못된 매개변수 포함"),
    // 리소스 없음
    RESOURCE_NOT_FOUND(404, "리소스 없음"),
    // 내부 서버 오류
    INTERNAL_SERVER_ERROR(404, "내부 서버 오류"),
    SQL_ERROR(404, "SQL 오류"),
    DATA_ACCESS_ERROR(404, "sql 접근 에러")
    ;

    private final int code;
    private final String message;
}
