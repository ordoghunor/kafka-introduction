package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryMessage {
    private Long userId;
    private String activity;
}
