package edu.bbte.realTimeWeb.hotelReservations.userService.controller.exception.handler;

import edu.bbte.realTimeWeb.hotelReservations.userService.service.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InvalidTokenExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleFailedConstraintException(InvalidTokenException exception) {
        LOGGER.error(exception.getMessage());
    }
}
