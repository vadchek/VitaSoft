package com.vadim.vitasoft;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, OPERATOR, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
