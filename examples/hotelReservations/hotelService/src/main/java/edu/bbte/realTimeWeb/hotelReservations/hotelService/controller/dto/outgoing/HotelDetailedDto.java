package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing;

import lombok.Data;

import java.util.List;

@Data
public class HotelDetailedDto {
    private Long hotelId;
    private String name;
    private double latitude;
    private double longitude;
    private List<RoomDetailedDto> rooms;
}
