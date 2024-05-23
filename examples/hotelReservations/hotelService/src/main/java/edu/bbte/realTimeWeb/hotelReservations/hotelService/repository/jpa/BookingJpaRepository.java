package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.jpa;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Booking;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.BookingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJpaRepository extends BookingRepository, JpaRepository<Booking, Long> {
}
