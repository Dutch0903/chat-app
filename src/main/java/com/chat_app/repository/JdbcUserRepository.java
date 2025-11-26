package com.chat_app.repository;

import com.chat_app.entity.User;
import com.chat_app.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean exists(String username, String email) {
        try {
            jdbcTemplate.queryForObject(
                    "SELECT count(*) FROM users WHERE username=? OR email=?",
                    Integer.class,
                    username,
                    email
            );
            // A result has been found.
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch(IncorrectResultSizeDataAccessException e) {
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
}
