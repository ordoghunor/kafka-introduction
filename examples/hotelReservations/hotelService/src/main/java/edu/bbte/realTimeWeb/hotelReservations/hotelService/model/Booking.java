package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data()
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @Column(nullable = false, name = "reservation_start_date")
    private Date reservationStartDate;
    @Column(nullable = false, name = "reservation_end_date")
    private Date reservationEndDate;
    @ManyToMany()
    @Column(nullable = false)
    private List<Room> rooms;
}
