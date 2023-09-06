package com.foodlist.service;

import com.foodlist.domain.LoginMember;
import com.foodlist.security.TokenInfo;

public interface SecurityService {
    public TokenInfo createToken(LoginMember loginMember);

    public TokenInfo createTokenForRefreshToken(String refreshToken);

    public String getRefreshToken(String refreshToken);

    public void addRefreshToken(String refreshToken);

}
