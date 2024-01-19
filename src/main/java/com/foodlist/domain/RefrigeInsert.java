package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefrigeInsert {
    private int ingredient_id;
    private String member_id;
    private String name;
    private String shelf_life;
    private String memo;
    private String img;
    private int amount;
}
