package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.rest;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelDetailedDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelListingDtoWithPagination;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.service.HotelService;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.service.exception.NotFoundException;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping("api/hotels")
@AllArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public HotelListingDtoWithPagination findPaginated(
            @RequestParam(defaultValue = "1", required = false) @Positive int page,
            @RequestParam(defaultValue = "5", required = false) @Positive int limit,
            @RequestParam(defaultValue = "46.770439", required = false) @Positive double userLat,
            @RequestParam(defaultValue = "23.591423", required = false) @Positive double userLong,
            @RequestParam(defaultValue = "150", required = false) @Positive double distanceKm) {
        LOGGER.info("GET paginated hotels at hotels api, "
                        + "page: {}, limit: {}, userLat: {}, userLong: {}, "
                        + "distanceKm: {},",
                page, limit, userLat, userLong, distanceKm);
        // default value of lat and long set to Cluj Napoca
        return hotelService.findHotelsWithinRangeOfUser(page, limit, userLat, userLong, distanceKm);
    }


    @GetMapping("/{id}")
    public HotelDetailedDto findById(@PathVariable("id") Long id,
                                     @RequestParam(required = false) String startDate,
                                     @RequestParam(required = false) String endDate
    ) throws NotFoundException, ParseException {
        Date reservationStartDate = new Date();
        Date reservationEndDate = new Date(reservationStartDate.getTime() + 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (startDate != null) {
            reservationStartDate = simpleDateFormat.parse(startDate);
        }
        if (endDate != null) {
            reservationEndDate = simpleDateFormat.parse(endDate);
        }
        LOGGER.info("GET hotel with id {} at hotels api, startdate: {}, endDate: {}", id, startDate, endDate);
        return hotelService.findHotelByIdWithAvailableRooms(id, reservationStartDate, reservationEndDate);
    }
}
