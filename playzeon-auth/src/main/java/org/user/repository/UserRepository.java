package org.user.repository;

import org.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String username);

    List<User> findByPhone(String phone);

    @Query(value = "Select email from User where name=:userName")
    String findByEmail(String userName);
}
