package edu.bbte.realTimeWeb.hotelReservations.userService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserRequestedMessage {
    private String requestId;
    private String username;
}
