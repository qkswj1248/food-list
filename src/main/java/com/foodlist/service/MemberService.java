package com.foodlist.service;

import com.foodlist.domain.LoginMember;
import com.foodlist.domain.Member;
import com.foodlist.security.TokenInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MemberService {

    public Member findMember(LoginMember loginMember);

    public void addMember(Member member);
}
