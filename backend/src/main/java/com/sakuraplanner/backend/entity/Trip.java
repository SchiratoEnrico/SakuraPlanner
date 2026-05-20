package com.sakuraplanner.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a travel itinerary created by a user.
 */
@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * The total estimated budget for the trip. 
     */
    @Column(name = "total_budget", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalBudget = BigDecimal.ZERO;

    /**
     * Many-to-One relationship with the User who owns this trip.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * One-to-Many relationship with TripDay.
     * mappedBy points to the 'trip' field in the TripDay class.
     * cascade = ALL ensures that if a Trip is deleted, all its days are deleted too.
     * orphanRemoval = true ensures that if a day is removed from this list, it gets deleted from the DB.
     */
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripDay> days = new ArrayList<>();
}