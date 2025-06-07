package net.javaguides.identity_service.enums;

import lombok.Getter;

@Getter
public enum ERole {
    ADMINISTRATOR("Administrator"),
    EMPLOYEE("Employee"),
    CUSTOMER("Customer");

    private final String label;

    ERole(String label) {
        this.label = label;
    }
}
