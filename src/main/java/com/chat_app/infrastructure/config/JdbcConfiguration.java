package com.chat_app.infrastructure.config;

import com.chat_app.infrastructure.converter.RoleIdToUuidConverter;
import com.chat_app.infrastructure.converter.UserIdToUuidConverter;
import com.chat_app.infrastructure.converter.UuidToRoleIdConverter;
import com.chat_app.infrastructure.converter.UuidToUserIdConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.*;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return Arrays.asList(
                new UserIdToUuidConverter(),
                new UuidToUserIdConverter(),
                new RoleIdToUuidConverter(),
                new UuidToRoleIdConverter()
        );
    }
}
