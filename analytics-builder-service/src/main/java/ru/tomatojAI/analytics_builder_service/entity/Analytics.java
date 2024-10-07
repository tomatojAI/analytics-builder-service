package ru.tomatojAI.analytics_builder_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "analytics")
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



}