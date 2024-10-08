package ru.tomatojAI.analytics_builder_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tomatojAI.analytics_builder_service.entity.Subscriptions;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscriptions, Long> {
    @Query("SELECT DISTINCT s.query FROM Subscriptions s")
    List<String> findDistinctQueries();
}
