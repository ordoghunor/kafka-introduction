package edu.bbte.realTimeWeb.hotelReservations.userService.config;

import edu.bbte.realTimeWeb.hotelReservations.userService.model.User;
import edu.bbte.realTimeWeb.hotelReservations.userService.model.UserHistory;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserHistoryRepository;
import edu.bbte.realTimeWeb.hotelReservations.userService.repository.UserRepository;
import edu.bbte.realTimeWeb.hotelReservations.userService.util.TokenExtraction;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
public class LogoutHandlerImp implements LogoutHandler {
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = TokenExtraction.extractTokenFromRequestCookie(request);
        if (token == null) {
            return;
        }
        // delete refresh token
        User user = userRepository.findByRefreshToken(token);
        user.setRefreshToken(null);
        userRepository.update(user.getId(), user);
        UserHistory userHistory = new UserHistory();
        userHistory.setUser(user);
        userHistory.setDate(new Date());
        userHistory.setActivity("Logout");
        userHistoryRepository.saveAndFlush(userHistory);
        // remove cookie by setting maxAge to 0 overwriting the existing cookie
        Cookie authCookie = new Cookie("Auth", "");
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge(0);
        authCookie.setAttribute("SameSite", "None");
        response.addCookie(authCookie);
        LOGGER.info("User with username '{}' logged out", user.getUsername());
    }
}
