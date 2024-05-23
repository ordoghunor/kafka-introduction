package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing;


import lombok.Data;

@Data
public class HotelListingDto {
    private Long hotelId;
    private String name;
    private double latitude;
    private double longitude;
}
