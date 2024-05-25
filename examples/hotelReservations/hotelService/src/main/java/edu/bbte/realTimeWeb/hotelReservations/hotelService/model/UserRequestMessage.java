package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserRequestMessage {
    private String requestId;
    private String username;
}
