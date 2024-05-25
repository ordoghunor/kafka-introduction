package edu.bbte.realTimeWeb.hotelReservations.hotelService.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.config.GrantedAuthorityDeserializer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data()
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Date registrationDate;
    private Role role;
    private String refreshToken;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    private List<GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
