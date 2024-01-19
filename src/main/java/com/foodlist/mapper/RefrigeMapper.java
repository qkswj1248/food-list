package com.foodlist.mapper;

import com.foodlist.domain.Ingredient;
import com.foodlist.domain.Refrige;
import com.foodlist.domain.RefrigeInsert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RefrigeMapper {
    List<Refrige> findAll(String id);
    void addRefrige(RefrigeInsert refrigeInsert);
    List<Ingredient> ingFindAll();
}
