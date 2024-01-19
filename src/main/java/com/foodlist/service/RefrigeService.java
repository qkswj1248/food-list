package com.foodlist.service;

import com.foodlist.domain.Ingredient;
import com.foodlist.domain.Refrige;
import com.foodlist.domain.RefrigeInsert;

import java.util.List;

public interface RefrigeService {
    List<Refrige> findAll(String id);

    void addRefrige(RefrigeInsert refrigeInsert);

    List<Ingredient> ingFindAll();
}
