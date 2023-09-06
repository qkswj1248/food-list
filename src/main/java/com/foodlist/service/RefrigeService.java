package com.foodlist.service;

import com.foodlist.domain.Refrige;

import java.util.List;

public interface RefrigeService {
    List<Refrige> findAll(String id);
}
