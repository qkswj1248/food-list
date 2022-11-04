package com.foodlist.controller;
import com.foodlist.domain.Member;
import com.foodlist.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    public String allMembers(Model model) {
        System.out.println("여긴 TestController");
        log.info("log Test");
        ArrayList<HashMap<String, Member>> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "memberList";
    }
}
