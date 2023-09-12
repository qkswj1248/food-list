package com.foodlist.service;

import com.foodlist.domain.Member;
import com.foodlist.error.CustomException;
import com.foodlist.error.TestException;
import com.foodlist.error.code.SecurityErrorCode;
import com.foodlist.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/*
spring security test 를 위한 클래스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /*
        userdetailsService 말고
        mybatis 를 이용해서 할 수 있게
        다른 방법은 없나?

        그대로 써도 되는듯 대신 optional 은 적용해야함

        optional 보다는
        UserDetails를 implement 하는 DTO를 반환하는 게 더 중요한 듯

    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("method : CustomUserDetailsService.loadUserByUsername");
        UserDetails userDetails = memberMapper.findById(username)
                .map(this::createUserDetails)
                /*
                이 else 는 찾은 user 가 null 일때 실행되는 거야
                아님 map에서 설정하는게 안됐을때 실행되는 거야?
                 */
                .orElseThrow(() -> new CustomException(SecurityErrorCode.SECURITY_USER_NOT_FOUND));

        System.out.println("아아니 뭐가 문젠데!");

        return userDetails;
    }

    private UserDetails createUserDetails(Member member){
        List<String> list = Arrays.asList("basic");
        return User.builder()
                .username(member.getId())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(list.toArray(new String[0]))
                .build();
    }



}
