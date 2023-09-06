package com.foodlist.service;

import com.foodlist.domain.Refrige;
import com.foodlist.mapper.RefrigeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefrigeServiceImpl implements RefrigeService{

    private final RefrigeMapper refrigeMapper;

    @Override
    public List<Refrige> findAll(String id) {
        List<Refrige> all = refrigeMapper.findAll(id);
        return all;
    }
}
