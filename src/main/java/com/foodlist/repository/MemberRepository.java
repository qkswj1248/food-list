package com.foodlist.repository;

import com.foodlist.domain.Member;

public interface MemberRepository {
    void save(Member member);
    Member findById(int memberId);
}
