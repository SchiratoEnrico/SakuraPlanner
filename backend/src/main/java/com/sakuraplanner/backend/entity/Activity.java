package com.sakuraplanner.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Represents a specific event, visit, or transport item within a single TripDay.
 */
@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    /**
     * Optional description or personal notes about the activity (e.g., "Wear comfortable shoes").
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    
    // Location Data (To be used with Mapbox / Google Places API)
    
    @Column(length = 255)
    private String address;

    /**
     * Latitude coordinate for map rendering.
     */
    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;

    /**
     * Longitude coordinate for map rendering.
     */
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

 
    // Budget & Expense Data

    /**
     * Cost of the activity in Japanese Yen (JPY).
     */
    @Column(name = "cost_jpy", nullable = false, precision = 12, scale = 2)
    private BigDecimal costJpy = BigDecimal.ZERO;


    // Relationships

    /**
     * Many-to-One relationship back to the specific TripDay.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_day_id", nullable = false)
    private TripDay tripDay;
}