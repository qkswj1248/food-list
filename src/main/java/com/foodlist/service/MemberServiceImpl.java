package com.foodlist.service;

import com.foodlist.domain.Member;
import com.foodlist.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public List<Member> findMembers() {
        return memberMapper.findAll();
    }

    @Override
    public void addMember(Member member) {
        memberMapper.insert(member);
    }
}
