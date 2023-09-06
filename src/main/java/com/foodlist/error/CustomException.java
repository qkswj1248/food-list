package com.foodlist.error;

import com.foodlist.error.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
언체크 예외를 상속받는 예외 클래스(런타임 시점 예외)
RuntimeException 의 하위 클래스가 Uncheck Exception 이고
Exception 의 하위 클래스가 Checked Exception 이다.

이 클래스는 IllegalAccessException 같은 런타임 에러를
CustomException 이라는 이름으로 만든것이다.
그러니 이것도 예외 중 하나!

이 클래스를 이용해서 비밀번호가 맞지 않는 등의
내가 설정한 예외를 이걸로 지정해서 처리할 수 있음!

 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
