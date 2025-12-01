package com.chat_app.repository;

import com.chat_app.entity.User;
import com.chat_app.valueobjects.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, UserId> {

    boolean existsByUsernameOrEmail(String username, String email);

    User findByUsername(String username);
}
