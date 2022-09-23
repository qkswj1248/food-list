package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Refrige {
    private int ingredientId;
    private String memberId;
    private Date date;
    private String memo;
}
