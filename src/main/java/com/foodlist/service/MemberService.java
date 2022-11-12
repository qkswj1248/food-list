package com.foodlist.service;

import com.foodlist.domain.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MemberService {
    public List<Member> findMembers();

    public void addMember(Member member);
}
