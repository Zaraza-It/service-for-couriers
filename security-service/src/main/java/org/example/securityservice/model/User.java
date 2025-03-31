package org.example.securityservice.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.example.securityservice.model.enums.RoleEnum;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "user_id")
private Long id;

@Min(4)
@Column(name = "username")
private String username;
@Min(5)
@Column(name = "password")
private String password;

@Getter
@Nullable
@Email
@Column(name = "email")
private String email;
private BigDecimal balance;

@Enumerated(EnumType.STRING)
@CollectionTable(name ="roles",joinColumns = @JoinColumn(name = "user_id"))
@ElementCollection(fetch = FetchType.EAGER)
private Set<RoleEnum> roles;



    public Set<RoleEnum> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.iterator().next().getAuthority()));
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
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

    public String getEmail() {
        return email;
    }
}
