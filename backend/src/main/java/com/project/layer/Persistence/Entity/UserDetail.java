package com.project.layer.Persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="USERDETAILS", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetail implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSERDETAILS", nullable = false)
    private long idUserDetails;

    @Column(name = "CLIENT_IDUSER", nullable = false)
    private String clientIdUser;

    @Column(name = "CLIENT_IDDOCTYPEFK", nullable = false)
    private String clientIdDocTypeFk;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER", nullable = false)
    private String user;

    @Column(name = "ATTEMPTS", nullable = false)
    private int attempts;

    @Column(name = "BLOCK", nullable = false)
    private boolean block;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("role"));
    }

    @Override
    public String getUsername() {
        return "";
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
        return true;
    }
}