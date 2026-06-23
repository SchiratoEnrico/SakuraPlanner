package com.sakuraplanner.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakuraplanner.backend.entity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Retrieves all activities scheduled for a specific day, ordered by start time.
     */
    List<Activity> findByTripDayIdOrderByStartTimeAsc(Long tripDayId);
}
