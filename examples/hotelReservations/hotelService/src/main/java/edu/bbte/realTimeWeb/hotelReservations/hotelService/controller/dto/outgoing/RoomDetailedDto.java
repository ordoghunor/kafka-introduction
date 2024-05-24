package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing;

import lombok.Data;

@Data
public class RoomDetailedDto {
    private Long roomId;
    private int roomNumber;
    private int type;
    private double price;
}
