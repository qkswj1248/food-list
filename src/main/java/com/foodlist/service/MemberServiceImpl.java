package com.foodlist.service;

import com.foodlist.domain.LoginMember;
import com.foodlist.domain.Member;
import com.foodlist.error.CustomException;
import com.foodlist.error.TestException;
import com.foodlist.error.code.CommonErrorCode;
import com.foodlist.error.code.UserErrorCode;
import com.foodlist.mapper.MemberMapper;
import com.foodlist.security.JwtTokenProvider;
import com.foodlist.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    /*
    현재 프로젝트 흐름
    Controller > Service > Mapper > mapper.xml
     */
    private final MemberMapper memberMapper;

    @Override
    public Member findMember(LoginMember loginMember) {
        /*
                throw 를 해도 이어서 계속 실행이되나? > 다음 print 는 출력이 안된다
                이게 약간 return 이랑 비슷한 느낌일듯? 그래서 controller 로 간 다음에
                ExceptionHandler 가 CustomException error 를 받아서 실행하는 듯!
        */
        Member member = memberMapper.findById(loginMember.getId())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        if(!member.getPassword().equals(loginMember.getPassword())){
            log.warn("password fail");
            throw new CustomException(UserErrorCode.USER_PASSWORD_FAIL);
        }

        return member;
    }

    @Override
    public void addMember(Member member) {
        /*
        이미 가입된 사용자인지 findById 로 확인하기
        근데 이건 따로 함수로 만들어야 할듯
         */
        Member findMember = memberMapper.findById(member.getId())
                .orElseThrow(() -> new CustomException(UserErrorCode.EXISTING_USER));

        try{
            memberMapper.insert(member);
            // 런타임 예외, jdbcTemplate 메소드는 SQLException 말고 이걸로 던진다.
        }catch (DataAccessException e){
            throw new TestException(CommonErrorCode.DATA_ACCESS_ERROR, e.getMessage());
        }
    }


}
