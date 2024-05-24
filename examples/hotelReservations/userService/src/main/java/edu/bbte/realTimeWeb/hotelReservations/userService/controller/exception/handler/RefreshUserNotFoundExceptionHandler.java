package edu.bbte.realTimeWeb.hotelReservations.userService.controller.exception.handler;

import edu.bbte.realTimeWeb.hotelReservations.userService.service.exception.RefreshUserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshUserNotFoundExceptionHandler {
    @ExceptionHandler(RefreshUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleFailedConstraintException(RefreshUserNotFoundException exception) {
    }
}
