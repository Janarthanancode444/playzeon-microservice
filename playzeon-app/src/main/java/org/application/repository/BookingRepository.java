package org.application.repository;

import org.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT b FROM Booking b WHERE b.startTime = :time")
    Optional<Booking> findByStartTime(@Param("time") Instant time);


    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Optional<Booking> findByUserId(@Param("userId") String userId);

    @Query("SELECT  b FROM Organization o JOIN Center c ON c.organization.id = o.id JOIN Booking b ON b.center.id = c.id WHERE o.id = :id")
    Optional<Booking> findOrganizationId(final String id);

    //@Query("SELECT b FROM Booking b  JOIN Center c ON b.center.id = c.id  JOIN SportCenterMap s ON s.center.id = c.id  WHERE FUNCTION('TO_CHAR', b.createdAt, 'YYYY-MM-DD') LIKE :'date%'")
    @Query(value = "SELECT b.* FROM booking b " +
            "JOIN center c ON b.center_id = c.id " +
            "JOIN sport_center_map s ON s.center_id = c.id " +
            "WHERE DATE_FORMAT(b.created_at, '%Y-%m-%d') LIKE :date", nativeQuery = true)
    Optional<Booking> findByDate(final String date);

}
