package com.chat_app.converter;

import com.chat_app.valueobjects.UserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class UuidToUserIdConverter implements Converter<UUID, UserId> {
    @Override
    public UserId convert(UUID source) {
        return new UserId(source);
    }
}
