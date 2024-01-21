package net.maslyna.security.entity;

import net.maslyna.security.enums.Role;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.EnumWriteSupport;

@WritingConverter
public class RoleWriteConverter extends EnumWriteSupport<Role> {
}