package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.setup.RoomDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data()
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
@JsonDeserialize(using = RoomDeserializer.class)
public class Room extends BaseEntity {
    @Column(nullable = false, name = "room_number")
    private int roomNumber;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false, name = "is_available")
    private boolean isAvailable;
    @ManyToMany(mappedBy = "rooms")
    private List<Booking> bookings;
}
