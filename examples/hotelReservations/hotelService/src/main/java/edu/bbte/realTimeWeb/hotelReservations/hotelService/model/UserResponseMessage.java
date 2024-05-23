package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseMessage {
    private User user;
    private String requestId;
    private String username;
}
