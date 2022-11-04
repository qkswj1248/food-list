package com.foodlist.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class IngredientForRecipeId implements Serializable {
    private int ingredientId;
    private int recipeId;
}
