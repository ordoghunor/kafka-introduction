package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.mapper;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.incoming.BookingCreationDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.BookingListingDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.CreatedObjectDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Booking;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, RoomMapper.class})
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservationStartDate", ignore = true)
    @Mapping(target = "reservationEndDate", ignore = true)
    @Mapping(target = "hotel.id", source = "hotelId")
    Booking creationDtoToModel(BookingCreationDto bookingCreationDto);

    default List<Room> mapRoomIdsToRooms(List<Long> rooms) {
        return rooms.stream().map(this::mapRoomIdToRoom).toList();
    }

    default Room mapRoomIdToRoom(Long roomId) {
        Room room = new Room();
        room.setId(roomId);
        return room;
    }

    CreatedObjectDto modelToCreatedObjectDto(Booking booking);

    @Mapping(target = "bookingId", source = "id")
    BookingListingDto modelToListingDto(Booking booking);

    @Mapping(target = "bookingId", source = "id")
    List<BookingListingDto> modelsToListingDtos(List<Booking> bookings);
}
