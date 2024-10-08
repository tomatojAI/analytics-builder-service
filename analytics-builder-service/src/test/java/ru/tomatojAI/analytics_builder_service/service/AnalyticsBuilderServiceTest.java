package ru.tomatojAI.analytics_builder_service.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.tomatojAI.analytics_builder_service.DTO.VacancyDTO;
import ru.tomatojAI.analytics_builder_service.entity.Analytics;
import ru.tomatojAI.analytics_builder_service.entity.Subscriptions;
import ru.tomatojAI.analytics_builder_service.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class AnalyticsBuilderServiceTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private AnalyticsBuilderService analyticsBuilderService;
    @Autowired
    private RedisTemplate<String, VacancyDTO> redisTemplate;


    @Test
    public void buildAnalyticTest() {
        VacancyDTO vacancyDTO1 = new VacancyDTO("java", 1L);
        String testKey1 = "key1";

        VacancyDTO vacancyDTO2 = new VacancyDTO("java", 5L);
        String testKey2 = "key2";

        VacancyDTO vacancyDTO3 = new VacancyDTO("python", 1L);
        String testKey3 = "key3";

        VacancyDTO vacancyDTO4 = new VacancyDTO("pascal", 1L);
        String testKey4 = "key4";

        redisTemplate.opsForValue().set(testKey1, vacancyDTO1);
        redisTemplate.opsForValue().set(testKey2, vacancyDTO2);
        redisTemplate.opsForValue().set(testKey3, vacancyDTO3);
        redisTemplate.opsForValue().set(testKey4, vacancyDTO4);


        Subscriptions subscriptions1 = new Subscriptions(null, "java");
        Subscriptions subscriptions2 = new Subscriptions(null, "python");
        Subscriptions subscriptions3 = new Subscriptions(null, "c++");

        subscriptionRepository.save(subscriptions1);
        subscriptionRepository.save(subscriptions2);
        subscriptionRepository.save(subscriptions3);

        Map<String, Analytics> result = analyticsBuilderService.buildAnalytic(LocalDateTime.now());

        Assertions.assertEquals(result.get("java").getAverageSalary(),3,"averageSalary должен быть 3");
        Assertions.assertEquals(result.get("java").getVacancyCount(),2,"getVacancyCount должен быть 2");

        Assertions.assertEquals(result.get("python").getAverageSalary(),1,"averageSalary должен быть 1");
        Assertions.assertEquals(result.get("python").getVacancyCount(),1,"getVacancyCount должен быть 1");

        Assertions.assertEquals(result.get("c++").getAverageSalary(),0,"averageSalary должен быть 0");
        Assertions.assertEquals(result.get("c++").getVacancyCount(),0,"getVacancyCount должен быть 0");

        Assertions.assertNull(result.get("pascal"), "pascal не должен быть в result");

        System.out.println(result);

        redisTemplate.delete(testKey1);
        redisTemplate.delete(testKey2);
        redisTemplate.delete(testKey3);
        redisTemplate.delete(testKey4);

        subscriptionRepository.deleteById(subscriptions1.getId());
        subscriptionRepository.deleteById(subscriptions2.getId());
        subscriptionRepository.deleteById(subscriptions3.getId());
    }
}
