package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.jpa;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.RoomRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface RoomJpaRepository extends RoomRepository, JpaRepository<Room, Long> {
}
