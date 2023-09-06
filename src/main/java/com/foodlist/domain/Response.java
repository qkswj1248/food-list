package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Response {
    private Boolean success;
    private String message;

    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
