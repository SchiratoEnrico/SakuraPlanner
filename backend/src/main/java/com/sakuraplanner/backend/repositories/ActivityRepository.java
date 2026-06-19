package com.sakuraplanner.backend.repositories;

import com.sakuraplanner.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Retrieves all activities scheduled for a specific day, ordered by start time.
     */
    List<Activity> findByTripDayIdOrderByStartTimeAsc(Long tripDayId);
}
