package edu.bbte.realTimeWeb.hotelReservations.userService.service;

import edu.bbte.realTimeWeb.hotelReservations.userService.model.*;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserHistoryRepository;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class KafkaConsumerService {
    private final KafkaTemplate<String, UserResponseMessage> kafkaTemplate;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final String kafkaProduceTopic;

    public KafkaConsumerService(KafkaTemplate<String, UserResponseMessage> kafkaTemplate,
                                UserRepository userRepository, UserHistoryRepository userHistoryRepository,
                                @Value("${kafkaProduceTopic}") String kafkaProduceTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
        this.kafkaProduceTopic = kafkaProduceTopic;
        this.userHistoryRepository = userHistoryRepository;
    }

    @KafkaListener(topics = "${kafkaConsumeTopic}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"spring.json.value.default.type=edu.bbte.realTimeWeb.hotelReservations.userService.model.UserRequestedMessage"})
    public void listenToUserRequest(UserRequestedMessage userRequestMessage) {
        LOGGER.info("Received request for user with message: {}", userRequestMessage);
        User user = userRepository.findByUsername(userRequestMessage.getUsername()).orElse(null);
        UserResponseMessage userResponseMessage = new UserResponseMessage(user, userRequestMessage.getRequestId(),
                userRequestMessage.getUsername());
        LOGGER.info("Sending response: {}", userResponseMessage);
        kafkaTemplate.send(kafkaProduceTopic, userResponseMessage);
    }


    @KafkaListener(topics = "${kafkaConsumeTopicUserHistory}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"spring.json.value.default.type=edu.bbte.realTimeWeb.hotelReservations.userService.model.UserHistoryMessage"})
    public void listenToUserRequest(UserHistoryMessage userHistoryMessage) {
        LOGGER.info("Received user history message: {}", userHistoryMessage);
        User user = new User();
        user.setId(userHistoryMessage.getUserId());
        UserHistory userHistory = new UserHistory(user, userHistoryMessage.getActivity(),
                new Date());
        userHistoryRepository.saveAndFlush(userHistory);
    }
}
