package edu.bbte.realTimeWeb.hotelReservations.hotelService.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    private final String kafkaProduceTopicUserRequest;
    private final String kafkaProduceTopicUserHistory;


    public TopicConfiguration(@Value("${kafkaProduceTopic}") String kafkaProduceTopicUserRequest,
                              @Value("${kafkaProduceTopicUserHistory}") String  kafkaProduceTopicUserHistory ) {
        this.kafkaProduceTopicUserRequest = kafkaProduceTopicUserRequest;
        this.kafkaProduceTopicUserHistory = kafkaProduceTopicUserHistory;
    }

    @Bean
    public NewTopic produceTopicuserRequest()
    {
        return TopicBuilder.name(kafkaProduceTopicUserRequest)
                .partitions(2)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic produceTopicUserHistory()
    {
        return TopicBuilder.name(kafkaProduceTopicUserHistory)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
