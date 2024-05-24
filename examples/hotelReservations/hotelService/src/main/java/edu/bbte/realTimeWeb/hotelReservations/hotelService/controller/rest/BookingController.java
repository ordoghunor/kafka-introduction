package edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.rest;

import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.incoming.BookingCreationDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.BookingListingDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.BookingListingWithPaginationDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.CreatedObjectDto;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.dto.outgoing.Pagination;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.controller.mapper.BookingMapper;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Booking;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.UserHistoryMessage;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.BookingRepository;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.service.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, UserHistoryMessage> kafkaTemplate;
    private final String kafkaProduceTopicUserHistory;

    public BookingController(BookingMapper bookingMapper, BookingRepository bookingRepository,
                             KafkaTemplate<String, UserHistoryMessage> kafkaTemplate,
                             @Value("${kafkaProduceTopicUserHistory}") String kafkaProduceTopicUserHistory) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProduceTopicUserHistory = kafkaProduceTopicUserHistory;
    }

    @GetMapping
    public BookingListingWithPaginationDto findPaginated(
            @RequestParam(defaultValue = "1", required = false) @Positive int page,
            @RequestParam(defaultValue = "5", required = false) @Positive int limit,
            @RequestParam() @Positive Long userId) {
        LOGGER.info("GET paginated bookings at bookings api, "
                + "userId: {}, page: {}, limit: {}", userId, page, limit);
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("reservationStartDate"));
        Page<Booking> bookingPage = bookingRepository.findAllByUserId(userId, pageRequest);
        List<BookingListingDto> bookingListingDtos = bookingMapper.modelsToListingDtos(bookingPage.getContent());
        Pagination pagination = new Pagination(page, limit,
                bookingPage.getTotalElements(), bookingPage.getTotalPages());
        return new BookingListingWithPaginationDto(bookingListingDtos, pagination);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedObjectDto create(@RequestBody @Valid BookingCreationDto bookingCreationDto) throws ParseException {
        LOGGER.info("POST request at bookings api");
        Booking booking = bookingMapper.creationDtoToModel(bookingCreationDto);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        booking.setReservationStartDate(simpleDateFormat.parse(bookingCreationDto.getStartDate()));
        booking.setReservationEndDate(simpleDateFormat.parse(bookingCreationDto.getEndDate()));
        Booking createdBooking = bookingRepository.saveAndFlush(booking);
        UserHistoryMessage userHistoryMessage = new UserHistoryMessage(createdBooking.getUserId(),
                "Booking created for hotel id " + bookingCreationDto.getHotelId()
                        + ", booking id " + createdBooking.getId());
        LOGGER.info("Sending user activity: {}", userHistoryMessage);
        kafkaTemplate.send(kafkaProduceTopicUserHistory, userHistoryMessage);
        return bookingMapper.modelToCreatedObjectDto(createdBooking);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("DELETE booking request at bookings/{} api", id);
        try {
            Booking booking = bookingRepository.getById(id);
            Date currentDate = new Date();
            Date currentDatePlus2Hours = new Date(currentDate.getTime() + 2 * 60 * 60 * 1000);
            if (booking != null && booking.getReservationStartDate().after(currentDatePlus2Hours)) {
                bookingRepository.delete(booking);
                UserHistoryMessage userHistoryMessage = new UserHistoryMessage(booking.getUserId(),
                        "Booking deleted for hotel id " + booking.getHotel().getId()
                                + ", booking id " + booking.getId());
                LOGGER.info("Sending user activity: {}", userHistoryMessage);
                kafkaTemplate.send(kafkaProduceTopicUserHistory, userHistoryMessage);
            }
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("booking with ID " + id + " not found", e);
        }
    }

}
