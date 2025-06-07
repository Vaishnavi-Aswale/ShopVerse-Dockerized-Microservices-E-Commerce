package net.javaguides.identity_service.config;

import net.javaguides.identity_service.dto.RoleDto;
import net.javaguides.identity_service.dto.UserDto;
import net.javaguides.identity_service.entity.Role;
import net.javaguides.identity_service.entity.UserCredential;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapping from Role to RoleDto
        modelMapper.createTypeMap(Role.class, RoleDto.class)
                .addMapping(Role::getId, RoleDto::setId)
                .addMapping(Role::getName, RoleDto::setAuthority);

        // Mapping from UserCredential to UserDto
        modelMapper.createTypeMap(UserCredential.class, UserDto.class)
                .addMapping(UserCredential::getId, UserDto::setId)
                .addMapping(UserCredential::getName, UserDto::setName)
                .addMapping(UserCredential::getEmail, UserDto::setEmail)
                .addMapping(src -> src.getRoles().stream()
                        .map(role -> modelMapper.map(role, RoleDto.class))
                        .collect(Collectors.toSet()), UserDto::setRoles);

        return modelMapper;
    }
}
