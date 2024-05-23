package edu.bbte.realTimeWeb.hotelReservations.userService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserRequestMessage {
    private String requestId;
    private String username;
}
