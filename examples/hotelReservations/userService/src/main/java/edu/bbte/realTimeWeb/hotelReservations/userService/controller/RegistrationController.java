package edu.bbte.realTimeWeb.hotelReservations.userService.controller;

import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.incoming.UserCreationDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.outgoing.CreatedObjectDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.controller.mapper.UserMapper;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.UserHistory;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserHistoryRepository;
import edu.bbte.realTimeWeb.hotelReservations.userService.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Slf4j
@AllArgsConstructor
@RestController
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping("/register")
public class RegistrationController {
    private final UserMapper userMapper;
    private final UserHistoryRepository userHistoryRepository;
    private final AuthenticationService authenticationService;


    @PostMapping
    public CreatedObjectDto register(@RequestBody @Valid UserCreationDto userCreationDto) {
        LOGGER.info("Registration request");
        User createdUser = authenticationService.register(userMapper.creationDtoToModel(userCreationDto));
        UserHistory userHistory = new UserHistory();
        userHistory.setUser(createdUser);
        userHistory.setDate(new Date());
        userHistory.setActivity("Registration");
        userHistoryRepository.saveAndFlush(userHistory);
        LOGGER.info("User registered with username '{}'", createdUser.getUsername());
        return userMapper.modelToCreatedObjDto(createdUser);
    }

}
