package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter @Setter
public class Refrige {

    private RefrigeId refrigeId;

    private Date date;
    private String memo;
}
