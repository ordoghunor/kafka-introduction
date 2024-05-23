package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.mapper;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelDetailedDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.HotelListingDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.RoomDetailedDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Hotel;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "hotelId", source = "id")
    HotelListingDto modelToListingDto(Hotel hotel);

    @Mapping(target = "hotelId", source = "id")
    List<HotelListingDto> modelsToListingDtos(List<Hotel> hotels);

    @Mapping(target = "hotelId", source = "id")
    HotelDetailedDto modelToDetailedDto(Hotel hotel);

    default RoomDetailedDto mapRoomToDto(Room room) {
        if (room == null) {
            return null;
        }
        RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);
        return roomMapper.modelToDetailedDto(room);
    }

    default List<RoomDetailedDto> mapRoomsToDtos(List<Room> rooms) {
        if (rooms == null) {
            return null;
        }
        return rooms.stream().map(this::mapRoomToDto).toList();
    }
}
