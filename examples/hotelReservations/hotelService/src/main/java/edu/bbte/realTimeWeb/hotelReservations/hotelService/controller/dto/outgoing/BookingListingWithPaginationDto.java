package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class BookingListingWithPaginationDto {
    Collection<BookingListingDto> bookingListingDtos;
    Pagination pagination;
}
