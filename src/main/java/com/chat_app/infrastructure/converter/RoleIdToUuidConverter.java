package com.chat_app.infrastructure.converter;

import com.chat_app.domain.valueobjects.RoleId;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class RoleIdToUuidConverter implements Converter<RoleId, UUID> {
    @Override
    public UUID convert(RoleId source) {
        return source.value();
    }
}
