package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "season_ticket")
public class SeasonTicket {

    private static final Integer COUNT_AVAILABLE_LESSON = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private UUID number;

    @Column(name = "count_available_lessons")
    private Integer countAvailableLessons;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public SeasonTicket(Long userID) {
        this.number = UUID.randomUUID();
        this.userId = userID;
        countAvailableLessons = COUNT_AVAILABLE_LESSON;
    }
}
