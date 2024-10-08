package ru.tomatojAI.analytics_builder_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tomatojAI.analytics_builder_service.entity.Analytics;

public interface AnalyticRepository extends JpaRepository<Analytics, Long> {
}
