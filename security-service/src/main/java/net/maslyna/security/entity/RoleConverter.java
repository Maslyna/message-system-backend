package net.maslyna.security.entity;

import net.maslyna.security.enums.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class RoleConverter {

    @WritingConverter
    public static class RoleWritingConverter implements Converter<Role, Integer> {
        @Override
        public Integer convert(Role source) {
            return switch (source) {
                case ADMIN -> 0;
                case USER -> 1;
            };
        }
    }

    @ReadingConverter
    public static class RoleReadingConverter implements Converter<Integer, Role> {
        @Override
        public Role convert(Integer source) {
            return switch (source) {
                case 0 -> Role.ADMIN;
                case 1 -> Role.USER;
                default -> null;
            };
        }
    }

}