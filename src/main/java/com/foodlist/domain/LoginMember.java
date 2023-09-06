package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter @Setter
public class LoginMember {
    private String id;
    private String password;

    //command + n
    public LoginMember(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
