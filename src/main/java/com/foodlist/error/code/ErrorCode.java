package com.foodlist.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    int getCode();
    String getMessage();
}
