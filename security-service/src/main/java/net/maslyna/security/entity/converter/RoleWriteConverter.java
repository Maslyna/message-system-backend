package net.maslyna.security.entity.converter;

import net.maslyna.security.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class RoleWriteConverter implements Converter<Role, Short> {
    @Override
    public Short convert(Role source) {
        return source.value;
    }
}