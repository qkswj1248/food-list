package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Recipe {
    private int id;
    private String name;
    private String explain;
    private String img;

    public Recipe() {
    }

    public Recipe(String name, String explain, String img) {
        this.name = name;
        this.explain = explain;
        this.img = img;
    }
}
