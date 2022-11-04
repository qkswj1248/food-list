package com.foodlist.service;

import com.foodlist.domain.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

public interface MemberService {
    public ArrayList<HashMap<String, Member>> findMembers();
}
