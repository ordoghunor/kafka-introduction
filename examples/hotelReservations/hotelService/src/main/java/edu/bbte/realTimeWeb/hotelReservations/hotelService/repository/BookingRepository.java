package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingRepository extends Repository<Booking, Long> {
    Page<Booking> findAll(Pageable pageable);
    void delete(Booking booking);
}
