package edu.bbte.realTimeWeb.hotelReservations.userService.service;

import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.UserRequestedMessage;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.UserResponseMessage;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
    private final KafkaTemplate<String, UserResponseMessage> kafkaTemplate;
    private final UserRepository userRepository;
    private final String kafkaProduceTopic;

    public KafkaConsumerService(KafkaTemplate<String, UserResponseMessage> kafkaTemplate,
                                UserRepository userRepository, @Value("${kafkaProduceTopic}") String kafkaProduceTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRepository = userRepository;
        this.kafkaProduceTopic = kafkaProduceTopic;
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
}
