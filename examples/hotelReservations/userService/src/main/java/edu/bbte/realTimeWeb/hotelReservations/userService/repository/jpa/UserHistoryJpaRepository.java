package edu.bbte.realTimeWeb.hotelReservations.userService.repository.jpa;

import edu.bbte.realTimeWeb.hotelReservations.userService.model.UserHistory;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserHistoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface UserHistoryJpaRepository extends UserHistoryRepository, JpaRepository<UserHistory, Long> {
}
