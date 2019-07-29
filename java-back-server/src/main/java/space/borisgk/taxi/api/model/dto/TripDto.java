package space.borisgk.taxi.api.model.dto;

import lombok.*;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.entity.User;

import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Integer id;

    private Date date;
    private Double fromLat, toLat, fromLong, toLong;
    private TripStatus status= TripStatus.ACTIVE;
    private String toString;

    private List<User> users;
}
