package com.foodlist.controller;
import com.foodlist.domain.Member;
import com.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;

    @ResponseBody
    @RequestMapping(value = "/member")
    public List<Member> getMember(){
        return memberService.findMembers();
    }

    @GetMapping(value = "/")
    public String goMainPage(){
        return "index";
    }

    @GetMapping(value = "/members")
    public String getMembers(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "memberList";
    }

    @GetMapping(value = "/login")
    public String goLogin(){
        return "loginForm";
    }

    @PostMapping(value = "/login")
    public String login(Member member){
        memberService.addMember(member);
        return "index";
    }

    @GetMapping(value = "/join")
    public String goJoinForm(Model model){
        model.addAttribute("member", new Member());
        return "joinForm";
    }

    @PostMapping(value = "/join")
    public String join(Member member){
        memberService.addMember(member);
        return "index";
    }


}
