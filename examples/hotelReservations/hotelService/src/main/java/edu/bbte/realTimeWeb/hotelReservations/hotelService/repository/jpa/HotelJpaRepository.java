package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.jpa;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Hotel;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.HotelRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface HotelJpaRepository extends HotelRepository, JpaRepository<Hotel, Long> {

    // calculate the distance between the position of the user and hotel in database
    // so that only relevant hotels are returned
    // if distance is calculated after the hotels are requested many entities are returned redundantly
    // distance is returned in meters by ST_Distance, converted to km by '* 1000'
    @Override
    @Query("FROM Hotel h "
            + "WHERE ST_Distance_Sphere(point(h.longitude, h.latitude), point(:userLong, :userLat)) "
            + "<= :distanceMeters ")
    Page<Hotel> findAllInsideRadiusMeters(double userLat, double userLong, double distanceMeters, Pageable pageable);
}
