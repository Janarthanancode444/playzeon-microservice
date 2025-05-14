package org.user.repository;

import org.app.entity.ErrorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorHistoryRepository extends JpaRepository<ErrorHistory, String> {

}
