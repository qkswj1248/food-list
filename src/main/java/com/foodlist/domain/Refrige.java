package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter @Setter
public class Refrige {
    private int ingredient_id;
    private Date shelf_life;
    private String img;
    private int amount;

}
