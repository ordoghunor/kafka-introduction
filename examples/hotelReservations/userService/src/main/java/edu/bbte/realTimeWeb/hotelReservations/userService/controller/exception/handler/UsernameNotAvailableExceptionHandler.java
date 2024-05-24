package edu.bbte.realTimeWeb.hotelReservations.userService.controller.exception.handler;

import edu.bbte.realTimeWeb.hotelReservations.userService.controller.dto.outgoing.ErrorData;
import edu.bbte.realTimeWeb.hotelReservations.userService.service.exception.UsernameNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UsernameNotAvailableExceptionHandler {
    @ExceptionHandler(UsernameNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorData handleFailedConstraintException(UsernameNotAvailableException exception) {
        LOGGER.error(exception.getMessage());
        return new ErrorData(exception.getMessage());
    }
}
