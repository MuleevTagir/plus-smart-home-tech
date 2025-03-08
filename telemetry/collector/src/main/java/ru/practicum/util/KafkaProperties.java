package ru.practicum.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.config")
public class KafkaProperties {
    private String bootstrapServers;
    private String clientIdConfig;
    private String producerKeySerializer;
    private String producerValueSerializer;
    private String sensorEventTopic;
    private String hubEventTopic;
}