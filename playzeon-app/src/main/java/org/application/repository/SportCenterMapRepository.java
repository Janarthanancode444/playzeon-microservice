package org.application.repository;

import org.app.entity.SportCenterMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportCenterMapRepository extends JpaRepository<SportCenterMap, String> {
    List<SportCenterMap> findAllByCenterId(String centerId);
}
