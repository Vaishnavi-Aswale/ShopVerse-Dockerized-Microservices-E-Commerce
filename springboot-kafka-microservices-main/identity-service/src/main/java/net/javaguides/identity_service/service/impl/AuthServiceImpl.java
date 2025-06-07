package net.javaguides.identity_service.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javaguides.identity_service.config.CustomUserDetails;
import net.javaguides.identity_service.dto.AuthRequest;
import net.javaguides.identity_service.dto.SignUpRequest;
import net.javaguides.identity_service.entity.Role;
import net.javaguides.identity_service.entity.UserCredential;
import net.javaguides.identity_service.enums.ERole;
import net.javaguides.identity_service.exception.AuthException;
import net.javaguides.identity_service.repository.RoleRepository;
import net.javaguides.identity_service.repository.UserCredentialRepository;
import net.javaguides.identity_service.service.AuthService;
import net.javaguides.identity_service.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Override
    public String saveUser(SignUpRequest signUpRequest) {
        if (userCredentialRepository.existsByName(signUpRequest.getName())) {
            throw new AuthException("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userCredentialRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AuthException("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        UserCredential userCredential = new UserCredential();
        userCredential.setName(signUpRequest.getName());
        userCredential.setEmail(signUpRequest.getEmail());
        userCredential.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (signUpRequest.getRoles() == null || signUpRequest.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            signUpRequest.getRoles().forEach(role -> {
                Role userRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });
        }

        userCredential.setRoles(roles);
        userCredentialRepository.save(userCredential);
        return "User added to the system";
    }

    @Override
    public String generateToken(AuthRequest authRequest, HttpServletResponse response) {
        UserDetails userDetails = loadUserByUsername(authRequest.getName());
        if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
            String token = jwtService.generateToken((CustomUserDetails) userDetails);
            return token;
        } else {
            throw new AuthException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token, loadUserByUsername(jwtService.extractUsername(token)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = userCredentialRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return CustomUserDetails.build(user);
    }
}
