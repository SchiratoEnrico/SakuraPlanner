package com.sakuraplanner.backend.repositories;

import com.sakuraplanner.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    /**
     * Retrieves all trips created by a specific user, ordered by the start date.
     * This is for the user's dashboard to show upcoming trips first.
     */
    List<Trip> findByUserIdOrderByStartDateAsc(Long userId);
}
