package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingListingDto {
    private Long bookingId;
    private HotelListingDto hotel;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private List<RoomDetailedDto> rooms;
}
