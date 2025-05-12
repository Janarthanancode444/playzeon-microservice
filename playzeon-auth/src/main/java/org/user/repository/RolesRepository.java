package org.user.repository;

import org.app.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, String> {
    @Query(value = "select * from roles s where s.user_id = :id",nativeQuery = true)
    Optional<Roles> findByRole(String id);
}
