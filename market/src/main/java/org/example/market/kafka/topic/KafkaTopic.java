package org.example.market.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {


    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("topic1").build();
    }
    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic2").build();
    }
}
