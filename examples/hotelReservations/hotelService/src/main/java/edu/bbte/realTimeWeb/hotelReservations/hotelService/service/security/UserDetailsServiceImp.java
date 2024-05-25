package edu.bbte.realTimeWeb.hotelReservations.hotelService.service.security;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.UserRequestMessage;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.UserResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService {
    private final KafkaTemplate<String, UserRequestMessage> kafkaTemplate;
    private final String kafkaProduceTopic;
    private final ConcurrentHashMap<String, CompletableFuture<UserDetails>>
            pendingRequest = new ConcurrentHashMap<>();

    public UserDetailsServiceImp(KafkaTemplate<String, UserRequestMessage> kafkaTemplate,
                                 @Value("${kafkaProduceTopic}") String kafkaProduceTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProduceTopic = kafkaProduceTopic;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUserByUsername(username).get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

    }

    @KafkaListener(topics = "${kafkaConsumeTopic}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"spring.json.value.default.type=edu.bbte.realTimeWeb.hotelReservations.hotelService.model.UserResponseMessage"})
    public void listenToUserRequest(UserResponseMessage userResponseMessage) {
        LOGGER.info("Received message: {}", userResponseMessage);
        if (userResponseMessage.getUser() == null) {
            throw new UsernameNotFoundException("User " + userResponseMessage.getUsername() + " not found");
        }
        CompletableFuture<UserDetails> future = pendingRequest.remove(userResponseMessage.getRequestId());
        if (future != null) {
            future.complete(userResponseMessage.getUser());
        }
    }


    private CompletableFuture<UserDetails> getUserByUsername(String username) {
        String requestId = UUID.randomUUID().toString();
        UserRequestMessage userRequestMessage = new UserRequestMessage(requestId, username);
        CompletableFuture<UserDetails> future = new CompletableFuture<>();
        pendingRequest.put(requestId, future);
        LOGGER.info("Sending request: {}", userRequestMessage);
        kafkaTemplate.send(kafkaProduceTopic, userRequestMessage);
        return future;
    }

}
