package edu.bbte.realTimeWeb.hotelReservations.hotelService.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    private final String kafkaProduceTopic;


    public TopicConfiguration(@Value("${kafkaProduceTopic}") String kafkaProduceTopic) {
        this.kafkaProduceTopic = kafkaProduceTopic;
    }

    @Bean
    public NewTopic produceTopic()
    {
        return TopicBuilder.name(kafkaProduceTopic)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
