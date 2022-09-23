package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ingredient {
    private int id;
    private String name;
    private String img;
    private String category;

    public Ingredient() {
    }

    public Ingredient(String name, String img, String category) {
        this.name = name;
        this.img = img;
        this.category = category;
    }
}
