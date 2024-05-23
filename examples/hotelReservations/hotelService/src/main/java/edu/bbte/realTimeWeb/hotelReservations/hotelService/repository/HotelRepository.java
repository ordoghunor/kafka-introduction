package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HotelRepository extends Repository<Hotel, Long> {
    Page<Hotel> findAllInsideRadiusMeters(double userLat, double userLong, double distanceMeters ,Pageable pageable);
}
