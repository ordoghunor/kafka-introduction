package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.handler;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.ErrorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataIntegrityViolationExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorData handleFailedConstraintException(DataIntegrityViolationException exception) {
        LOGGER.error("Invalid data {}", exception.getMessage());
        return new ErrorData("Invalid data");
    }
}
