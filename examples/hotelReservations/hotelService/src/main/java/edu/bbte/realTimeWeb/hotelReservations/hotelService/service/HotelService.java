package edu.bbte.realTimeWeb.hotelReservations.hotelService.service;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelDetailedDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelListingDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelListingDtoWithPagination;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.Pagination;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.mapper.HotelMapper;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Hotel;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.HotelRepository;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.service.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelListingDtoWithPagination findHotelsWithinRangeOfUser(
            int page, int limit, double userLat, double userLong, double distanceKm
    ) {
        // pageNumber is 0 indexed
        // request only part of the hotels
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("name"));
        Page<Hotel> hotelsPage = hotelRepository.findAllInsideRadiusMeters(
                userLat, userLong, distanceKm * 1000, pageRequest);
        List<HotelListingDto> hotelListingDtos = hotelMapper.modelsToListingDtos(hotelsPage.getContent());
        Pagination pagination = new Pagination(page, limit,
                hotelsPage.getTotalElements(), hotelsPage.getTotalPages());
        return new HotelListingDtoWithPagination(hotelListingDtos, pagination);
    }

    public HotelDetailedDto findHotelByIdWithAvailableRooms(Long id, Date startDate, Date endDate) {
        try {
            Hotel hotel = hotelRepository.getById(id);
            if (hotel == null) {
                throw new NotFoundException("Hotel with ID " + id + " not found");
            } else {
                // filter the rooms to only show available ones
                List<Room> availabeRooms = hotel.getRooms().stream()
                        .filter(room -> isRoomAvailable(room, startDate, endDate))
                        .toList();
                hotel.setRooms(availabeRooms);
                return hotelMapper.modelToDetailedDto(hotel);
            }
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("Hotel with ID " + id + " not found", e);
        }
    }

    private boolean isRoomAvailable(Room room, Date startDate, Date endDate) {
        return room.isAvailable() &&
                room.getBookings().stream().noneMatch(booking ->
                        booking.getReservationEndDate().after(startDate) &&
                                booking.getReservationStartDate().before(endDate)
                );
    }
}
