package org.application.repository;

import org.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT b FROM Booking b WHERE b.startTime = :time")
    Optional<Booking> findByStartTime(@Param("time") Instant time);


    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    Optional<Booking> findByUserId(@Param("userId") String userId);


}
