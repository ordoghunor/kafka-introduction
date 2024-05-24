package edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreationDto {
    @NotNull @NotBlank
    private String username;
    @NotNull @NotBlank
    private String firstName;
    @NotNull @NotBlank
    private String lastName;
    @NotNull @NotBlank
    private String password;
}
