package com.careerservice.ai_career_planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 여기에 인증을 무시할 경로를 추가하세요.
                .requestMatchers("/", "/home", "/**").permitAll()
                // 모든 다른 요청은 인증 필요
                .anyRequest().authenticated()
                .and()
                // 로그인 페이지 비활성화
                .formLogin().disable()
                // CSRF 비활성화 (개발 중일 경우)
                .csrf().disable();

        return http.build();
    }
}