package com.foodlist.repository;

import com.foodlist.domain.Member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);
    Member findById(int memberId);
    List<Member> findAll();
}