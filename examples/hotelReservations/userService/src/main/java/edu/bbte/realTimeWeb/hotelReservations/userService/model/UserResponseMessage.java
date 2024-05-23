package edu.bbte.realTimeWeb.hotelReservations.userService.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseMessage {
    private User user;
    private String requestId;
    private String username;
}
