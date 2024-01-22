package net.maslyna.security.entity.converter;

import net.maslyna.security.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class RoleReadingConverter implements Converter<Short, Role> {
    @Override
    public Role convert(Short source) {
        return Role.getRole(source);
    }
}
