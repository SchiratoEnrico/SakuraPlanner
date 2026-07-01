package com.sakuraplanner.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sakuraplanner.backend.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Adapter class that wraps our pure JPA User entity 
 * to comply with Spring Security's UserDetails contract.
 * This ensures the User entity follows the Single Responsibility Principle.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    // The wrapped entity
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For now, we return an empty list. Roles can be added here later.
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // We map Spring's "username" concept to our application's "email"
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    /**
     * Helper method to easily access the underlying pure entity
     * when needed in Controllers or Services.
     */
    public User getUser() {
        return this.user;
    }
}