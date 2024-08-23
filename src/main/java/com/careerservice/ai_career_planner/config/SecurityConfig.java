package com.careerservice.ai_career_planner.config;

import com.careerservice.ai_career_planner.config.auth.MyLoginSuccessHandler;
import com.careerservice.ai_career_planner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

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
                .formLogin()
                .loginPage("/users/login")                 // 로그인 페이지
                .usernameParameter("loginId")
                .passwordParameter("password")
                .failureUrl("/users/login?fail") // 로그인 실패 시 redirect 될 URL => 실패 메세지 출력
                .successHandler(new MyLoginSuccessHandler(userRepository))    // 로그인 성공 시 실행 될 Handler
                // CSRF 비활성화 (개발 중일 경우)
                .and()
                .csrf().disable();

        return http.build();
    }
}