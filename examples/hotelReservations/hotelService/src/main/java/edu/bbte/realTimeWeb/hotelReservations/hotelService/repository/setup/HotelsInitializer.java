package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.setup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Hotel;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@Component
public class HotelsInitializer implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (hotelRepository.count() == 0) {
            InputStream inputStream = new ClassPathResource("hotels.json").getInputStream();
            List<Hotel> hotels = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            hotelRepository.saveAllAndFlush(hotels);
        }
    }
}
