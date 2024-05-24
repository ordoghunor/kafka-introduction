package edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorData {
    private String errorMessage;
}
