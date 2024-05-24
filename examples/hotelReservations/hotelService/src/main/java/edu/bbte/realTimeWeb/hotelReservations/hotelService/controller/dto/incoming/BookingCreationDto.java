package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class BookingCreationDto {
    @Positive
    private Long hotelId;
    @Positive
    private Long userId;
    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;
    @NotEmpty
    private List<Long> rooms;
}
