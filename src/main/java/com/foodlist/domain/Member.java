package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class Member {
    private String id;
    private String name;
    private String password;

    public Member() {
    }

    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
