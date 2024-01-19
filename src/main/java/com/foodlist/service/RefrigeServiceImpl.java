package com.foodlist.service;

import com.foodlist.domain.Ingredient;
import com.foodlist.domain.Member;
import com.foodlist.domain.Refrige;
import com.foodlist.domain.RefrigeInsert;
import com.foodlist.error.CustomException;
import com.foodlist.error.TestException;
import com.foodlist.error.code.CommonErrorCode;
import com.foodlist.error.code.UserErrorCode;
import com.foodlist.mapper.RefrigeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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

    @Override
    public void addRefrige(RefrigeInsert refrigeInsert) {
        try{
            refrigeMapper.addRefrige(refrigeInsert);
            // 런타임 예외, jdbcTemplate 메소드는 SQLException 말고 이걸로 던진다.
        }catch (DataAccessException e){
            throw new TestException(CommonErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }

    @Override
    public List<Ingredient> ingFindAll() {
        List<Ingredient> all = refrigeMapper.ingFindAll();
        return all;
    }


}
