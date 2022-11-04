package com.foodlist.repository;

import com.foodlist.domain.Ingredient;

import java.util.List;

public interface RefrigeRepository {
    void save(List<Ingredient> ingredients);
    void deleteOne(int id);

    List<Ingredient> findAll();
    Ingredient detail(int ingredientId);
}
