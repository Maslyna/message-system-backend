package net.maslyna.security.model.entity.converter;

import net.maslyna.security.model.entity.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class RoleReadingConverter implements Converter<Short, Role> {
    @Override
    public Role convert(Short source) {
        return Role.getRole(source);
    }
}
