package ru.tomatojAI.analytics_builder_service.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tomatojAI.analytics_builder_service.DTO.VacancyDTO;
import ru.tomatojAI.analytics_builder_service.entity.Analytics;
import ru.tomatojAI.analytics_builder_service.repository.AnalyticRepository;
import ru.tomatojAI.analytics_builder_service.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnalyticsBuilderService {
    private final SubscriptionRepository subscriptionRepository;
    private final AnalyticRepository analyticRepository;
    private final ExtractionFromRedisService extractionFromRedisService;

    public Map<String, Analytics> buildAnalytic(LocalDateTime dateTime) {
        List<VacancyDTO> vacancies = extractionFromRedisService.getAllVacancies();
        List<String> distinctQueries = subscriptionRepository.findDistinctQueries();

        Map<String, Analytics> analyticsMap = distinctQueries.stream()
                .collect(Collectors.toMap(
                        query -> query,
                        query -> new Analytics(dateTime, 0L, query, 0L)
                ));

        analyticsMap.putAll(vacancies.stream()
                .flatMap(vacancy -> distinctQueries.stream()
                        .filter(query -> vacancy.getName().contains(query))
                        .map(query -> new AbstractMap.SimpleEntry<>(query, vacancy)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                entryList -> {
                                    long count = entryList.size();
                                    long totalSalary = entryList.stream().mapToLong(entry -> entry.getValue().getSalary()).sum();
                                    return new Analytics(dateTime, count, entryList.get(0).getKey(), totalSalary / count);
                                }
                        )
                )));

        return analyticsMap;
    }

    public void buildAnalyticAndSave(LocalDateTime dateTime){
        Map<String, Analytics> builtAnalytics = buildAnalytic(dateTime);
        analyticRepository.saveAll(builtAnalytics.values());
    }

}
