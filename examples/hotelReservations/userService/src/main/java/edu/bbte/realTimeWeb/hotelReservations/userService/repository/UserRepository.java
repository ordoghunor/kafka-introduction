package edu.bbte.realTimeWeb.hotelReservations.userService.repository;


import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByRefreshToken(String refreshToken);

    void update(Long id, User entity);
}
