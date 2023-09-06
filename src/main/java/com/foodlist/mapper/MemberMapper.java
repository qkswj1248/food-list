package com.foodlist.mapper;

import com.foodlist.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 여기서 발생하는 에러는 어떻게 받고 어떻게 처리하지?
    List<Member> findAll();

    Optional<Member> findById(String id);

    void insert(Member member);

}
