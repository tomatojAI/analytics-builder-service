package ru.tomatojAI.analytics_builder_service.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.tomatojAI.analytics_builder_service.DTO.VacancyDTO;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class RedisExtractionServiceTest {

    @Autowired
    private ExtractionFromRedisService extractionFromRedisService;

    @Autowired
    private RedisTemplate<String, VacancyDTO> redisTemplate;


    @Test
    public void findByKeyTest() {
        VacancyDTO vacancyDTO = new VacancyDTO("name", 0L);
        String testKey = "key";
        redisTemplate.opsForValue().set(testKey, vacancyDTO);

        VacancyDTO testVacancy = extractionFromRedisService.findByKey(testKey);

        assertEquals(vacancyDTO, testVacancy);
    }

    @Test
    public void getAllVacanciesTest(){
        VacancyDTO vacancyDTO1 = new VacancyDTO("name1", 0L);
        String testKey1 = "key1";
        VacancyDTO vacancyDTO2 = new VacancyDTO("name2", 0L);
        String testKey2 = "key2";

        List<VacancyDTO> listOfVacancy = List.of(vacancyDTO1, vacancyDTO2);

        redisTemplate.opsForValue().set(testKey1, vacancyDTO1);
        redisTemplate.opsForValue().set(testKey2, vacancyDTO2);

        List<VacancyDTO> allVacancies = extractionFromRedisService.getAllVacancies();

        assertEquals(listOfVacancy.size(), allVacancies.size());
        assertTrue(allVacancies.containsAll(listOfVacancy));
    }
}
