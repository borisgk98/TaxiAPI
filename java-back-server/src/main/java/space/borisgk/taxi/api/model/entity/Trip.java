package space.borisgk.taxi.api.model.entity;

import lombok.*;
import space.borisgk.taxi.api.model.dto.TripStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Double fromLat, toLat, fromLong, toLong;
    private TripStatus status= TripStatus.ACTIVE;
    private String toString;

    @ManyToMany
    private List<User> users;
}
