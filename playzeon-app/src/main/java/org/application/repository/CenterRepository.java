package org.application.repository;

import org.app.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, String> {
}
