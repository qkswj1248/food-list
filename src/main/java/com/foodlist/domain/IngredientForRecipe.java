package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientForRecipe {
    private int ingredientId;
    private int recipeId;
    private String amount;
}
