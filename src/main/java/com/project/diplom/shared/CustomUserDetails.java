package com.project.diplom.shared;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.diplom.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    @JsonView(View.JsonWebToken.class)
    private Long id;

    @JsonView(View.JsonWebToken.class)
    private String name;

    @JsonView(View.JsonWebToken.class)
    private User.UserRole userRole;

    @JsonIgnore
    private String password;

    public CustomUserDetails() {
    }

    public CustomUserDetails(
            Long id,
            String name,
            User.UserRole userRole,
            String password) {
        this.id = id;
        this.name = name;
        this.userRole = userRole;
        this.password = password;
    }

    public CustomUserDetails(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.getUserRole().equals(User.UserRole.USER)) {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        } else return AuthorityUtils.createAuthorityList("ROLE_UNAUTHENTICATED");
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
