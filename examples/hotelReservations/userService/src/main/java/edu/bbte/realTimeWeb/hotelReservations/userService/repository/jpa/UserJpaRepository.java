package edu.bbte.realTimeWeb.hotelReservations.userService.repository.jpa;


import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("jpa")
public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("UPDATE User "
            + "SET username= :#{#entity.username}, firstName= :#{#entity.firstName}, password= :#{#entity.password}, "
            + "lastName= :#{#entity.lastName}, refreshToken= :#{#entity.refreshToken} WHERE id = :id ")
    @Override
    void update(Long id, User entity);

}
