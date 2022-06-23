package ot.homework5plus.rushm.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, BANNED_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
