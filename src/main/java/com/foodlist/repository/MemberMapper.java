package com.foodlist.repository;

import com.foodlist.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
@Repository
public interface MemberMapper {
    ArrayList<HashMap<String, Member>> findAll();
}
