package com.chat_app.infrastructure.converter;

import com.chat_app.domain.valueobjects.RoleId;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class UuidToRoleIdConverter implements Converter<UUID, RoleId> {
    @Override
    public RoleId convert(UUID source) {
        return new RoleId(source);
    }
}
