package net.javaguides.identity_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.identity_service.enums.ERole;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private Set<ERole> roles;
}
