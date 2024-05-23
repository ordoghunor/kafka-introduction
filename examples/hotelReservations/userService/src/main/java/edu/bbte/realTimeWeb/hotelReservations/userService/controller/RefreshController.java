package edu.bbte.realTimeWeb.hotelReservations.userService.controller;

import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.outgoing.TokenDto;
import edu.bbte.realTimeWeb.hotelReservations.userService.service.AuthenticationService;
import edu.bbte.realTimeWeb.hotelReservations.userService.util.TokenExtraction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/refresh")
public class RefreshController {
    private final AuthenticationService authenticationService;

    @GetMapping
    public TokenDto refresh(@NotNull HttpServletRequest request) {
        String token = TokenExtraction.extractTokenFromRequestCookie(request);
        return new TokenDto(authenticationService.refresh(token));
    }
}
