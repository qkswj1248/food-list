package com.foodlist.error;

import com.foodlist.error.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String error;
}
