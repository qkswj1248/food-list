package com.foodlist.mapper;

import com.foodlist.domain.Refrige;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RefrigeMapper {
    List<Refrige> findAll(String id);
}
