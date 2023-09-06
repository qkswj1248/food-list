package com.foodlist.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

/*
@Configuration 은 @Component 를 포함하는데, 외부 라이브러리 또는 내장 클래스를
bean으로 등록하고자할 때, 1개 이상의 @Bean을 제공하는 클래스에 붙여 사용한다.
*/
@Configuration
@EnableWebSecurity // 스프링 시큐리티 사용을 위한 어노테이션(SpringSecurityFilterChain 이 포함됨)
@RequiredArgsConstructor
public class SecurityConfig {

    // 패스워드 암호화 하는 칭구 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public final JwtTokenProvider jwtTokenProvider;
    public final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private ObjectMapper objectMapper;

    /*
    spring security jwt 사용을 위해 기본으로 적용된 설정을 변경하거나
    필터에 인증, 인가 절차를 더하는 메소드
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // basic 이 아마 base64 였던 것 같은데 확실하진 않음
                .httpBasic().disable()
                /* 사용하지 않을 것들을 비활성화 해두기 */
                // token 을 사용하는 방식이기 때문에 csrf 를 disable 함
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                // 세션을 사용하지 않기 때문에 STATELESS 로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                /* security 적용할 곳과 아닐 곳 선정하기 */
                // url에 따른 접근 가능여부 설정
                .and()
                .authorizeRequests() // 뭔지 모르것음
                .antMatchers("/users/login").permitAll() // antMatchers에 설정한거 접근을 인증절차 없이 허용
                .antMatchers("/users/join").permitAll() // 마찬가지
                .antMatchers("/auth/get_token").permitAll() // test
                .anyRequest().authenticated() // 그 외 인증 없이 접근 x

                /* 어떤 필터를 실행할 것인지 적용하기 */
                .and()
                // jwt 인증을 위해 직접 구현한 필터를
                // UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정이다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
