package edu.bbte.realTimeWeb.hotelReservations.userService.controller.mapper;


import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.incoming.LoginInformationDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.incoming.UserCreationDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.outgoing.CreatedObjectDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    CreatedObjectDto modelToCreatedObjDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    User creationDtoToModel(UserCreationDto userCreationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    User loginDtoToModel(LoginInformationDto loginInformationDto);

}
