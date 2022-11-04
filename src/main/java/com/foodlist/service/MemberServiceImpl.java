package com.foodlist.service;

import com.foodlist.domain.Member;
import com.foodlist.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Override
    public ArrayList<HashMap<String, Member>> findMembers() {
        System.out.println("여긴 memberService");
        return memberMapper.findAll();
    }
}
