package net.javaguides.identity_service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.identity_service.entity.Permission;
import net.javaguides.identity_service.entity.Role;
import net.javaguides.identity_service.entity.UserCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private Set<Permission> permissions = new HashSet<>();

    public static CustomUserDetails build(UserCredential userCredential) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(userCredential.getId());
        userDetails.setName(userCredential.getName());
        userDetails.setEmail(userCredential.getEmail());
        userDetails.setPassword(userCredential.getPassword());
        userDetails.setRoles(userCredential.getRoles());
        userDetails.setPermissions(userCredential.getPermissions());
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name()));
        });
        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getName().name()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
