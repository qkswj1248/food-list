package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class Member {
    private String id; // 이 id 는 mp010123 처럼 그 아이디다 그니까 String!!!
    private String name;
    private String password;
    private String email;
    private String nickname;

    public Member() {
    }

    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Member(String id, String name, String password, String email, String nickname) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }
}
