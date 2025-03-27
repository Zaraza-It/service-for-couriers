package org.example.market.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.market.dto.EmailDTO;
import org.example.market.dto.KafkaOrderDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {


    @Bean
    public ProducerFactory<String, EmailDTO> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
             ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
             "localhost:9092");
        props.put(
             ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
             StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, KafkaOrderDTO> orderProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EmailDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, KafkaOrderDTO> orderKafkaTemplate() {
        return new KafkaTemplate<>(orderProducerFactory());
    }

}
