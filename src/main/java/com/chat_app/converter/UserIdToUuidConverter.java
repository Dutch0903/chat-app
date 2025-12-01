package com.chat_app.converter;

import com.chat_app.valueobjects.UserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.UUID;

@WritingConverter
public class UserIdToUuidConverter implements Converter<UserId, UUID> {
    @Override
    public UUID convert(UserId source) {
        return source.value();
    }
}
