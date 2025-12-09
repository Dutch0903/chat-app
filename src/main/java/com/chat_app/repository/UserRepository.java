package com.chat_app.repository;

import com.chat_app.entity.User;
import com.chat_app.valueobjects.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, UserId> {

    boolean existsByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    List<User> findAllByIdIn(Set<UserId> userIds);
}
