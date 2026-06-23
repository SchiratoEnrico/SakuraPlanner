package com.sakuraplanner.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakuraplanner.backend.entity.TripDay;

import java.util.List;

@Repository
public interface TripDayRepository extends JpaRepository<TripDay, Long> {

    /**
     * Retrieves all days of a specific trip, chronologically ordered.
     */
    List<TripDay> findByTripIdOrderByDayDateAsc(Long tripId);
}
