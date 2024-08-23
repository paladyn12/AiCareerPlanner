package com.careerservice.ai_career_planner.config.auth;

import com.careerservice.ai_career_planner.domain.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Slf4j
public class UserDetail implements UserDetails {

    private User user;
    public UserDetail(User user) {
        this.user = user;
    }

    //계정이 가지고 있는 권한 목록 (Role, 아직 구현 X)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    // 계정이 만료되었는지 (true: 만료 X)
    @Override
    public boolean isAccountNonExpired() { return true; }

    // 계정이 잠겼는지 (true : 잠김 X)
    @Override
    public boolean isAccountNonLocked() { return true; }

    // 비밀번호가 만료되었는지 (ture: 만료 X)
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    // 계정이 활성화(사용 가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() { return true; }
}
