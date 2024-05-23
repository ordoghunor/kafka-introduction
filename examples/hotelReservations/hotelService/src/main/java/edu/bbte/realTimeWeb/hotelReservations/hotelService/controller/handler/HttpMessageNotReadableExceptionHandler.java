package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.handler;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.ErrorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpMessageNotReadableExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorData handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error("Invalid request body: {}", exception.getMessage());
        return new ErrorData("Invalid request body");
    }
}
