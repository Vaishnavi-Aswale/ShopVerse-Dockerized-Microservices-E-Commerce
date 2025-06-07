package net.javaguides.identity_service.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.javaguides.identity_service.enums.ERole;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<ERole, String> {

    @Override
    public String convertToDatabaseColumn(ERole status) {
        if(status == null){
            return null;
        }
        return status.name();
    }

    @Override
    public ERole convertToEntityAttribute(String label) {
        if(label == null){
            return null;
        }
        return ERole.valueOf(label);
    }
}
