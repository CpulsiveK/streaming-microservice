package com.cpulsivek.notification_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class Kafka {
    @Bean
    public KafkaAdmin.NewTopics topics456() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("upload")
                        .build(),
                TopicBuilder.name("defaultPart")
                        .build(),
                TopicBuilder.name("defaultRepl")
                        .build());
    }
}
