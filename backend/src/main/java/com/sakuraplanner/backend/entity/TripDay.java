package com.sakuraplanner.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific day within a Trip itinerary.
 */
@Entity
@Table(name = "trip_days")
@Getter
@Setter
@NoArgsConstructor
public class TripDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The specific calendar date for this travel day (e.g., 2026-10-15).
     */
    @Column(name = "day_date", nullable = false)
    private LocalDate dayDate;

    /**
     * An optional title or theme for the day (e.g., "Exploring Akihabara" or "Kyoto Temple Tour").
     */
    @Column(length = 150)
    private String theme;

    /**
     * Bidirectional Many-to-One relationship back to the parent Trip.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    
    /**
     * One-to-Many relationship with Activity.
     * Keeps track of all scheduled activities for this specific day, ordered by start time.
     */
    @OneToMany(mappedBy = "tripDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startTime ASC") // Hibernate will automatically sort activities chronologically
    private List<Activity> activities = new ArrayList<>();
}