package ru.tomatojAI.analytics_builder_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RabbitReceiver {

    private final AnalyticsBuilderService analyticsBuilderService;

    @RabbitListener(queues = {"${queue.name}"})
    public void receiver() {
        analyticsBuilderService.buildAnalyticAndSave(LocalDateTime.now().withMinute(0).withSecond(0).withNano(0));
    }
}
