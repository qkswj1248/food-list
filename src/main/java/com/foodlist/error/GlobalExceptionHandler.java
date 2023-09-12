package com.foodlist.error;

import com.foodlist.error.code.CommonErrorCode;
import com.foodlist.error.code.ErrorCode;
import com.foodlist.error.code.SecurityErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

// 에러를 잡아서 처리할 클래스
// 왜 ResponseEntityExceptionHandler 를 쓰는지는 모름
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 내가 설정한 예외를 잡아서 처리
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.toCustomResponseEntity(errorCode);
    }

    @ExceptionHandler(TestException.class)
    private ResponseEntity<ErrorResponse> handleTestException(TestException e){
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.toResponseEntity(errorCode, e.getError());
    }

    // 언체크 예외를 잡아서 처리
    // illegal 은 적절하지 못한 인자를 메소드에 넘겨주었을 때 발생한다.
    // @Valid에 의한 유효성 검증에 실패했을 때 발생함(??)
    @ExceptionHandler(IllegalAccessException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalAccessException e){
        log.warn("handleIllegalArgument", e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return ErrorResponse.toResponseEntity(errorCode, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    private ResponseEntity<ErrorResponse> handleSQLException(SQLException e){
        log.warn("handleSQLException", e);
        ErrorCode errorCode = CommonErrorCode.SQL_ERROR;
        return ErrorResponse.toResponseEntity(errorCode, e.getMessage());
    }

}
