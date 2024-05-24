package edu.bbte.realTimeWeb.hotelReservations.userService.service.exception;

public class UsernameNotAvailableException extends RuntimeException {

    public UsernameNotAvailableException(String message) {
        super(message);
    }

    public UsernameNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
