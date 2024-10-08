package ru.tomatojAI.analytics_builder_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "analytics")
@NoArgsConstructor
public class Analytics {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "datetime")
    private LocalDateTime datetime;

    @Column(name = "vacancy_count")
    private Long vacancyCount;

    @Column(name = "query")
    private String query;

    @Column(name = "average_salary")
    private Long averageSalary;

    public Analytics(LocalDateTime datetime, Long vacancyCount, String query, Long averageSalary) {
        this.datetime = datetime;
        this.vacancyCount = vacancyCount;
        this.query = query;
        this.averageSalary = averageSalary;
    }

}