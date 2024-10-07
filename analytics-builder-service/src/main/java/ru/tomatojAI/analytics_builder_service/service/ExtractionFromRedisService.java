package ru.tomatojAI.analytics_builder_service.service;

import io.lettuce.core.ScanCursor;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import ru.tomatojAI.analytics_builder_service.DTO.VacancyDTO;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ExtractionFromRedisService {
    private final RedisTemplate<String, VacancyDTO> redisTemplate;

    public VacancyDTO findByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<VacancyDTO> getAllVacancies() {
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (factory == null) {
            return Collections.emptyList();
        }

        try (RedisConnection connection = factory.getConnection()) {
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match("*").build());
            List<VacancyDTO> allValues = new ArrayList<>();

            while (cursor.hasNext()) {
                String key = new String(cursor.next(), StandardCharsets.UTF_8);
                VacancyDTO value = redisTemplate.opsForValue().get(key);
                if (value != null) {
                    allValues.add(value);
                }
            }
            return allValues;
        }
    }
}
