package com.project.layer.Persistence.Entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_AUTHENTICATION", uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthentication implements UserDetails {

    @EmbeddedId
    private UserId userId;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ATTEMPTS", nullable = false)
    private int attempts;
    
    @Column(name = "ISBLOCKED", nullable = false)
    private boolean isBlocked;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = role.getPermissions().stream()
            .map(permissionsEnum -> new SimpleGrantedAuthority(permissionsEnum.name()))
            .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //retorna el estado, pero debo hacer el trigger
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
