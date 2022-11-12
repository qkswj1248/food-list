package com.foodlist.mapper;

import com.foodlist.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> findAll();

    void insert(Member member);
}
