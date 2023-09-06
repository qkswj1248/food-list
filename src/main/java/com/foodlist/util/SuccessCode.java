package com.foodlist.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED)
    ;
    private final HttpStatus httpStatus;
}
