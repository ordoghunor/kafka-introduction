package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.mapper;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.RoomDetailedDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomId", source = "id")
    RoomDetailedDto modelToDetailedDto(Room room);
}
