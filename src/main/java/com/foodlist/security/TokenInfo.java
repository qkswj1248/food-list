package com.foodlist.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
    // grantType 은 JWT 에 대한 인증 타입으로 Bearer 를 사용한다.
    // + HTTP 헤더에 prefix 로 붙여줌
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
