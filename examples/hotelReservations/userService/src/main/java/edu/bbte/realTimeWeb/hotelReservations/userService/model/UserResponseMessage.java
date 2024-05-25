package edu.bbte.realTimeWeb.hotelReservations.userService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseMessage {
    private User user;
    private String requestId;
    private String username;
}
