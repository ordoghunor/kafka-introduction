package edu.bbte.realTimeWeb.hotelReservations.userService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data()
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_history")
public class UserHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String activity;
    @Column(nullable = false)
    private Date date;
}
