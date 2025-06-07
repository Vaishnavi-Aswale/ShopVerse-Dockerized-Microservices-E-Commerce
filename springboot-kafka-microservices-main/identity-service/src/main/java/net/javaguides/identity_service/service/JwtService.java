package net.javaguides.identity_service.service;

import net.javaguides.identity_service.config.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String generateToken(CustomUserDetails userPrincipal);
    String extractUsername(String token);
    Date extractExpiration(String token);
    Boolean validateToken(String token, UserDetails userDetails);
}
