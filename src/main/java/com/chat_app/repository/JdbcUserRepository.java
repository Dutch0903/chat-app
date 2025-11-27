package com.chat_app.repository;

import com.chat_app.entity.User;
import com.chat_app.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.UUID;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean exists(String username, String email) {
        try {
            int count = jdbcTemplate.queryForObject(
                    "SELECT count(*) FROM users WHERE username=? OR email=?",
                    Integer.class,
                    username,
                    email
            );
            // A result has been found.
            return count != 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (IncorrectResultSizeDataAccessException e) {
            return true;
        }
    }

    public int save(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)",
                user.getId().value(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE username = ?",
                getUserRowMapper(),
                username
        );
    }

    private RowMapper<User> getUserRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        new UserId(rs.getObject(1, UUID.class)),
                        rs.getString(2),
                        rs.getString(3),
                        Collections.emptySet(),
                        rs.getString(4)
                );
            }
        };
    }
}
