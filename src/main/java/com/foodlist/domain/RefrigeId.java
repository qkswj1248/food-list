package com.foodlist.domain;

import lombok.Data;
import java.io.Serializable;

@Data
public class RefrigeId implements Serializable {
    private int ingredientId;
    private String memberId;
}
