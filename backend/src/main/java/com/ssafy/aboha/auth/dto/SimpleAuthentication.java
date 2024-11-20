package com.ssafy.aboha.auth.dto;

import com.ssafy.aboha.user.dto.response.UserResponse;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SimpleAuthentication implements Authentication {

    private final UserResponse userResponse;
    private boolean authenticated = true;

    public SimpleAuthentication(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 필요 시 사용자 권한 추가 가능
    }

    @Override
    public Object getCredentials() {
        return null; // 비밀번호는 반환하지 않음
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userResponse;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userResponse.email(); // 사용자 이메일 반환
    }
}
