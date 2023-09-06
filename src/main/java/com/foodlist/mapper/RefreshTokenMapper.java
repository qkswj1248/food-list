package com.foodlist.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    Optional<String> getRefreshToken(String token);
    void addRefreshToken(String token);
}
