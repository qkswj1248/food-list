package com.foodlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IngredientForRecipe {

    private IngredientForRecipeId ir_id;
    private String amount;
}
