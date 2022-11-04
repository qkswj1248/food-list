package com.foodlist.repository;

import com.foodlist.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public void save(Member member) {

    }

    @Override
    public Member findById(int memberId) {
        return null;
    }

    @Override
    public List<Member> findAll() {

        return null;
    }
}
